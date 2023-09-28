package io.github.tt432.kitchenkarrot.item;

import io.github.tt432.kitchenkarrot.registries.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MeowTranslator extends Item {
    private static final Vec3 MissionPos = new Vec3(-431f, 93f, 124f);
    private static final Vec3 Midori = new Vec3(-406f, 94f, 37f);
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
    private String current = null;
    private int dialogStage = 0;
    private int randomStage;
    public MeowTranslator(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.literal("原理不明但是似乎管用的猫咪语翻译器"));
        tooltip.add(Component.literal("对胡萝卜岛喵喵泉的那只名为桃井的猫有效"));
        if (current != null) {
            tooltip.add(Component.literal("桃井现在想喝：").append(Component.translatable(current.replace(':', '.')).withStyle(ChatFormatting.RED).withStyle(ChatFormatting.BOLD)));
        }
    }

    @Override
    @NotNull
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (true) {
            long time = level.getGameTime();
            if (lastRespondTime + 10 < time) {
                if (isInRange(player, MissionPos)) {
                    talk(level, player);
                    if (!level.isClientSide) lastRespondTime = time;
                    return InteractionResultHolder.success(itemStack);
                }
                if (isInRange(player, Midori) && level.isClientSide) {
                    level.playSound(player, player.blockPosition(), SoundEvents.CAT_AMBIENT, SoundSource.MASTER);
                    sendMessage(MutableComponent.create(new LiteralContents(""))
                            .append(Component.literal("绿：").withStyle(Style.EMPTY.withColor(TextColor.fromLegacyFormat(ChatFormatting.GREEN))))
                            .append(MutableComponent.create(new LiteralContents("喵！~")).withStyle(Style.EMPTY)));
                    return InteractionResultHolder.success(itemStack);
                }
            }
        }
        return InteractionResultHolder.pass(itemStack);
    }
    private void reward(Level level, Player player) {
        //ServerLevel

    }
    private void talk(Level level, Player player) {
        level.playSound(player, player.blockPosition(), SoundEvents.CAT_AMBIENT, SoundSource.MASTER);
        if (level.isClientSide) return;
        if (current != null) {
            if (removeCocktail(player)) {
                randomStage = level.getRandom().nextInt(4);
                switchCurrent(level);
                sendMessage(player, Component.literal("*收到了妙鲜包猫罐头x1"));
                switch (randomStage) {
                    case 0, 1 -> sendMessage(player,catLine("嗯嗯！就是这个味道！好好收下我的奖励吧！"));
                    case 2, 3 -> sendMessage(player,catLine("啊~果然是美味……这个送给你……但是还不够！"));
                }
                player.playNotifySound(SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1f, 1f);
                player.getInventory().add(new ItemStack(ModItems.CANNED_CAT_FOOD.get()));
            }
            switch (randomStage) {
                case 0 -> sendMessage(player,threePartsComponent("现在我想要一杯", "，快给本喵拿来！"));
                case 1 -> sendMessage(player,threePartsComponent("嗯……", "的味道怎么样？摇一杯我尝尝！"));
                case 2 -> sendMessage(player,threePartsComponent("我想喝", "了！快点行动起来吧~"));
                case 3 -> sendMessage(player,threePartsComponent("我换口味了！我决定要喝", "，给我给我给我！"));
            }
        } else {
            dialogStage++;
            switch (dialogStage) {
                case 1 -> sendMessage(player,catLine("哇啊！你也能听懂我说话！"));
                case 2 -> sendMessage(player,catLine("嗯……你只要给我拿杯鸡尾酒来，我就给你我的宝物哦。"));
                case 3 -> {
                    dialogStage = 0;
                    current = list.get(level.getRandom().nextInt(list.size()));
                    sendMessage(player,threePartsComponent("摇出一杯", "再找我吧！不会亏待你的！"));
                }
            }
        }
    }
    private void switchCurrent(Level level) {
        List<String> temp = new ArrayList<>(List.copyOf(list));
        if (current != null) temp.remove(current);
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

    private boolean isInRange(Player player, Vec3 pos) {
        Vec3 playerPos = player.getEyePosition();
        return playerPos.distanceTo(pos) < 8;
    }
    private MutableComponent catLine(String string) {
        return MutableComponent.create(new LiteralContents(""))
                .append(Component.literal("桃井：").withStyle(Style.EMPTY.withColor(TextColor.fromLegacyFormat(ChatFormatting.GOLD))))
                .append(MutableComponent.create(new LiteralContents(string)).withStyle(Style.EMPTY));
    }
    private Component threePartsComponent(String front, String back) {
        return catLine(front)
                .append(Component.translatable(current.replace(':', '.')).withStyle(ChatFormatting.RED).withStyle(ChatFormatting.BOLD))
                .append(Component.literal(back)).withStyle(Style.EMPTY);
    }
    private void sendMessage(Player player, Component component) {
        player.sendSystemMessage(component);
    }
    private void sendMessage(Component component) {
        Minecraft.getInstance().gui.getChat().addMessage(component);
    }
}
