package io.github.tt432.kitchenkarrot.item.food;

import io.github.tt432.kitchenkarrot.item.IndexItem;
import io.github.tt432.kitchenkarrot.item.ModItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class PopacornItem extends IndexItem {
    public PopacornItem() {
        super(FoodUtil.food(ModItems.defaultProperties(), 2, 3.2F).stacksTo(1).defaultDurability(8));
    }

    @Override
    public ItemStack finishUsingItem(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull LivingEntity livingEntity) {
        if (livingEntity instanceof Player pl) {
            itemStack.hurtAndBreak(1,livingEntity,(player) -> player.broadcastBreakEvent(player.getUsedItemHand()));
            if (!pl.isCreative()) {
                itemStack.grow(1);
            }
        }
        super.finishUsingItem(itemStack,level,livingEntity);
        return itemStack;
    }



    @Override
    public PopacornItem setIndex(int index) {
        super.setIndex(index);
        return this;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 24;
    }
}
