package io.github.tt432.kitchenkarrot.item;

import io.github.tt432.kitchenkarrot.registries.ModItems;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class InstantFoodItem extends CannedFoodItem {
    public InstantFoodItem(int nutrition, float saturation, EffectEntry... effectEntries) {
        super(FoodUtil.effectFood(ModItems.defaultProperties(), nutrition, saturation,false, effectEntries));
    }
    public InstantFoodItem(int nutrition, float saturation, boolean alwaysEat, EffectEntry... effectEntries) {
        super(FoodUtil.effectFood(ModItems.defaultProperties(), nutrition, saturation,alwaysEat, effectEntries));
    }

    @Override
    public int getUseDuration(@NotNull ItemStack pStack) {
        return 1;
    }

}
