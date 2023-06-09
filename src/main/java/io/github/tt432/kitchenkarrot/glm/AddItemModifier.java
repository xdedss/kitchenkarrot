package io.github.tt432.kitchenkarrot.glm;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AddItemModifier extends LootModifier {
    public final LootItemCondition[] conditions;
    private final Item item;

    public Item getItem() {
        return item;
    }

    /**
     * Constructs a LootModifier.
     *
     * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
     */
    public AddItemModifier(LootItemCondition[] conditionsIn, Item item) {
        super(conditionsIn);
        this.conditions = conditionsIn;
        this.item = item;
    }

    @NotNull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        generatedLoot.add(item.getDefaultInstance());
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<AddItemModifier>
    {
        @Override
        public AddItemModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] ailootcondition) {
            Item addedItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation((GsonHelper.getAsString(object, "item"))));
            return new AddItemModifier(ailootcondition, addedItem);
        }
        @Override
        public JsonObject write(AddItemModifier instance) {
            return new JsonObject();
        }
    }

}
