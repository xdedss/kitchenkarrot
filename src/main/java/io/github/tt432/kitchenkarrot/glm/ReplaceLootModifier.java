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
import java.util.Objects;

public class ReplaceLootModifier extends LootModifier {
    public final LootItemCondition[] conditions;
    private final Item item;
    private final int weight;
    private final int count;

    public Item getItem() {
        return item;
    }

    public int getWeight() {
        return weight;
    }

    /**
     * Constructs a LootModifier.
     *
     * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
     */
    public ReplaceLootModifier(LootItemCondition[] conditionsIn, Item item, int weight, int count) {
        super(conditionsIn);
        this.conditions = conditionsIn;
        this.weight = weight;
        this.item = item;
        this.count = count;
    }

    @NotNull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        //TODO 现在是手动模拟一个权重，以后可以考虑换一种实现。使用mixin，或者参考Blueprint库
        if (context.getRandom().nextInt(900) < weight) {
            generatedLoot.clear();
            generatedLoot.add(new ItemStack(item, context.getRandom().nextInt(count) + 1));
        }
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<ReplaceLootModifier> {
        public ReplaceLootModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] conditions) {
            Item addedItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(GsonHelper.getAsString(object, "item")));
            int weight = GsonHelper.getAsInt(object, "weight", 45);
            int count = GsonHelper.getAsInt(object, "max_count", 4);
            return new ReplaceLootModifier(conditions, addedItem, weight, count);
        }

        public JsonObject write(ReplaceLootModifier instance) {
            JsonObject object = this.makeConditions(instance.conditions);
            object.addProperty("item", Objects.requireNonNull(instance.getItem().getRegistryName()).toString());
            object.addProperty("weight",instance.getWeight());
            object.addProperty("max_count",instance.getWeight());
            return object;
        }
    }

}
