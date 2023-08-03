package io.github.tt432.kitchenkarrot.item;


import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import java.util.List;

import static io.github.tt432.kitchenkarrot.block.PlateHolderMap.plateHolder;

public class ModFood extends Item {
    private UseAnim foodType = UseAnim.EAT;
    private Duration duration = Duration.Normal;


    public ModFood(Properties pProperties) {
        super(pProperties);
    }
    private boolean canPutOnPlate(){
        return plateHolder.containsKey(this);
    }


    @Override
    public void appendHoverText(ItemStack pStack, Level pLevel, List<Component> tooltip, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, tooltip, pIsAdvanced);
        if (this.canPutOnPlate()){
            tooltip.add(Component.translatable("info.kitchenkarrot.can_be_dished"));
        }
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return foodType;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return duration.time;
    }

    public ModFood setFoodType(UseAnim foodType) {
        this.foodType = foodType;
        return this;
    }

    public ModFood setDuration(Duration duration) {
        this.duration = duration;
        return this;
    }

    enum Duration {
        VeryFast(16), Fast(24), Normal(32), Slow(48), VerySlow(64);
        private final int time;

        Duration(int duration) {
            this.time = duration;
        }
    }
}
