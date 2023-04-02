package io.github.tt432.kitchenkarrot.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModCommonConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec COMMON;

    public static ForgeConfigSpec.BooleanValue ENABLE_CLASSIC_FOOD;
    public static ForgeConfigSpec.BooleanValue ENABLE_MODERN_FOOD;
    public static ForgeConfigSpec.BooleanValue ENABLE_CANNED_FOOD;
    public static ForgeConfigSpec.BooleanValue ENABLE_COCKTAILS;
    public static ForgeConfigSpec.DoubleValue GEM_CARROT_DROP_CHANCE;
    public static ForgeConfigSpec.IntValue GEM_CARROT_MAX_AGE;


    static {
        BUILDER.comment("Common Configs for KitchenKarrot");

        BUILDER.comment("Gem Carrot Crop Settings").push("Gem Carrot Crops");
        GEM_CARROT_DROP_CHANCE = BUILDER.comment("Chance of getting a gem carrot when harvest carrot.","0.02 by default.")
                .defineInRange("Gem Carrot Drop Chance",0.02,0,1);
        GEM_CARROT_MAX_AGE = BUILDER.comment("Max age of gem carrot crop.It describes generally how slow it grows.","15 by default.Must be an integer between 1 and 999.")
                .defineInRange("Gem Carrot Max Age",15,1,999);
        BUILDER.pop();
        COMMON = BUILDER.build();
    }
}
