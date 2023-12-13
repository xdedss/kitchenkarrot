package io.github.tt432.kitchenkarrot.registries;

import io.github.tt432.kitchenkarrot.client.cocktail.CocktailList;
import io.github.tt432.kitchenkarrot.item.CocktailItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static io.github.tt432.kitchenkarrot.Kitchenkarrot.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModTabs {

    @SubscribeEvent
    public static void addCreative(CreativeModeTabEvent.Register reg) {

        reg.registerCreativeModeTab(new ResourceLocation(MOD_ID, "cocktail"), builder ->
        // Set name of tab to display
        builder.title(Component.translatable("itemGroup.kitchenkarrot.cocktail"))
                // Set icon of creative tab
                .icon(() -> new ItemStack(ModItems.SHAKER.get()))
                // Add default items to tab
                .displayItems((params, event) -> {
                    event.accept(ModItems.SHAKER.get());
                    event.accept(ModItems.ACORN_WINE_BASE.get());
                    event.accept(ModItems.MEAD_BASE.get());
                    event.accept(ModItems.RUM_BASE.get());
                    event.accept(ModItems.VODKA_BASE.get());
                    for (String cocktail : CocktailList.INSTANCE.cocktails) {
                        ItemStack itemStack = new ItemStack(ModItems.COCKTAIL.get());
                        CocktailItem.setCocktail(itemStack, new ResourceLocation(cocktail));
                        event.accept(itemStack);
                    }
                })

        );

        reg.registerCreativeModeTab(new ResourceLocation(MOD_ID, "main"), builder ->
        // Set name of tab to display
        builder.title(Component.translatable("itemGroup.kitchenkarrot.main"))
                // Set icon of creative tab
                .icon(() -> new ItemStack(ModItems.CARROT_SPICES.get()))
                // Add default items to tab
                // Add default items to tab
                .displayItems((params, event) -> {
                    event.accept(ModItems.EMPTY_PLATE.get());
                    event.accept(ModItems.KNIFE.get());
                    event.accept(ModBlocks.AIR_COMPRESSOR.get());
                    event.accept(ModBlocks.BREWING_BARREL.get());
                    event.accept(ModBlocks.COASTER.get());
                    event.accept(ModItems.GEM_CARROT.get());
                    event.accept(ModItems.CARROT_SPICES.get());
                    event.accept(ModItems.ICE_CUBES.get());
                    event.accept(ModItems.ACORN.get());
                    event.accept(ModItems.EMPTY_CAN.get());
                    event.accept(ModBlocks.SEA_SALT.get());
                    event.accept(ModBlocks.ROCK_SALT.get());
                    event.accept(ModBlocks.FINE_SALT.get());
                    event.accept(ModBlocks.ACORN_OIL.get());
                    event.accept(ModBlocks.SUNFLOWER_OIL.get());
                    event.accept(ModBlocks.CHORUS_OIL.get());
                    event.accept(ModItems.WATER.get());
                    event.accept(ModItems.MILK.get());

                    event.accept(ModItems.BIRCH_SAP.get());
                    event.accept(ModItems.KELP_WITH_SUNFLOWER_SEED.get());
                    event.accept(ModItems.FRIED_PUMPKIN_CAKE.get());
                    event.accept(ModItems.SEED_PIE.get());
                    event.accept(ModItems.RAW_BEEF_IN_DRIPLEAF.get());
                    event.accept(ModItems.BEEF_IN_DRIPLEAF.get());
                    event.accept(ModItems.SMALL_BEEF_IN_DRIPLEAF.get());
                    event.accept(ModItems.BAMBOO_POTATO.get());
                    event.accept(ModItems.PICKLED_SEA_PICKLES.get());
                    event.accept(ModItems.BIRCH_SAP_CHOCOLATE_BAR.get());
                    event.accept(ModItems.CHOCOLATE_CROISSANT.get());
                    event.accept(ModItems.BEETROOT_CREPE.get());
                    event.accept(ModItems.CHINESE_CREPE.get());
                    event.accept(ModItems.CROQUE_MADAME.get());
                    event.accept(ModItems.GRILLED_WHEATMEAL.get());
                    event.accept(ModItems.GRILLED_FISH_AND_CACTUS.get());
                    event.accept(ModItems.FLOWER_CAKE.get());
                    event.accept(ModItems.PILLAGER_PIE.get());
                    event.accept(ModItems.SWEET_ROLL.get());
                    event.accept(ModItems.MONSTER_LASAGNA.get());
                    event.accept(ModItems.SMALL_MONSTER_LASAGNA.get());
                    event.accept(ModItems.SWEET_BERRY_MILK.get());
                    event.accept(ModItems.BACON_WRAPPED_POTATO.get());
                    event.accept(ModItems.CRISPY_BREAD_WITH_KELP.get());
                    event.accept(ModItems.WOODLAND_TATER_PUREE.get());
                    event.accept(ModItems.FISHERMENS_DELIGHT.get());
                    event.accept(ModItems.CREAM_OF_MUSHROOM_SOUP.get());

                    event.accept(ModItems.LUSH_SALAD.get());
                    event.accept(ModItems.FRESH_SALAD.get());
                    event.accept(ModItems.TRAVELERS_SALAD.get());
                    event.accept(ModItems.BEETROOT_SALAD.get());
                    event.accept(ModItems.FRUIT_CEREAL_PORRIDGE.get());
                    event.accept(ModItems.CREEPER_CEREAL_PORRIDGE.get());
                    event.accept(ModItems.ULTRA_SUPER_DELICIOUS_CEREAL_PORRIDGE.get());

                    event.accept(ModItems.CARROT_AND_CARROT.get());
                    event.accept(ModItems.SOOTHING_TEA.get());
                    event.accept(ModItems.CHORUS_MOUSSE.get());
                    event.accept(ModItems.SMALL_CHORUS_MOUSSE.get());
                    event.accept(ModItems.SLIME_MOUSSE.get());
                    event.accept(ModItems.SMALL_SLIME_MOUSSE.get());
                    event.accept(ModItems.FEAST_PIZZA.get());
                    event.accept(ModItems.FEAST_PIZZA_SLICE.get());
                    event.accept(ModItems.SHINY_PIZZA.get());
                    event.accept(ModItems.SHINY_PIZZA_SLICE.get());
                    event.accept(ModItems.DUNGEON_PIZZA.get());
                    event.accept(ModItems.DUNGEON_PIZZA_SLICE.get());
                    event.accept(ModItems.RAW_SWEET_LOAF.get());
                    event.accept(ModItems.SWEET_LOAF.get());
                    event.accept(ModItems.SWEET_LOAF_SLICE.get());
                    event.accept(ModItems.SIRLOIN_STEAK.get());
                    event.accept(ModItems.BEEF_GRAINS.get());
                    event.accept(ModItems.SASHIMI.get());
                    event.accept(ModItems.MOSS_FRIED_LAMB_CUTLETS.get());
                    event.accept(ModItems.FRIES.get());
                    event.accept(ModItems.DRUMSTICK.get());
                    event.accept(ModItems.FRIED_CHICKEN_COMBO.get());
                    event.accept(ModItems.POPACORN.get());
                    event.accept(ModItems.CURRY_UDON.get());
                    event.accept(ModItems.RICE_CAKE.get());
                    event.accept(ModItems.VERDANT_NAMA_CHOCO.get());
                    event.accept(ModItems.HONEY_BRULEE.get());
                    event.accept(ModItems.LAVA_BRULEE.get());
                    event.accept(ModItems.HI_NRG_BRULEE.get());
                    event.accept(ModItems.EGG_TART.get());
                    event.accept(ModItems.SWEET_BERRY_TART.get());
                    event.accept(ModItems.CARROT_TART.get());

                    event.accept(ModItems.CANNED_MUTTON_PUMPKIN.get());
                    event.accept(ModItems.CANNED_PORK_BEETROOT.get());
                    event.accept(ModItems.CANNED_BEEF_POTATO.get());
                    event.accept(ModItems.CANNED_CANDIED_APPLE.get());
                    event.accept(ModItems.CANNED_SWEET_BERRY_MILK.get());
                    event.accept(ModItems.CANNED_HOGLIN_CONFIT.get());

                    event.accept(ModItems.ICED_MELON_LAGER.get());
                    event.accept(ModItems.GLOW_BERRY_LAGER.get());

                    event.accept(ModItems.ACORN_WINE.get());
                    event.accept(ModItems.MEAD.get());
                    event.accept(ModItems.RUM.get());
                    event.accept(ModItems.VODKA.get());
                    event.accept(ModItems.LIGHT_SODA.get());
                    event.accept(ModItems.KELP_SODA.get());
                    event.accept(ModItems.TWISTING_SODA.get());
                    event.accept(ModItems.DANDELION_COKE.get());
                    event.accept(ModItems.CORAL_COKE.get());
                    event.accept(ModItems.DRAGON_BREATH_COKE.get());

                    event.accept(ModItems.RAW_VEGAN_MUTTON.get());
                    event.accept(ModItems.COOKED_VEGAN_MUTTON.get());
                    event.accept(ModItems.RAW_VEGAN_PORK.get());
                    event.accept(ModItems.COOKED_VEGAN_PORK.get());
                    event.accept(ModItems.RAW_VEGAN_BEEF.get());
                    event.accept(ModItems.COOKED_VEGAN_BEEF.get());

                    event.accept(ModItems.PLATE_PIECES.get());
                })

        );
    }
}
