package io.github.tt432.kitchenkarrot.registries;

import io.github.tt432.kitchenkarrot.item.*;
import net.minecraft.core.NonNullList;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Comparator;

import static io.github.tt432.kitchenkarrot.Kitchenkarrot.MOD_ID;

/**
 * @author DustW
 **/
@SuppressWarnings("unused")
@ParametersAreNonnullByDefault
public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    public static final RegistryObject<Item> BEEF_IN_DRIPLEAF = ITEMS.register("beef_in_dripleaf", () -> new ModFood(12, 14.4F).setIndex(25));
    public static final RegistryObject<Item> SMALL_BEEF_IN_DRIPLEAF = ITEMS.register("small_beef_in_dripleaf", () -> new ModFood(6, 7.2F).setIndex(26));
    public static final RegistryObject<Item> BAMBOO_POTATO = ITEMS.register("bamboo_potato", () -> new ModFood(9, 9).setIndex(27));
    public static final RegistryObject<Item> PICKLED_SEA_PICKLES = ITEMS.register("pickled_sea_pickles", () -> new ModFood(2, 2).setIndex(28));
    public static final RegistryObject<Item> BIRCH_SAP_CHOCOLATE_BAR = ITEMS.register("birch_sap_chocolate_bar", () -> new ModFood(5, 9.2F).setIndex(29));
    public static final RegistryObject<Item> CHOCOLATE_CROISSANT = ITEMS.register("chocolate_croissant", () -> new ModFood(7, 8).setIndex(30));
    public static final RegistryObject<Item> BEETROOT_CREPE = ITEMS.register("beetroot_crepe", () -> new ModFood(9, 9F).setIndex(31));
    public static final RegistryObject<Item> CHINESE_CREPE = ITEMS.register("chinese_crepe", () -> new ModFood(9, 9F).setIndex(32));
    public static final RegistryObject<Item> CROQUE_MADAME = ITEMS.register("croque_madame", () -> new ModFood(8, 10).setIndex(33));
    public static final RegistryObject<Item> GRILLED_WHEATMEAL = ITEMS.register("grilled_wheatmeal", () -> new ModFood(3, 1).setIndex(34));
    public static final RegistryObject<Item> GRILLED_FISH_AND_CACTUS = ITEMS.register("grilled_fish_and_cactus", () -> new ModFood(8, 8F).setIndex(35));
    public static final RegistryObject<Item> FLOWER_CAKE = ITEMS.register("flower_cake", () -> new ModFood(6, 4.8F).setIndex(36));
    public static final RegistryObject<Item> PILLAGER_PIE = ITEMS.register("pillager_pie", () -> new ModFood(6, 4.8F, EffectEntry.of(MobEffects.BAD_OMEN, 600, 1)).setIndex(37));
    public static final RegistryObject<Item> MONSTER_LASAGNA = ITEMS.register("monster_lasagna", () -> new ModFood(13, 3.2F, EffectEntry.of(MobEffects.HUNGER, 30, 1)).setIndex(40));
    public static final RegistryObject<Item> SMALL_MONSTER_LASAGNA = ITEMS.register("small_monster_lasagna", () -> new ModFood(5, 1.6F, EffectEntry.of(MobEffects.HUNGER, 10, 1)).setIndex(41));
    public static final RegistryObject<Item> CHORUS_MOUSSE = ITEMS.register("chorus_mousse", () -> new ModFood(12, 14.4F).setIndex(63));
    public static final RegistryObject<Item> SMALL_CHORUS_MOUSSE = ITEMS.register("small_chorus_mousse", () -> new ModFood(4, 4.8F).setIndex(64));
    public static final RegistryObject<Item> SLIME_MOUSSE = ITEMS.register("slime_mousse", () -> new ModFood(12, 14.4F).setIndex(65));
    public static final RegistryObject<Item> SMALL_SLIME_MOUSSE = ITEMS.register("small_slime_mousse", () -> new ModFood(4, 4.8F).setIndex(66));
    public static final RegistryObject<Item> DUNGEON_PIZZA_SLICE = ITEMS.register("dungeon_pizza_slice", () -> new ModFood(10, 6F, EffectEntry.of(MobEffects.POISON, 10, 0.2F)).setIndex(72));
    public static final RegistryObject<Item> FEAST_PIZZA_SLICE = ITEMS.register("feast_pizza_slice", () -> new ModFood(10, 12.8F).setIndex(68));
    public static final RegistryObject<Item> SHINY_PIZZA_SLICE = ITEMS.register("shiny_pizza_slice", () -> new ModFood(7, 24).setIndex(70));
    public static final RegistryObject<Item> SWEET_LOAF = ITEMS.register("sweet_loaf", () -> new ModFood(14, 14.4F).setIndex(74));
    public static final RegistryObject<Item> SWEET_LOAF_SLICE = ITEMS.register("sweet_loaf_slice", () -> new ModFood(7, 9.6F).setIndex(75));
    public static final RegistryObject<Item> EGG_TART = ITEMS.register("egg_tart", () -> new ModFood(5, 7.2F).setIndex(90));
    public static final RegistryObject<Item> SWEET_BERRY_TART = ITEMS.register("sweet_berry_tart", () -> new ModFood(5, 7.2F).setIndex(91));
    public static final RegistryObject<Item> CARROT_TART = ITEMS.register("carrot_tart", () -> new ModFood(5, 7.2F).setIndex(92));
    public static final RegistryObject<Item> HONEY_BRULEE = ITEMS.register("honey_brulee", () -> new ModFood(4, 9.6F).setIndex(87));
    public static final RegistryObject<Item> LAVA_BRULEE = ITEMS.register("lava_brulee", () -> new ModFood(4, 9.6F).setIndex(88));
    public static final RegistryObject<Item> HI_NRG_BRULEE = ITEMS.register("hi_nrg_brulee", () -> new ModFood(4, 14.4F).setIndex(89));
    public static final RegistryObject<Item> SIRLOIN_STEAK = ITEMS.register("sirloin_steak", () -> new ModFood(13, 18F).setIndex(76));
    public static final RegistryObject<Item> BEEF_GRAINS = ITEMS.register("beef_grains", () -> new ModFood(3, 4).setIndex(77));
    public static final RegistryObject<Item> SASHIMI = ITEMS.register("sashimi", () -> new ModFood(4, 2.4F).setIndex(78));
    public static final RegistryObject<Item> MOSS_FRIED_LAMB_CUTLETS = ITEMS.register("moss_fried_lamb_cutlets", () -> new ModFood(10, 12.8F).setIndex(79));
    public static final RegistryObject<Item> FRIES = ITEMS.register("fries", () -> new ModFood(6, 8F).setIndex(80));
    public static final RegistryObject<Item> DRUMSTICK = ITEMS.register("drumstick", () -> new ModFood(7, 9.6F).setIndex(81));
    public static final RegistryObject<Item> FRIED_CHICKEN_COMBO = ITEMS.register("fried_chicken_combo", () -> new ModFood(15, 30).setIndex(82));
    public static final RegistryObject<Item> RAW_VEGAN_BEEF = ITEMS.register("raw_vegan_beef", () -> new ModFood(3, 1.8F).setIndex(129));
    public static final RegistryObject<Item> COOKED_VEGAN_BEEF = ITEMS.register("cooked_vegan_beef", () -> new ModFood(8, 12.8F).setIndex(130));
    public static final RegistryObject<Item> RAW_VEGAN_PORK = ITEMS.register("raw_vegan_pork", () -> new ModFood(3, 0.6F).setIndex(131));
    public static final RegistryObject<Item> COOKED_VEGAN_PORK = ITEMS.register("cooked_vegan_pork", () -> new ModFood(8, 12.8F).setIndex(132));
    public static final RegistryObject<Item> RAW_VEGAN_MUTTON = ITEMS.register("raw_vegan_mutton", () -> new ModFood(2, 1.2F).setIndex(133));
    public static final RegistryObject<Item> COOKED_VEGAN_MUTTON = ITEMS.register("cooked_vegan_mutton", () -> new ModFood(6, 8.2F).setIndex(134));
    public static final RegistryObject<Item> GEM_CARROT = ITEMS.register("gem_carrot", () -> new ModFood(6, 8F).setIndex(12));
    public static final RegistryObject<Item> CRISPY_BREAD_WITH_KELP = ITEMS.register("crispy_bread_with_kelp", () -> new ModFood(6, 8F).setIndex(44));
    public static final RegistryObject<Item> RAW_BEEF_IN_DRIPLEAF = ITEMS.register("raw_beef_in_dripleaf", () -> new ModFood(4,4).setIndex(24));
    public static final RegistryObject<Item> SWEET_ROLL = ITEMS.register("sweet_roll", () -> new ModFood(11,1.1F).setIndex(38));


    public static final RegistryObject<Item> SOOTHING_TEA = ITEMS.register("soothing_tea", () -> ModFood.drinkItem(1,3.6F).setDuration(ModFood.Duration.Slow).setIndex(62));
    public static final RegistryObject<Item> BIRCH_SAP = ITEMS.register("birch_sap", () -> ModFood.drinkItem(1, 2).setIndex(20));
    public static final RegistryObject<Item> ICED_MELON_LAGER = ITEMS.register("iced_melon_lager", () -> ModFood.drinkItem(3, 2.4F, EffectEntry.of(MobEffects.FIRE_RESISTANCE, 300, 2, 1)).setIndex(109));
    public static final RegistryObject<Item> GLOW_BERRY_LAGER = ITEMS.register("glow_berry_lager", () -> ModFood.drinkItem(2, 2.4F, EffectEntry.of(MobEffects.GLOWING, 300, 2, 1)).setIndex(110));
    public static final RegistryObject<Item> ACORN_WINE = ITEMS.register("acorn_wine", () -> ModFood.drinkItem(2, 5.2F).setIndex(115));
    public static final RegistryObject<Item> MEAD = ITEMS.register("mead", () -> ModFood.drinkItem(1, 0F).setIndex(116));
    public static final RegistryObject<Item> RUM = ITEMS.register("rum", () -> ModFood.drinkItem(1, 0F, EffectEntry.of(MobEffects.DOLPHINS_GRACE, 300, 1)).setIndex(117));
    public static final RegistryObject<Item> VODKA = ITEMS.register("vodka", () -> ModFood.drinkItem(1, 0F, EffectEntry.of(MobEffects.DAMAGE_BOOST, 300, 1)).setIndex(118));

    public static final RegistryObject<Item> KELP_WITH_SUNFLOWER_SEED = ITEMS.register("kelp_with_sunflower_seed", () -> new ModFood(2, 2f).setDuration(ModFood.Duration.SunflowerKelp).setIndex(21));
    public static final RegistryObject<Item> FRIED_PUMPKIN_CAKE = ITEMS.register("fried_pumpkin_cake", () -> new ModFood(4, 3.2F, 16).setDuration(ModFood.Duration.Fast).setIndex(22));
    public static final RegistryObject<Item> SEED_PIE = ITEMS.register("seed_pie", () -> new ModFood(4, 2.4F, 16).setDuration(ModFood.Duration.Fast).setIndex(23));
    public static final RegistryObject<Item> RICE_CAKE = ITEMS.register("rice_cake", () -> new ModFood(4, 4, 16).setDuration(ModFood.Duration.VeryFast).setIndex(85));
    public static final RegistryObject<Item> VERDANT_NAMA_CHOCO = ITEMS.register("leafy_fresh_chocolate", () -> new ModFood(2, 4.8F, 16).setDuration(ModFood.Duration.VeryFast).setIndex(86));
    public static final RegistryObject<Item> BACON_WRAPPED_POTATO = ITEMS.register("bacon_wrapped_potato", () -> new ModFood(5, 3.6F, 16).setDuration(ModFood.Duration.Fast).setIndex(43));

    public static final RegistryObject<Item> LIGHT_SODA = ITEMS.register("light_soda", () -> ModFood.drinkItem(1, 0).setDuration(ModFood.Duration.Fast).setIndex(119));
    public static final RegistryObject<Item> KELP_SODA = ITEMS.register("kelp_soda", () -> ModFood.drinkItem(1, 0F, EffectEntry.of(MobEffects.WATER_BREATHING, 180, 1)).setDuration(ModFood.Duration.Fast).setIndex(120));
    public static final RegistryObject<Item> TWISTING_SODA = ITEMS.register("twisting_soda", () -> ModFood.drinkItem(1, 0F, EffectEntry.of(MobEffects.ABSORPTION, 60, 1)).setDuration(ModFood.Duration.Fast).setIndex(121));
    public static final RegistryObject<Item> DANDELION_COKE = ITEMS.register("dandelion_coke", () -> CannedFoodItem.drinkItem(1, 0F, EffectEntry.of(MobEffects.MOVEMENT_SPEED, 180, 2, 1), EffectEntry.of(MobEffects.DIG_SPEED, 180, 1)).setDuration(ModFood.Duration.Fast).setIndex(122));
    public static final RegistryObject<Item> CORAL_COKE = ITEMS.register("coral_coke", () -> CannedFoodItem.drinkItem(1, 0F, EffectEntry.of(MobEffects.MOVEMENT_SPEED, 180, 1), EffectEntry.of(MobEffects.DIG_SPEED, 180, 2, 1)).setDuration(ModFood.Duration.Fast).setIndex(123));
    public static final RegistryObject<Item> DRAGON_BREATH_COKE = ITEMS.register("dragon_breath_coke", () -> CannedFoodItem.drinkItem(1, 3F, EffectEntry.of(MobEffects.MOVEMENT_SPEED, 180, 2, 1), EffectEntry.of(MobEffects.DIG_SPEED, 180, 2, 1)).setDuration(ModFood.Duration.Fast).setIndex(124));

    public static final RegistryObject<Item> FISHERMENS_DELIGHT = ITEMS.register("fishermens_delight", () -> new ModFood(10, 10.4F).setBowlFood().setIndex(46));
    public static final RegistryObject<Item> LUSH_SALAD = ITEMS.register("lush_salad", () -> new ModFood(7, 6.4F).setBowlFood().setIndex(50));
    public static final RegistryObject<Item> FRESH_SALAD = ITEMS.register("fresh_salad", () -> new ModFood(6, 4).setBowlFood().setIndex(51));
    public static final RegistryObject<Item> TRAVELERS_SALAD = ITEMS.register("travelers_salad", () -> new ModFood(5, 3.2F).setBowlFood().setIndex(52));
    public static final RegistryObject<Item> FRUIT_CEREAL_PORRIDGE = ITEMS.register("fruit_cereal_porridge", () -> new ModFood(7, 9.6F).setBowlFood().setIndex(55));
    public static final RegistryObject<Item> CREEPER_CEREAL_PORRIDGE = ITEMS.register("creeper_cereal_porridge", () -> new ModFood(7, 9.6F).setBowlFood().setIndex(56));
    public static final RegistryObject<Item> BEETROOT_SALAD = ITEMS.register("beetroot_salad", () -> new ModFood(6, 3.6F).setBowlFood().setIndex(53));
    public static final RegistryObject<Item> WOODLAND_TATER_PUREE = ITEMS.register("woodland_tater_puree", () -> new ModFood(7, 6.4F).setBowlFood().setIndex(45));
    public static final RegistryObject<Item> CARROT_AND_CARROT = ITEMS.register("carrot_and_carrot", () -> new ModFood(10, 10F).setBowlFood().setIndex(61));


    public static final RegistryObject<Item> CANNED_MUTTON_PUMPKIN = ITEMS.register("canned_mutton_pumpkin", () -> new InstantFoodItem(5, 9.6F).setIndex(99));
    public static final RegistryObject<Item> CANNED_PORK_BEETROOT = ITEMS.register("canned_pork_beetroot", () -> new InstantFoodItem(5, 9.6F).setIndex(100));
    public static final RegistryObject<Item> CANNED_CANDIED_APPLE = ITEMS.register("canned_candied_apple", () -> new InstantFoodItem(4, 10.8F).setIndex(102));
    public static final RegistryObject<Item> CANNED_BEEF_POTATO = ITEMS.register("canned_beef_potato", () -> new InstantFoodItem(5, 9.6F).setIndex(101));
    public static final RegistryObject<Item> CANNED_SWEET_BERRY_MILK = ITEMS.register("canned_sweet_berry_milk", () -> new InstantFoodItem(2, 3.6F, EffectEntry.of(MobEffects.INVISIBILITY, 90, 1)).setIndex(103));
    public static final RegistryObject<Item> CANNED_HOGLIN_CONFIT = ITEMS.register("canned_hoglin_confit", () -> new InstantFoodItem(6, 10.8F).setIndex(104));

    public static final RegistryObject<Item> CREAM_OF_MUSHROOM_SOUP = ITEMS.register("cream_of_mushroom_soup", () -> new ModFood(8, 9.2F).setBowlFood().setIndex(47));

    public static final RegistryObject<Item> SWEET_BERRY_MILK = ITEMS.register("sweet_berry_milk", () -> new ModFood(4, 4.8F, EffectEntry.of(MobEffects.INVISIBILITY, 60, 1)).setIndex(42));

    public static final RegistryObject<Item> ULTRA_SUPER_DELICIOUS_CEREAL_PORRIDGE = ITEMS.register("ultra_super_delicious_cereal_porridge", () -> new ModFood(FoodUtil.food(defaultProperties(), 20, 100F).rarity(Rarity.UNCOMMON).stacksTo(16)).setIndex(57));

    public static final RegistryObject<Item> CURRY_UDON = ITEMS.register("curry_udon", () -> new ModFood(FoodUtil.effectFood(defaultProperties(), 18, 16F, false, EffectEntry.of(MobEffects.REGENERATION, 60, 0, 1)).stacksTo(1).rarity(Rarity.RARE)).setEffectEntries(new EffectEntry[]{EffectEntry.of(MobEffects.REGENERATION, 60, 0, 1)}).setIndex(84));
    public static final RegistryObject<Item> POPACORN = ITEMS.register("popacorn", () -> new PopacornItem().setIndex(83));

    public static final RegistryObject<Item> EMPTY_PLATE = ITEMS.register("plate_item", () -> new EmptyPlateItem(defaultProperties()).setIndex(1));

    public static final RegistryObject<Item> FOOD_FILLED_PLATE = ITEMS.register("plate", () -> new FilledPlateItem(ModBlocks.PLATE.get(), new Item.Properties()));

    public static final RegistryObject<Item> KNIFE = ITEMS.register("knife", ()->new KnifeItem(){{setIndex(2);}});

    public static final RegistryObject<Item> SHAKER = ITEMS.register("shaker", ShakerItem::new);

    public static final RegistryObject<Item> RUM_BASE = ITEMS.register("rum_base", BaseLiquorItem::new);
    public static final RegistryObject<Item> MEAD_BASE = ITEMS.register("mead_base", BaseLiquorItem::new);
    public static final RegistryObject<Item> VODKA_BASE = ITEMS.register("vodka_base", BaseLiquorItem::new);
    public static final RegistryObject<Item> ACORN_WINE_BASE = ITEMS.register("acorn_wine_base", BaseLiquorItem::new);

    public static final RegistryObject<Item> COCKTAIL = ITEMS.register("cocktail", CocktailItem::new);
    public static final RegistryObject<Item> WATER = ITEMS.register("water", () -> new ModItem(defaultProperties(),15));
    public static final RegistryObject<Item> MILK = ITEMS.register("milk", () -> new ModItem(defaultProperties(),16));
    public static final RegistryObject<Item> EMPTY_CAN = ITEMS.register("empty_can", () -> new ModItem(defaultProperties(),18));
    public static final RegistryObject<Item> ICE_CUBES = ITEMS.register("ice_cubes", () -> new ModItem(defaultProperties(),14));
    public static final RegistryObject<Item> CARROT_SPICES = ITEMS.register("carrot_spices", () -> new ModItem(defaultProperties(),13));
    public static final RegistryObject<Item> ACORN = ITEMS.register("acorn", () -> new ModItem(FoodUtil.food(defaultProperties(), 2, 1F),17));
    public static final RegistryObject<Item> DUNGEON_PIZZA = ITEMS.register("dungeon_pizza", () -> new ModItem(defaultProperties(),71));
    public static final RegistryObject<Item> FEAST_PIZZA = ITEMS.register("feast_pizza", () -> new ModItem(defaultProperties(),67));
    public static final RegistryObject<Item> SHINY_PIZZA = ITEMS.register("shiny_pizza", () -> new ModItem(defaultProperties(),69));
    public static final RegistryObject<Item> RAW_SWEET_LOAF = ITEMS.register("raw_sweet_loaf", () -> new ModItem(defaultProperties(),73));
    public static final RegistryObject<Item> PLATE_PIECES = ITEMS.register("plate_pieces", () -> new ModItem(defaultProperties(),500));

    public static Item.Properties defaultProperties() {
        return new Item.Properties().tab(MAIN_TAB);
    }

    public static Item.Properties cocktailProperties() {
        return new Item.Properties().tab(COCKTAIL_TAB);
    }


    public static final CreativeModeTab COCKTAIL_TAB = new CreativeModeTab(MOD_ID + ".cocktail") {
        @NotNull
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.SHAKER.get());
        }
    };
    public static final CreativeModeTab MAIN_TAB = new CreativeModeTab(MOD_ID + ".main") {
        @NotNull
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.CARROT_SPICES.get());
        }

        @Override
        public void fillItemList(NonNullList<ItemStack> pItems) {
            super.fillItemList(pItems);
            pItems.sort(Comparator.comparing(itemStack -> sorter(itemStack)));
        }
        private static int sorter(ItemStack itemStack){
            if (itemStack.getItem() instanceof ModItem) {
                ModItem item = (ModItem) itemStack.getItem();
                return item.getIndex();
            }else return 0;
        }
    };

}
