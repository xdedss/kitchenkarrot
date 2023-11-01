package io.github.tt432.kitchenkarrot.item;


import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import io.github.tt432.kitchenkarrot.registries.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.github.tt432.kitchenkarrot.block.PlateHolderMap.plateHolder;

public class ModFood extends ModItem {
    protected UseAnim foodType = UseAnim.EAT;
    protected Duration duration = Duration.Normal;
    protected EffectEntry[] effectEntries;
    protected Item returnItem;


    public ModFood(Properties pProperties) {
        super(pProperties);
    }
    public ModFood(int nutrition, float saturation, EffectEntry... effectEntries) {
        this(nutrition, saturation, false, effectEntries);
        this.effectEntries = effectEntries;
    }
    public ModFood(int nutrition, float saturation, boolean alwaysEat, EffectEntry... effectEntries) {
        super(FoodUtil.effectFood(ModItems.defaultProperties(), nutrition, saturation, alwaysEat, effectEntries));
        this.effectEntries = effectEntries;
    }
    public ModFood(int nutrition, float saturation, int stackSize, EffectEntry... effectEntries) {
        this(nutrition, saturation, stackSize, false, effectEntries);
        this.effectEntries = effectEntries;
    }
    public ModFood(int nutrition, float saturation, int stackSize, boolean alwaysEat, EffectEntry... effectEntries) {
        super(FoodUtil.effectFood(ModItems.defaultProperties(), nutrition, saturation, alwaysEat, effectEntries).stacksTo(stackSize));
        this.effectEntries = effectEntries;
    }
    public static ModFood drinkItem(int nutrition, float saturation, EffectEntry... effectEntries) {
        ModFood item = new ModFood(nutrition, saturation, true, effectEntries).setDrinkAnim();
        item.effectEntries = effectEntries;
        return item;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> tooltip, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, tooltip, pIsAdvanced);
        if (this.effectEntries != null && this.effectEntries.length > 0) {
            List<MobEffectInstance> list = Arrays.stream(this.effectEntries).map(effectEntry -> effectEntry.effect.get()).toList();
            List<Pair<Attribute, AttributeModifier>> list1 = Lists.newArrayList();
            for(MobEffectInstance mobeffectinstance : list) {
                MutableComponent mutablecomponent = Component.translatable(mobeffectinstance.getDescriptionId());
                MobEffect mobeffect = mobeffectinstance.getEffect();
                Map<Attribute, AttributeModifier> map = mobeffect.getAttributeModifiers();
                if (!map.isEmpty()) {
                    for(Map.Entry<Attribute, AttributeModifier> entry : map.entrySet()) {
                        AttributeModifier attributemodifier = entry.getValue();
                        AttributeModifier attributemodifier1 = new AttributeModifier(attributemodifier.getName(), mobeffect.getAttributeModifierValue(mobeffectinstance.getAmplifier(), attributemodifier), attributemodifier.getOperation());
                        list1.add(new Pair<>(entry.getKey(), attributemodifier1));
                    }
                }

                if (mobeffectinstance.getAmplifier() > 0) {
                    mutablecomponent = Component.translatable("potion.withAmplifier", mutablecomponent, Component.translatable("potion.potency." + mobeffectinstance.getAmplifier()));
                }

                if (mobeffectinstance.getDuration() > 20) {
                    mutablecomponent = Component.translatable("potion.withDuration", mutablecomponent, MobEffectUtil.formatDuration(mobeffectinstance, 1));
                }

                tooltip.add(mutablecomponent.withStyle(mobeffect.getCategory().getTooltipFormatting()));
            }

            if (!list1.isEmpty()) {
                tooltip.add(CommonComponents.EMPTY);
                tooltip.add(Component.translatable("potion.whenDrank").withStyle(ChatFormatting.DARK_PURPLE));

                for(Pair<Attribute, AttributeModifier> pair : list1) {
                    AttributeModifier attributemodifier2 = pair.getSecond();
                    double d0 = attributemodifier2.getAmount();
                    double d1;
                    if (attributemodifier2.getOperation() != AttributeModifier.Operation.MULTIPLY_BASE && attributemodifier2.getOperation() != AttributeModifier.Operation.MULTIPLY_TOTAL) {
                        d1 = attributemodifier2.getAmount();
                    } else {
                        d1 = attributemodifier2.getAmount() * 100.0D;
                    }

                    if (d0 > 0.0D) {
                        tooltip.add(Component.translatable("attribute.modifier.plus." + attributemodifier2.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(d1), Component.translatable(pair.getFirst().getDescriptionId())).withStyle(ChatFormatting.BLUE));
                    } else if (d0 < 0.0D) {
                        d1 *= -1.0D;
                        tooltip.add(Component.translatable("attribute.modifier.take." + attributemodifier2.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(d1), Component.translatable(pair.getFirst().getDescriptionId())).withStyle(ChatFormatting.RED));
                    }
                }
            }

        }
    }

    @Override
    @NotNull
    public UseAnim getUseAnimation(ItemStack stack) {
        return foodType;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return duration.time;
    }

    @Override
    public ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, LivingEntity livingEntity) {
        stack = livingEntity.eat(level, stack);
        if (returnItem == null) return stack;
        if (livingEntity instanceof Player player) {
            player.getInventory().add(new ItemStack(returnItem));
        }
        return stack;
    }

    public ModFood setFoodType(@NotNull UseAnim foodType) {
        this.foodType = foodType;
        return this;
    }
    public ModFood setDrinkAnim() {
        setFoodType(UseAnim.DRINK);
        return this;
    }

    public ModFood setDuration(@NotNull Duration duration) {
        this.duration = duration;
        return this;
    }

    public ModFood setBowlFood() {
        setReturnItem(Items.BOWL);
        return this;
    }
    public ModFood setBottleFood() {
        setReturnItem(Items.GLASS_BOTTLE);
        return this;
    }
    public ModFood setReturnItem(@NotNull Item item) {
        this.returnItem = item;
        return this;
    }

    public ModFood setEffectEntries(EffectEntry[] effectEntries) {
        this.effectEntries = effectEntries;
        return this;
    }

    public enum Duration {
        SunflowerKelp(12), VeryFast(16), Fast(24), Normal(32), Slow(48), VerySlow(64), CurryUdon(96);
        private final int time;

        Duration(int duration) {
            this.time = duration;
        }
    }
}
