package io.github.tt432.kitchenkarrot.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModCommonConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec COMMON;

    public static ForgeConfigSpec.ConfigValue<Boolean> CAN_ENTITY_SPAWN;
    public static ForgeConfigSpec.ConfigValue<Integer> CAN_ENTITY_LIFETIME;


    static {
        BUILDER.comment("Common Configs for KitchenKarrot");

        BUILDER.push("Canned Food Settings");
        CAN_ENTITY_SPAWN = BUILDER.comment("Whether or not an empty can entity spawns everytime player finished eating a canned food.")
                .define("Spawn Empty Can Entity",Boolean.TRUE);
        CAN_ENTITY_LIFETIME = BUILDER.comment("The maximum time in seconds an empty can entity can live.","10 by default, -1 to prevent disappearing.")
                .define("Can Entity Lifetime",10);
        BUILDER.pop();
        COMMON = BUILDER.build();
    }
}
