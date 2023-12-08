package io.github.tt432.kitchenkarrot.recipes.recipe;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.tt432.kitchenkarrot.recipes.base.BaseRecipe;
import io.github.tt432.kitchenkarrot.registries.RecipeSerializers;
import io.github.tt432.kitchenkarrot.registries.RecipeTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraftforge.common.util.RecipeMatcher;
import org.checkerframework.checker.units.qual.A;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author DustW
 **/
public class AirCompressorRecipe extends BaseRecipe<AirCompressorRecipe> {
    public AirCompressorRecipe(NonNullList<Ingredient> ingredient, int craftingTime, Ingredient container, ItemStack result) {
        this.ingredient = ingredient;
        this.craftingTime = craftingTime;
        this.container = container;
        this.result = result;
    }
    @Expose
    NonNullList<Ingredient> ingredient = NonNullList.withSize(4, Ingredient.EMPTY);
    @Expose
    @SerializedName("craftingtime")
    int craftingTime;
    @Expose
    Ingredient container;
    @Expose
    ItemStack result;

    @Override
    public boolean matches(List<ItemStack> inputs) {
        return RecipeMatcher.findMatches(inputs, ingredient) != null;
    }

    public NonNullList<Ingredient> getIngredient() {
        return ingredient;
    }

    public boolean testContainer(ItemStack stack) {
        return container == null || container.test(stack);
    }

    public Ingredient getContainer() {
        return container;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess p_267052_) {
        return result.copy();
    }

//    @Override
//    public ItemStack getResultItem() {
//        return result.copy();
//    }

    public int getCraftingTime() {
        return craftingTime;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializers.AIR_COMPRESSOR.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeTypes.AIR_COMPRESSOR.get();
    }

    public static class Serializer implements RecipeSerializer<AirCompressorRecipe> {
        private static final Codec<AirCompressorRecipe> CODEC = RecordCodecBuilder.create((builder) -> {
            return builder.group(Ingredient.CODEC.fieldOf("container").forGetter((recipe) -> {
                return recipe.container;
            }), Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredient").flatXmap((ingredientList) -> {
                Ingredient[] aingredient = ingredientList.stream().filter((i) -> !i.isEmpty()).toArray(Ingredient[]::new);
                return DataResult.success(NonNullList.of(Ingredient.EMPTY, aingredient));
            }, DataResult::success),
                //TODO 为什么这地方会报错？？
                Codec.INT.fieldOf("craftingTime").forGetter((recipe) -> {
                return recipe.craftingTime;
            }), CraftingRecipeCodecs.ITEMSTACK_OBJECT_CODEC.fieldOf("result").forGetter((recipe) -> {
                return recipe.result;
            })).apply(builder, SmithingTransformRecipe::new);
        });

        public Codec<AirCompressorRecipe> codec() {
            return CODEC;
        }

        public AirCompressorRecipe fromNetwork(FriendlyByteBuf buf) {
            Ingredient container = Ingredient.fromNetwork(buf);
            NonNullList<Ingredient> ingredients = NonNullList.withSize(4, Ingredient.EMPTY);
            for(int i = 0; i < 4; ++i) {
                ingredients.set(i, Ingredient.fromNetwork(buf));
            }
            int time = buf.readInt();
            ItemStack itemstack = buf.readItem();
            return new AirCompressorRecipe(ingredients, time, container, itemstack);
        }

        public void toNetwork(FriendlyByteBuf buf, AirCompressorRecipe recipe) {
            recipe.container.toNetwork(buf);
            for (Ingredient i : recipe.ingredient) {
                i.toNetwork(buf);
            }
            buf.writeInt(recipe.craftingTime);
            buf.writeItem(recipe.result);
        }
    }
}
