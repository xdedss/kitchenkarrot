package io.github.tt432.kitchenkarrot.recipes.recipe;

import com.google.gson.annotations.Expose;
import io.github.tt432.kitchenkarrot.block.PlateHolderMap;
import io.github.tt432.kitchenkarrot.recipes.base.BaseRecipe;
import io.github.tt432.kitchenkarrot.registries.RecipeSerializers;
import io.github.tt432.kitchenkarrot.registries.RecipeTypes;
import io.github.tt432.kitchenkarrot.tag.ModItemTags;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author DustW
 **/
public class PlateRecipe extends BaseRecipe<PlateRecipe> {

    @Expose Input input;
    @Expose @NotNull Output result = new Output();
    @Expose @Nullable Ingredient tool;

    static class Input {
        @Expose String item;

        Item cache;

        Item getItem() {
            return cache == null ? cache = ForgeRegistries.ITEMS.getValue(new ResourceLocation(item)) : cache;
        }
    }

    static class Output {
        @Expose String item;

        Ingredient defaultTool = Ingredient.of(ModItemTags.KNIFE_ITEM);

        Item cache;

        Item getItem() {
            return cache == null ? cache = ForgeRegistries.ITEMS.getValue(new ResourceLocation(item)) : cache;
        }

    }
    public Item getInput() {
        return input.getItem();
    }

    public int getResultCount() {
        return  PlateHolderMap.plateHolder.get(result.getItem());
    }

    public ItemStack getResultStack() {
        return new ItemStack(result.getItem(), getResultCount());
    }
    public Ingredient getTool() {
        return tool == null || tool.isEmpty() ? result.defaultTool : tool;
    }
    public boolean canCut(ItemStack tool, ItemStack input) {
        return getTool().test(tool) && this.getInput() == input.getItem();
    }
    @Override
    public boolean matches(List<ItemStack> inputs) {
        return inputs.get(0).is(getInput());
    }

    @Override
    @NotNull
    public ItemStack getResultItem() {
        return result.item != null ? new ItemStack(result.getItem()) : ItemStack.EMPTY;
    }

    @Override
    @NotNull
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializers.PLATE.get();
    }

    @Override
    @NotNull
    public RecipeType<?> getType() {
        return RecipeTypes.PLATE.get();
    }
}
