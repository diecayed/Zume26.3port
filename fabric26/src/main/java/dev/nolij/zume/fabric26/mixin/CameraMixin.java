package dev.nolij.zume.fabric26.mixin;

import dev.nolij.zume.impl.Zume;
import net.minecraft.client.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = Camera.class, priority = 1500)
public abstract class CameraMixin {
	
	// Third-person zoom: scales the camera distance passed to the wall-clipping check.
	@ModifyArg(method = "setup", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;getMaxZoom(F)F"))
	public float zume$setup$getMaxZoom(float original) {
		return (float) Zume.thirdPersonCameraHook(original);
	}
	
}
