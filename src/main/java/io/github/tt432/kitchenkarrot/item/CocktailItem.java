package io.github.tt432.kitchenkarrot.item;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import io.github.tt432.kitchenkarrot.Kitchenkarrot;
import io.github.tt432.kitchenkarrot.client.cocktail.CocktailList;
import io.github.tt432.kitchenkarrot.config.ModCommonConfigs;
import io.github.tt432.kitchenkarrot.recipes.object.EffectStack;
import io.github.tt432.kitchenkarrot.recipes.recipe.CocktailRecipe;
import io.github.tt432.kitchenkarrot.registries.RecipeTypes;
import io.github.tt432.kitchenkarrot.registries.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

/**
 * @author DustW
 **/
@ParametersAreNonnullByDefault
public class CocktailItem extends Item {

    public static final ResourceLocation UNKNOWN_COCKTAIL = new ResourceLocation(Kitchenkarrot.MOD_ID, "unknown");

    public CocktailItem() {
        super(ModItems.defaultProperties().food(new FoodProperties.Builder().alwaysEat().build()));
    }

    @Override
    public void fillItemCategory(CreativeModeTab creativeModeTab, NonNullList<ItemStack> list) {
        if (creativeModeTab == ModItems.COCKTAIL_TAB) {
            for (String cocktail : CocktailList.INSTANCE.cocktails) {
                ItemStack stack = new ItemStack(this);
                setCocktail(stack, new ResourceLocation(cocktail));
                list.add(stack);
            }
        }
    }

    public static ItemStack unknownCocktail() {
        var stack = new ItemStack(ModItems.COCKTAIL.get());
        setCocktail(stack, UNKNOWN_COCKTAIL);
        return stack;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level pLevel, LivingEntity livingEntity) {
        if (livingEntity instanceof ServerPlayer player) {
            ResourceLocation cocktail = getCocktail(stack);
            if (cocktail != null) {
                CocktailRecipe recipe = get(pLevel, cocktail);
                if (recipe != null) {
                    player.getFoodData().eat(recipe.content.hunger, recipe.content.saturation);
                    if (!player.getAbilities().instabuild) {
                        stack.shrink(1);
                    }
                    for (EffectStack effectStack : recipe.content.getEffect()) {
                        player.addEffect(effectStack.get());
                    }
                } else if (cocktail.equals(UNKNOWN_COCKTAIL)) {
                    player.addEffect(new MobEffectInstance(getUnknownCocktailEffect(pLevel), 100, 0));
                    if (!player.getAbilities().instabuild) {
                        stack.shrink(1);
                    }
                }
            }
        }
        return stack;
    }
    private MobEffect getUnknownCocktailEffect(Level level) {
        List<String> list = ModCommonConfigs.UNKNOWN_COCKTAIL_EFFECTS_BLACKLIST.get();
        List<MobEffect> effects = new ArrayList<>();
        for (String s : list) try {
            effects.add(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(s)));
        } catch (Exception ignored) {}
        if (!ModCommonConfigs.WHITELIST_MODE.get()) {
            List<MobEffect> values = new ArrayList<>(ForgeRegistries.MOB_EFFECTS.getValues().stream().toList());
            values.removeAll(effects);
            return values.get(level.getRandom().nextInt(values.size()));
        }
        return effects.get(level.getRandom().nextInt(effects.size()));
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.DRINK;
    }

    @Override
    public String getDescriptionId(ItemStack stack) {
        ResourceLocation name = getCocktail(stack);

        if (name != null) {
            return name.toString().replace(":", ".");
        }

        return super.getDescriptionId(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        ResourceLocation name = getCocktail(stack);
        if (name != null && level != null) {
            tooltip.add(Component.translatable(name.toString().replace(":", ".") + ".tooltip"));
            CocktailRecipe recipe = get(level, Objects.requireNonNull(getCocktail(stack)));
            if (recipe != null) {
                tooltip.add(Component.translatable("item.cocktail.author", recipe.author));
                CocktailRecipe cocktailRecipe = get(level, name);
                if (cocktailRecipe != null) {
                    List<MobEffectInstance> list = cocktailRecipe.content.getEffect().stream().map(EffectStack::get).toList();
                    if (list.isEmpty()) {
                        tooltip.add(Component.translatable("effect.none").withStyle(ChatFormatting.GRAY));
                    }
                    else {
                        List<Pair<Attribute, AttributeModifier>> list1 = Lists.newArrayList();
                        for (MobEffectInstance mobeffectinstance : list) {
                            MutableComponent mutablecomponent = Component.translatable(mobeffectinstance.getDescriptionId());
                            MobEffect mobeffect = mobeffectinstance.getEffect();
                            Map<Attribute, AttributeModifier> map = mobeffect.getAttributeModifiers();
                            if (!map.isEmpty()) {
                                for (Map.Entry<Attribute, AttributeModifier> entry : map.entrySet()) {
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

                            for (Pair<Attribute, AttributeModifier> pair : list1) {
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
            }
        }

    }

    @Nullable
    public static ResourceLocation getCocktail(ItemStack itemStack) {
        if (itemStack.getTag() != null && itemStack.getTag().contains("cocktail")) {
            return new ResourceLocation(itemStack.getTag().getString("cocktail"));
        }

        return null;
    }

    public static void setCocktail(ItemStack itemStack, ResourceLocation name) {
        itemStack.getOrCreateTag().putString("cocktail", name.toString());
    }

    @Nullable
    public static CocktailRecipe get(Level level, ResourceLocation resourceLocation) {
        Optional<? extends Recipe<?>> result = level.getRecipeManager().byKey(resourceLocation);

        if (result.isPresent() && result.get().getType() == RecipeTypes.COCKTAIL.get()) {
            return (CocktailRecipe) result.get();
        }

        return null;
    }



}