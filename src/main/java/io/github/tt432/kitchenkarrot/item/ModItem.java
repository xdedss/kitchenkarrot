package io.github.tt432.kitchenkarrot.item;

import io.github.tt432.kitchenkarrot.block.PlateHolderMap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/*
    Currently used ONLY as a makeshift of item tooltip mixin before really fixing the render&interact problem.
 */
public class ModItem extends Item {
    public ModItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, level, tooltip, tooltipFlag);
        if (PlateHolderMap.canPutOnPlate(stack.getItem())) {
            tooltip.add(Component.translatable("info.kitchenkarrot.can_be_dished"));
        }
    }
}
