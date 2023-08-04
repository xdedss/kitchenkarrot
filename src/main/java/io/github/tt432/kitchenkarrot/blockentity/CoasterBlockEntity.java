package io.github.tt432.kitchenkarrot.blockentity;

import io.github.tt432.kitchenkarrot.blockentity.sync.ItemStackHandlerSyncData;
import io.github.tt432.kitchenkarrot.blockentity.sync.SyncDataManager;
import io.github.tt432.kitchenkarrot.registries.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

/**
 * @author DustW
 **/
public class CoasterBlockEntity extends BaseBlockEntity {
    ItemStackHandlerSyncData item;

    public CoasterBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.COASTER.get(), pWorldPosition, pBlockState);
    }

    @Override
    protected void syncDataInit(SyncDataManager manager) {
        item = manager.add(new ItemStackHandlerSyncData(this, "item", 1, true));
    }

    @Override
    public List<ItemStack> drops() {
        return Collections.singletonList(item.get().getStackInSlot(0));
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return ForgeCapabilities.ITEM_HANDLER.orEmpty(cap, LazyOptional.of(item).cast());
    }
}
