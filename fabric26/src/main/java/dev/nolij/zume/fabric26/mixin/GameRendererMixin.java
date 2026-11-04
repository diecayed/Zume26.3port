package dev.nolij.zume.fabric26.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.nolij.zume.impl.Zume;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
	
	@Inject(method = "render", at = @At("HEAD"))
	public void zume$render$HEAD(CallbackInfo ci) {
		Zume.renderHook();
	}
	
	@ModifyReturnValue(method = "getFov", at = @At("RETURN"))
	public float zume$getFov$RETURN(float original) {
		if (Zume.isFOVHookActive())
			return (float) Zume.fovHook(original);
		
		return original;
	}
	
}
