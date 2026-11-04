package dev.nolij.zume.fabric26;

import dev.nolij.zume.impl.CameraPerspective;
import dev.nolij.zume.impl.IZumeImplementation;
import dev.nolij.zume.impl.Zume;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;

public class Fabric26Zume implements ClientModInitializer, IZumeImplementation {
	
	@Override
	public void onInitializeClient() {
		Zume.LOGGER.info("Loading Zume for Fabric 26.x...");
		
		Zume.registerImplementation(this, FabricLoader.getInstance().getConfigDir());
		if (Zume.disabled)
			return;
		
		for (final ZumeKeyBind keyBind : ZumeKeyBind.values()) {
			KeyMappingHelper.registerKeyMapping(keyBind.value);
		}
		
		Zume.postInit();
	}
	
	@Override
	public boolean isZoomPressed() {
		return Minecraft.getInstance().screen == null && ZumeKeyBind.ZOOM.isPressed();
	}
	
	@Override
	public boolean isZoomInPressed() {
		return ZumeKeyBind.ZOOM_IN.isPressed();
	}
	
	@Override
	public boolean isZoomOutPressed() {
		return ZumeKeyBind.ZOOM_OUT.isPressed();
	}
	
	@Override
	public CameraPerspective getCameraPerspective() {
		// CameraType: FIRST_PERSON, THIRD_PERSON_BACK, THIRD_PERSON_FRONT (same order as CameraPerspective)
		return CameraPerspective.values()[Minecraft.getInstance().options.getCameraType().ordinal()];
	}
	
}
