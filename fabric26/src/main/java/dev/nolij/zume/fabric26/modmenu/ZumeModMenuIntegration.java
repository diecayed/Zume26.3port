package dev.nolij.zume.fabric26.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.nolij.zume.impl.Zume;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ZumeModMenuIntegration implements ModMenuApi {
	
	// The "Config" button opens zume.json5 in the system editor, then returns to the previous screen.
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> new Screen(Component.literal("")) {
			@Override
			protected void init() {
				Zume.openConfigFile();
				Minecraft.getInstance().setScreen(parent);
			}
		};
	}
	
}
