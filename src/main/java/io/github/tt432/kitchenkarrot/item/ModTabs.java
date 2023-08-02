package io.github.tt432.kitchenkarrot.item;

import io.github.tt432.kitchenkarrot.block.ModBlocks;
import io.github.tt432.kitchenkarrot.client.cocktail.CocktailList;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.stream.Collectors;

import static io.github.tt432.kitchenkarrot.Kitchenkarrot.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);
    public static final RegistryObject<CreativeModeTab> COCKTAIL_TAB = TABS.register("cocktail", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(ModItems.SHAKER.get()))
            .title(Component.translatable("itemGroup.kitchenkarrot.cocktail"))
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .build());
    public static final RegistryObject<CreativeModeTab> MAIN_TAB = TABS.register("main", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(ModItems.CARROT_SPICES.get()))
            .title(Component.translatable("itemGroup.kitchenkarrot.main"))
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .build());

    @SubscribeEvent
    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == ModTabs.COCKTAIL_TAB.get()) {
            for (String cocktail : CocktailList.INSTANCE.cocktails) {
                ItemStack itemStack = new ItemStack(ModItems.COCKTAIL.get());
                CocktailItem.setCocktail(itemStack, new ResourceLocation(cocktail));
                event.accept(itemStack);
            }
        }
        if (event.getTab() == ModTabs.MAIN_TAB.get()) {
            event.accept(ModItems.EMPTY_PLATE);
            event.accept(ModItems.KNIFE);
            event.accept(ModBlocks.AIR_COMPRESSOR);
            event.accept(ModBlocks.BREWING_BARREL);
            event.accept(ModBlocks.COASTER);
            event.accept(ModBlocks.SEA_SALT);
            event.accept(ModBlocks.ROCK_SALT);
            event.accept(ModBlocks.FINE_SALT);
            event.accept(ModBlocks.ACORN_OIL);
            event.accept(ModBlocks.SUNFLOWER_OIL);
            event.accept(ModBlocks.CHORUS_OIL);
            event.accept(ModItems.GEM_CARROT);
            event.accept(ModItems.CARROT_SPICES);
            event.accept(ModItems.ICE_CUBES);
            event.accept(ModItems.WATER);
            event.accept(ModItems.MILK);
            event.accept(ModItems.ACORN);
            event.accept(ModItems.EMPTY_CAN);
            event.accept(ModItems.BIRCH_SAP);
            event.accept(ModItems.KELP_WITH_SUNFLOWER_SEED);
            event.accept(ModItems.FRIED_PUMPKIN_CAKE);

            event.acceptAll(ModItems.ITEMS.getEntries()
                    .stream()
                    .filter(reg -> reg != ModItems.COCKTAIL && reg != ModItems.FOOD_FILLED_PLATE)
                    .map(RegistryObject::get)
                    .sorted((item, compare) -> {
                        if (item instanceof IndexItem indexItem) {
                            return indexItem.getIndex();
                        }
                        return 0;
                    })
                    .map(Item::getDefaultInstance)
                    .collect(Collectors.toList()));
            event.acceptAll(ModBlockItems.BLOCK_ITEMS.getEntries()
                    .stream()
                    .map(reg -> reg.get().getDefaultInstance())
                    .collect(Collectors.toList()));
        }
    }
}
