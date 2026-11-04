package dev.nolij.zume.fabric26.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.nolij.zume.impl.Zume;
import net.minecraft.client.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = Camera.class, priority = 1500)
public abstract class CameraMixin {
	
	// Smooth FOV zoom. Since 26.x the world FOV is computed once per frame in Camera#calculateFov
	// (GameRenderer#getFov no longer exists); the hand/HUD FOV is computed separately and left alone.
	@ModifyReturnValue(method = "calculateFov", at = @At("RETURN"))
	public float zume$calculateFov$RETURN(float original) {
		if (Zume.isFOVHookActive())
			return (float) Zume.fovHook(original);
		
		return original;
	}
	
	// Third-person zoom: scales the camera distance passed to the wall-clipping check.
	@ModifyArg(method = "alignWithEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;getMaxZoom(F)F"))
	public float zume$alignWithEntity$getMaxZoom(float original) {
		return (float) Zume.thirdPersonCameraHook(original);
	}
	
}
