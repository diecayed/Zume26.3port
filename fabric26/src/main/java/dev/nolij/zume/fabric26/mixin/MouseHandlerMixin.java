package dev.nolij.zume.fabric26.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.nolij.zume.impl.Zume;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.ScrollWheelHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = MouseHandler.class, priority = 500)
public abstract class MouseHandlerMixin {
	
	// Cinematic camera while zoomed
	@ModifyExpressionValue(method = "turnPlayer", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Options;smoothCamera:Z"))
	public boolean zume$turnPlayer$smoothCamera(boolean original) {
		return Zume.cinematicCameraEnabledHook(original);
	}
	
	// Lower mouse sensitivity while zoomed
	@SuppressWarnings("unchecked")
	@ModifyExpressionValue(method = "turnPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/OptionInstance;get()Ljava/lang/Object;", ordinal = 0))
	public <T> T zume$turnPlayer$sensitivity(T original) {
		return (T) (Object) Zume.mouseSensitivityHook((Double) original);
	}
	
	// Allow zoom scrolling in spectator mode
	@ModifyExpressionValue(method = "onScroll", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isSpectator()Z"))
	public boolean zume$onScroll$isSpectator(boolean original) {
		if (Zume.isMouseScrollHookActive())
			return false;
		
		return original;
	}
	
	// Scroll to zoom instead of changing hotbar slot
	@WrapOperation(method = "onScroll", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/ScrollWheelHandler;getNextScrollWheelSelection(DII)I"))
	public int zume$onScroll$getNextScrollWheelSelection(double scrollAmount, int current, int max, Operation<Integer> original) {
		if (Zume.mouseScrollHook((int) scrollAmount))
			return current;
		
		return original.call(scrollAmount, current, max);
	}
	
}
