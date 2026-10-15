package com.example.comboclicker;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

// dist = CLIENT -> мод грузится только на клиенте, на сервере ставить не нужно
@Mod(value = ComboClicker.MODID, dist = Dist.CLIENT)
public class ComboClicker {
    public static final String MODID = "comboclicker";

    public ComboClicker(IEventBus modBus, ModContainer container) {
        container.registerConfig(ModConfig.Type.CLIENT, Config.SPEC);
        // Экран настроек: Mods -> ComboClicker -> Config
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);

        modBus.addListener(ClickerHandler::registerKeys);
        NeoForge.EVENT_BUS.addListener(ClickerHandler::onClientTick);
    }
}
