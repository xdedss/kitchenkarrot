package io.github.tt432.kitchenkarrot.datagen.loottable;

import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class ModBlockLootProvider extends BlockLoot {

    private final Set<Block> skipBlocks = new HashSet<>();

    @Override
    protected void add(@NotNull Block pBlock, @NotNull LootTable.Builder pLootTableBuilder) {
        super.add(pBlock, pLootTableBuilder);
        skipBlocks.add(pBlock);
    }

    @Override
    @NotNull
    protected Iterable<Block> getKnownBlocks() {
        return skipBlocks;
    }

}
