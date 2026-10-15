package com.example.comboclicker;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

public class ClickerHandler {
    // По умолчанию - боковая кнопка мыши (назад, GLFW button 4).
    // Меняется в Настройки -> Управление -> ComboClicker
    public static final KeyMapping CLICK_KEY = new KeyMapping(
            "key.comboclicker.click",
            InputConstants.Type.MOUSE,
            GLFW.GLFW_MOUSE_BUTTON_4,
            "key.categories.comboclicker");

    private static double accumulator = 0;
    private static boolean enabled = false;

    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(CLICK_KEY);
    }

    public static void onClientTick(ClientTickEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null || mc.screen != null) {
            accumulator = 0;
            enabled = false;
            return;
        }

        boolean active;
        if (Config.TOGGLE_MODE.get()) {
            while (CLICK_KEY.consumeClick()) {
                enabled = !enabled;
            }
            active = enabled;
        } else {
            enabled = false;
            active = CLICK_KEY.isDown();
        }

        if (!active) {
            accumulator = 0;
            return;
        }

        if (Config.ONLY_ON_ENTITY.get()
                && (mc.hitResult == null || mc.hitResult.getType() != HitResult.Type.ENTITY)) {
            accumulator = 0;
            return;
        }

        // Тик = 20 раз/сек, поэтому копим дробные клики (поддерживает CPS > 20)
        accumulator += Config.CPS.get() / 20.0;
        while (accumulator >= 1.0) {
            accumulator -= 1.0;
            KeyMapping.click(mc.options.keyAttack.getKey());
        }
    }
}
