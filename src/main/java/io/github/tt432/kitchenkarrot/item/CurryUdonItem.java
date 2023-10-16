package io.github.tt432.kitchenkarrot.item;

import io.github.tt432.kitchenkarrot.registries.ModItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class CurryUdonItem extends ModItem {
    public CurryUdonItem(int nutrition, float saturation, EffectEntry effectEntry) {
        super(FoodUtil.effectFood(ModItems.defaultProperties(), nutrition, saturation, false, effectEntry).stacksTo(1).rarity(Rarity.RARE));
    }

    @Override
    public CurryUdonItem setIndex(int index) {
        super.setIndex(index);
        return this;
    }

    @Override
    public int getUseDuration(ItemStack itemStack) {
        return 108;
    }

    @Override
    @NotNull
    public ItemStack finishUsingItem(@NotNull ItemStack itemStack, @NotNull Level level, LivingEntity livingEntity) {
        itemStack = livingEntity.eat(level, itemStack);
        if (livingEntity instanceof Player player) {
            player.getInventory().add(new ItemStack(Items.BOWL));
        }
        return itemStack;
    }
}
