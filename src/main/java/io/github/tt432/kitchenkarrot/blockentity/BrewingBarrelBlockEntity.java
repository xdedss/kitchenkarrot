package io.github.tt432.kitchenkarrot.blockentity;

import io.github.tt432.kitchenkarrot.blockentity.sync.*;
import io.github.tt432.kitchenkarrot.capability.KKItemStackHandler;
import io.github.tt432.kitchenkarrot.menu.BrewingBarrelMenu;
import io.github.tt432.kitchenkarrot.recipes.recipe.BrewingBarrelRecipe;
import io.github.tt432.kitchenkarrot.recipes.register.RecipeTypes;
import io.github.tt432.kitchenkarrot.util.ItemHandlerUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author DustW
 **/
public class BrewingBarrelBlockEntity extends MenuBlockEntity {
    public static final int FLUID_CONSUMPTION = 500;
    public static final int FLUID_CAPACITY = 4000;
    FluidTankSyncData tank;
    public KKItemStackHandler input = new KKItemStackHandler(this, 6);
    private KKItemStackHandler result = new KKItemStackHandler(this, 1);
    private IntSyncData progress;
    private IntSyncData maxProgress;
    private StringSyncData recipe;
    private BrewingBarrelRecipe currentRecipe;
    private BoolSyncData started;

    public BrewingBarrelBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.BREWING_BARREL.get(), pWorldPosition, pBlockState);
    }

    @Override
    protected void syncDataInit(SyncDataManager manager) {
        manager.add(tank = new FluidTankSyncData("fluid",FLUID_CAPACITY, (f) -> f.getFluid() == Fluids.WATER, true));
        manager.add(progress = new IntSyncData("progress", 0, true));
        manager.add(maxProgress = new IntSyncData("maxProgress", 0, true));
        manager.add(recipe = new StringSyncData("recipe", "", true));
        manager.add(started = new BoolSyncData("started", false, true));
    }

    public BrewingBarrelRecipe getRecipe() {
        return currentRecipe == null && !this.recipe.isEmpty() ?
                currentRecipe = (BrewingBarrelRecipe) level.getRecipeManager()
                        .byKey(new ResourceLocation(recipe.get())).get() : currentRecipe;
    }

    @Override
    public void tick() {
        super.tick();

        if (!level.isClientSide) {
            if (hasRecipe() && hasEnoughWater()){
                if (isStarted()) {
                    if (isRecipeSame() && progress.plus(1,getMaxProgress()) == getMaxProgress()){
                        finishBrewing();
                    }
                } else {start();}
            }else if(isStarted()) {
                endProgress();
            }
        }
    }

    private boolean hasRecipe(){
        var inputList = ItemHandlerUtils.toList(input);
        return level.getRecipeManager().getAllRecipesFor(RecipeTypes.BREWING_BARREL.get())
                .stream().anyMatch(r -> r.matches(inputList));
    }

    private void finishBrewing(){
        ItemStack resultStack = result.getStackInSlot(0);
        if (resultStack.isEmpty() ||
                (resultStack.sameItem(getRecipe().getResultItem()) && resultStack.getCount() < resultStack.getMaxStackSize())) {
            result.insertItem(0, getRecipe().getResultItem(), false);
            for (int i = 0; i < input.getSlots(); i++) {
                input.extractItem(i, 1, false);
            }
            tank.get().drain(500, IFluidHandler.FluidAction.EXECUTE);
            endProgress();
        }
    }

    public boolean resultEmpty() {
        return result.getStackInSlot(0).isEmpty();
    }

    public boolean hasEnoughWater() {
        return tank.get().getFluidAmount() >= FLUID_CONSUMPTION;
    }

    public boolean isRecipeSame() {
        return this.getRecipe() != null && this.getRecipe().matches(ItemHandlerUtils.toList(input));
    }

    public boolean isStarted() {
        return started.get();
    }

    void endProgress() {
        started.set(false);
        setRecipe(null);
        resetProgress();
    }

    void setRecipe(BrewingBarrelRecipe recipe) {
        this.currentRecipe = recipe;

        if (recipe != null) {
            this.recipe.set(recipe.getId().toString());
        }
        else {
            this.recipe.set("");
        }
    }

    void resetProgress() {
        if (isStarted()) {
            this.progress.set(0);
            this.maxProgress.set(currentRecipe.getCraftingTime());
        }
        else {
            this.progress.set(0);
            this.maxProgress.set(0);
        }
    }

    public void start() {
        var inputList = ItemHandlerUtils.toList(input);
        level.getRecipeManager().getAllRecipesFor(RecipeTypes.BREWING_BARREL.get())
                .stream().filter(r -> r.matches(inputList)).forEach(r -> {setRecipe(r);});
        started.set(true);
        resetProgress();
    }

    @Override
    public List<ItemStack> drops() {
        return ItemHandlerUtils.toList(input, result);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new BrewingBarrelMenu(pContainerId, pInventory, this);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.orEmpty(cap, LazyOptional.of(() -> tank.get()));
    }

    public Integer getMaxProgress() {
        return maxProgress.get();
    }

    public Integer getProgress() {
        return progress.get();
    }

    public IItemHandler result() {
        return result;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("input", input.serializeNBT());
        pTag.put("result", result.serializeNBT());
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        if (!isSyncTag(pTag)) {
            input.deserializeNBT(pTag.getCompound("input"));
            result.deserializeNBT(pTag.getCompound("result"));
        }
    }

}
