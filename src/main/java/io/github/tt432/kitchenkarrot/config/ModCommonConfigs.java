package io.github.tt432.kitchenkarrot.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModCommonConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec COMMON;

    public static ForgeConfigSpec.DoubleValue GEM_CARROT_DROP_CHANCE;


    static {
        BUILDER.comment("Common Configs for KitchenKarrot");

        BUILDER.comment("Item Drop Settings").push("Loot Modifier");
        GEM_CARROT_DROP_CHANCE = BUILDER.comment("Chance of getting a gem carrot when harvest carrot.","0.02 by default.")
                .defineInRange("Gem Carrot Drop Chance",0.02,0,1);
        BUILDER.pop();
        COMMON = BUILDER.build();
    }
}
