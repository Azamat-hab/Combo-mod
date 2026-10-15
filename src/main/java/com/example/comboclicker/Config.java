package com.example.comboclicker;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder B = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue CPS = B
            .comment("Кликов в секунду")
            .defineInRange("cps", 12, 1, 50);

    public static final ModConfigSpec.BooleanValue TOGGLE_MODE = B
            .comment("true = нажал и отпустил - вкл/выкл; false = кликает пока держишь кнопку")
            .define("toggleMode", false);

    public static final ModConfigSpec.BooleanValue ONLY_ON_ENTITY = B
            .comment("Кликать только когда прицел наведён на сущность (не ломать блоки)")
            .define("onlyOnEntity", true);

    static final ModConfigSpec SPEC = B.build();
}
