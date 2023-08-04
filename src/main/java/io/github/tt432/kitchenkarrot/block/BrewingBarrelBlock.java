package io.github.tt432.kitchenkarrot.block;

import io.github.tt432.kitchenkarrot.blockentity.BrewingBarrelBlockEntity;
import io.github.tt432.kitchenkarrot.registries.ModBlockEntities;
import io.github.tt432.kitchenkarrot.registries.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;

public class BrewingBarrelBlock extends FacingGuiEntityBlock<BrewingBarrelBlockEntity> {
    public static final BooleanProperty OPEN = BooleanProperty.create("open");

    public static final VoxelShape SHAPE_Z = Block.box(1, 1, 0, 15, 15, 16);
    public static final VoxelShape SHAPE_X = Block.box(0, 1, 1, 16, 15, 15);
    public BrewingBarrelBlock(Properties p_49224_) {
        super(p_49224_);
        this.registerDefaultState(defaultBlockState().setValue(OPEN, false));
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return super.getStateForPlacement(pContext).setValue(OPEN,false);
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(OPEN);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        AtomicBoolean changed = new AtomicBoolean(false);

        pLevel.getBlockEntity(pPos).getCapability(ForgeCapabilities.FLUID_HANDLER).ifPresent(handler -> {
            if (handler instanceof IFluidTank tank) {
                ItemStack item = pPlayer.getItemInHand(pHand);
                ItemStack remain = ItemStack.EMPTY;

                if (item.getItem() == Items.WATER_BUCKET) {
                    FluidStack water = new FluidStack(Fluids.WATER, 1000);

                    if (tank.fill(water, IFluidHandler.FluidAction.SIMULATE) == 1000) {
                        changed.set(true);
                        remain = new ItemStack(Items.BUCKET);

                        if (!pLevel.isClientSide) {
                            tank.fill(water, IFluidHandler.FluidAction.EXECUTE);
                        }
                    }
                    pPlayer.playSound(SoundEvents.BUCKET_EMPTY, 0.5F,
                            pLevel.random.nextFloat() * 0.1F + 0.9F);
                } else if (item.getItem() == Items.POTION && PotionUtils.getAllEffects(item.getTag()).isEmpty()) {
                    FluidStack water = new FluidStack(Fluids.WATER, 250);

                    if (tank.fill(water, IFluidHandler.FluidAction.SIMULATE) == 250) {
                        changed.set(true);
                        remain = new ItemStack(Items.GLASS_BOTTLE);

                        if (!pLevel.isClientSide) {
                            tank.fill(water, IFluidHandler.FluidAction.EXECUTE);
                        }
                    }
                    pPlayer.playSound(SoundEvents.BOTTLE_EMPTY, 0.5F,
                            pLevel.random.nextFloat() * 0.1F + 0.9F);
                } else if (item.getItem() == ModItems.WATER.get()) {
                    FluidStack water = new FluidStack(Fluids.WATER, 125);

                    if (tank.fill(water, IFluidHandler.FluidAction.SIMULATE) == 125) {
                        changed.set(true);

                        if (!pLevel.isClientSide) {
                            tank.fill(water, IFluidHandler.FluidAction.EXECUTE);
                        }
                    }
                    pPlayer.playSound(SoundEvents.BUCKET_EMPTY, 0.5F,
                            pLevel.random.nextFloat() * 0.1F + 0.9F);
                } else {
                    if (!pLevel.isClientSide()) {
                        // Fixme: qyl27: work not correctly when more than one player open it.
                        pLevel.setBlock(pPos, pState.setValue(OPEN, true), Block.UPDATE_ALL);
                        this.getBlockEntity().getBlockEntity(pLevel, pPos).playSound(SoundEvents.BARREL_OPEN);
                    }
                }

                if (changed.get()) {
                    if (!pPlayer.getAbilities().instabuild) {
                        item.shrink(1);
                        pPlayer.getInventory().add(remain);
                    }
                }
            }
        });

        if (changed.get()) {
            return InteractionResult.SUCCESS;
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public BlockEntityType<BrewingBarrelBlockEntity> getBlockEntity() {
        return ModBlockEntities.BREWING_BARREL.get();
    }
    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {

        return switch (pState.getValue(FACING)) {
            case NORTH,SOUTH -> SHAPE_Z;
            case EAST,WEST -> SHAPE_X;
            default -> SHAPE_Z;
        };
    }
}
