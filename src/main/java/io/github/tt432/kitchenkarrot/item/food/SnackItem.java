package io.github.tt432.kitchenkarrot.item.food;

import io.github.tt432.kitchenkarrot.item.ModFood;
import io.github.tt432.kitchenkarrot.item.ModItems;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SnackItem extends ModFood {
    int tick;

    public SnackItem(int nutrition, float saturation, int tick) {
        super(FoodUtil.food(ModItems.defaultProperties(), nutrition, saturation).stacksTo(16));
        this.tick = tick;
    }
    public SnackItem(int nutrition, float saturation, int tick, int stackSize) {
        super(FoodUtil.food(ModItems.defaultProperties(), nutrition, saturation).stacksTo(stackSize));
        this.tick = tick;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack pStack) {
        return tick;
    }
}
