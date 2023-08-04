package io.github.tt432.kitchenkarrot.item;

import io.github.tt432.kitchenkarrot.config.ModCommonConfigs;
import io.github.tt432.kitchenkarrot.entity.CanEntity;
import io.github.tt432.kitchenkarrot.registries.ModEntities;
import io.github.tt432.kitchenkarrot.registries.ModItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class InstantFoodItem extends ModFood {
    public InstantFoodItem(int nutrition, float saturation, EffectEntry... effectEntries) {
        super(FoodUtil.effectFood(ModItems.defaultProperties(), nutrition, saturation,false, effectEntries));
    }

    @Override
    public int getUseDuration(@NotNull ItemStack pStack) {
        return 1;
    }

    @Override
    @NotNull
    public ItemStack finishUsingItem(@NotNull ItemStack itemStack, @NotNull Level level, LivingEntity livingEntity) {
        if (ModCommonConfigs.CAN_ENTITY_SPAWN.get()){
            CanEntity canEntity = new CanEntity(ModEntities.CAN.get(), level);
            canEntity.setPos(livingEntity.position());
            canEntity.moveRelative(1, livingEntity.getLookAngle());
            level.addFreshEntity(canEntity);
        }
        return super.finishUsingItem(itemStack, level, livingEntity);
    }

}
