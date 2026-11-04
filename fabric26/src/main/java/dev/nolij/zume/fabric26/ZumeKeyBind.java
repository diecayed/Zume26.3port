package dev.nolij.zume.fabric26;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;

public enum ZumeKeyBind {
	
	ZOOM("zume.zoom", InputConstants.KEY_Z),
	ZOOM_IN("zume.zoom_in", InputConstants.KEY_EQUALS),
	ZOOM_OUT("zume.zoom_out", InputConstants.KEY_MINUS),
	
	;
	
	private static final class Holder {
		// Since 1.21.9 key categories are registered objects instead of plain translation keys.
		static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath("zume", "zume"));
	}
	
	public final KeyMapping value;
	
	public boolean isPressed() {
		return value.isDown();
	}
	
	ZumeKeyBind(String translationKey, int code) {
		this.value = new KeyMapping(translationKey, InputConstants.Type.KEYBOARD, code, Holder.CATEGORY);
	}
	
}
