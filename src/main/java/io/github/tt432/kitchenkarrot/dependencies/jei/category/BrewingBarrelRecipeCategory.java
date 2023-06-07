package io.github.tt432.kitchenkarrot.dependencies.jei.category;

import io.github.tt432.kitchenkarrot.Kitchenkarrot;
import io.github.tt432.kitchenkarrot.dependencies.jei.JeiPlugin;
import io.github.tt432.kitchenkarrot.item.ModBlockItems;
import io.github.tt432.kitchenkarrot.item.ModItems;
import io.github.tt432.kitchenkarrot.recipes.recipe.BrewingBarrelRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

/**
 * @author DustW
 **/
public class BrewingBarrelRecipeCategory extends BaseRecipeCategory<BrewingBarrelRecipe> {
    public static final ResourceLocation BACKGROUND =
            new ResourceLocation(Kitchenkarrot.MOD_ID, "textures/gui/jei.png");

    public BrewingBarrelRecipeCategory(IGuiHelper helper) {
        super(JeiPlugin.BREWING_BARREL,
                helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlockItems.BREWING_BARREL.get())),
                helper.createDrawable(BACKGROUND, 0, 72, 149, 69));
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BrewingBarrelRecipe recipe, IFocusGroup focuses) {
        var ingredients = recipe.getIngredient();

        builder.addSlot(RecipeIngredientRole.INPUT, 42, 17).addIngredients(ingredients.get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 62, 17).addIngredients(ingredients.get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 82, 17).addIngredients(ingredients.get(2));
        builder.addSlot(RecipeIngredientRole.INPUT, 42, 37).addIngredients(ingredients.get(3));
        builder.addSlot(RecipeIngredientRole.INPUT, 62, 37).addIngredients(ingredients.get(4));
        builder.addSlot(RecipeIngredientRole.INPUT, 82, 37).addIngredients(ingredients.get(5));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 109, 27).addItemStack(recipe.getResultItem());
    }
}
