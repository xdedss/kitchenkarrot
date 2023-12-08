package io.github.tt432.kitchenkarrot.recipes.base;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import io.github.tt432.kitchenkarrot.recipes.recipe.AirCompressorRecipe;
import io.github.tt432.kitchenkarrot.util.json.JsonUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.crafting.CraftingRecipeCodecs;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;

/**
 * @author DustW
 **/
public class BaseSerializer<RECIPE extends BaseRecipe<RECIPE>>
        //TODO super class deleted, possibly cause unknown issue.
//        extends ForgeRegistryEntry<RecipeSerializer<?>>
        implements RecipeSerializer<RECIPE> {

    Class<RECIPE> recipeClass;
    private static final CraftingRecipeCodecs.

    public BaseSerializer(Class<RECIPE> recipeClass) {
        this.recipeClass = recipeClass;
    }

    @Override
    public Codec<RECIPE> codec() {
        return Codec.of();
    }

    @Override
    public @Nullable RECIPE fromNetwork(FriendlyByteBuf buf) {
        return JsonUtils.INSTANCE.normal.fromJson(buf.readUtf(), recipeClass);
//        return JsonUtils.INSTANCE.normal.fromJson(buf.readUtf(), recipeClass).setID(id);
    }

    @Override
    public void toNetwork(FriendlyByteBuf pBuffer, RECIPE pRecipe) {
        pBuffer.writeUtf(JsonUtils.INSTANCE.normal.toJson(pRecipe));
    }

    @Override
    public RECIPE fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
        return JsonUtils.INSTANCE.normal.fromJson(pSerializedRecipe, recipeClass).setID(pRecipeId);
    }

    public JsonObject toJson(RECIPE pRecipe) {
        return JsonUtils.INSTANCE.normal.toJsonTree(pRecipe).getAsJsonObject();
    }
}
