package io.github.tt432.kitchenkarrot.datagen.loottable;

import io.github.tt432.kitchenkarrot.glm.ReplaceLootModifier;
import io.github.tt432.kitchenkarrot.glm.ModGlobalLootModifiers;
import io.github.tt432.kitchenkarrot.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifierProvider(DataGenerator gen, String modid) {
        super(gen, modid);
    }

    @Override
    protected void start() {}
}
