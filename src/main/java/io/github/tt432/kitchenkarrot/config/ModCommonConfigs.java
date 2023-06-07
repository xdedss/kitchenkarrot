package io.github.tt432.kitchenkarrot.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModCommonConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec COMMON;

    public static ForgeConfigSpec.ConfigValue<Boolean> CAN_ENTITY_SPAWN;
    public static ForgeConfigSpec.ConfigValue<Integer> CAN_ENTITY_LIFETIME;
    public static ForgeConfigSpec.ConfigValue<Boolean> BUTCHER_SELL_SALT;
    public static ForgeConfigSpec.ConfigValue<Boolean> BUTCHER_SELL_OIL;
    public static ForgeConfigSpec.ConfigValue<Boolean> FARMER_BUY_GEM_CARROT;


    static {
        BUILDER.comment("Common Configs for KitchenKarrot");

        BUILDER.push("Canned Food Settings");
        CAN_ENTITY_SPAWN = BUILDER.comment("Whether or not an empty can entity spawns everytime player finished eating a canned food.")
                .define("Spawn Empty Can Entity", Boolean.TRUE);
        CAN_ENTITY_LIFETIME = BUILDER.comment("The maximum time in seconds an empty can entity can live.","10 by default, -1 to prevent disappearing.")
                .define("Can Entity Lifetime",10);
        BUILDER.pop();
        BUILDER.push("Villager Trade Settings");
        BUTCHER_SELL_SALT = BUILDER.comment("Will butcher villager sell salt at level 1")
                .define("Butcher Sell Salt", Boolean.TRUE);
        BUTCHER_SELL_OIL = BUILDER.comment("Will butcher villager sell oil at level 1")
                .define("Butcher Sell Oil", Boolean.TRUE);
        FARMER_BUY_GEM_CARROT = BUILDER.comment("Will farmer villager buy gem carrot at level 3")
                .define("Farmer Buy Gem Carrot", Boolean.TRUE);
        COMMON = BUILDER.build();
    }
}
