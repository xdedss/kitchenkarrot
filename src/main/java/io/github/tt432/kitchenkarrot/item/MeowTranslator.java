package io.github.tt432.kitchenkarrot.item;

import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class MeowTranslator extends Item {
    private static final Vec3 MissionPos = new Vec3(0f, 0f, 0f);
    private long lastRespondTime;
    private final List<String> list = List.of(
            "kitchenkarrot:cocktails/sweet_berry_martini",
            "kitchenkarrot:cocktails/shanghai_beach",
            "kitchenkarrot:cocktails/red_lizard",
            "kitchenkarrot:cocktails/july_21",
            "kitchenkarrot:cocktails/black_pearl",
            "kitchenkarrot:cocktails/nebula_chronicles",
            "kitchenkarrot:cocktails/jacks_story",
            "kitchenkarrot:cocktails/yura_punk",
            "kitchenkarrot:cocktails/tsundere_heroine",
            "kitchenkarrot:cocktails/milk_acorn_wine",
            "kitchenkarrot:cocktails/twilight_forest",
            "kitchenkarrot:cocktails/birch_sap_vodka"
    );
    private String current = "kitchenkarrot:cocktails/sweet_berry_martini";
    public MeowTranslator(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        if (current != null) {
            tooltip.add(Component.literal("大臣现在想喝："));
            tooltip.add(Component.translatable(current.replace(':', '.')));
        }
    }

    @Override
    @NotNull
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            long time = level.getGameTime();
            if (lastRespondTime + 10 < time) {
                if (isInRange(player)) {
                    talk(level, player);
                    lastRespondTime = time;
                    return InteractionResultHolder.success(itemStack);
                }
            }
        }
        return InteractionResultHolder.pass(itemStack);
    }
    private void talk(Level level, Player player) {
        if (removeCocktail(player)) {
            switchCurrent(level);
            player.sendSystemMessage(Component.literal("true"));
        } else {
            player.sendSystemMessage(Component.literal("false"));
        }
    }
    private void switchCurrent(Level level) {
        List<String> temp = new ArrayList<>(List.copyOf(list));
        temp.remove(current);
        current = temp.get(level.getRandom().nextInt(temp.size()));
    }
    private boolean removeCocktail(Player player) {
        Inventory inventory = player.getInventory();
        for(int i = 0; i < inventory.getContainerSize(); ++i) {
            ItemStack itemstack = inventory.getItem(i);
            if (!itemstack.isEmpty() && cocktailPredicate(itemstack)) {
                itemstack.shrink(1);
                return true;
            }
        }
        return false;
    }
    private boolean cocktailPredicate(ItemStack cocktailStack) {
        String cocktail = "";
        if (cocktailStack.getTag() != null && cocktailStack.getTag().contains("cocktail")) {
            cocktail = cocktailStack.getTag().getString("cocktail");
        }
        return cocktail.equals(current);
    }

    private boolean isInRange(Player player) {
        Vec3 playerPos = player.getEyePosition();
        return playerPos.distanceTo(MissionPos) < 8;
    }
}
