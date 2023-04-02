package io.github.tt432.kitchenkarrot.item.food;

import io.github.tt432.kitchenkarrot.item.IndexItem;
import io.github.tt432.kitchenkarrot.item.ModItems;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SnackItem extends IndexItem {
    int tick;

    public SnackItem(int nutrition, float saturation, int tick) {
        super(FoodUtil.food(ModItems.defaultProperties(), nutrition, saturation).stacksTo(16));
        this.tick = tick;
    }

    @Override
    public SnackItem setIndex(int index) {
        super.setIndex(index);
        return this;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack pStack) {
        return tick;
    }
}
