package io.github.tt432.kitchenkarrot.item;

import io.github.tt432.kitchenkarrot.registries.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PopacornItem extends ModFood {
    public PopacornItem() {
        super(FoodUtil.food(ModItems.defaultProperties(), 2, 3.2F).stacksTo(1).defaultDurability(8));
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull LivingEntity livingEntity) {
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
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> tooltip, TooltipFlag pIsAdvanced) {
        tooltip.add(Component.translatable("info.kitchenkarrot.popacorn", 8 - pStack.getDamageValue()));
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 24;
    }
}
