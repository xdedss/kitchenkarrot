package io.github.tt432.kitchenkarrot.glm;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ReplaceLootModifier extends LootModifier {
    public static final Supplier<Codec<ReplaceLootModifier>> CODEC = Suppliers.memoize(()
            -> RecordCodecBuilder.create(inst -> codecStart(inst)
            .and(inst.group(
                    ForgeRegistries.ITEMS.getCodec().fieldOf("item").forGetter(m -> m.item),
                    Codec.INT.optionalFieldOf("weight", 1).forGetter(m -> m.weight),
                    Codec.INT.optionalFieldOf("count", 1).forGetter(m -> m.count)
                )
            )
            .apply(inst, ReplaceLootModifier::new)));
    private final Item item;
    private final int weight;
    private final int count;

    protected ReplaceLootModifier(LootItemCondition[] conditionsIn, Item item, int weight, int count) {
        super(conditionsIn);
        this.item = item;
        this.weight = weight;
        this.count = count;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (context.getRandom().nextInt(900) < weight) {
            generatedLoot.clear();
            generatedLoot.add(new ItemStack(item, context.getRandom().nextInt(count) + 1));
        }
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
