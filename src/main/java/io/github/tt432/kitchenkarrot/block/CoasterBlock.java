package io.github.tt432.kitchenkarrot.block;

import io.github.tt432.kitchenkarrot.blockentity.CoasterBlockEntity;
import io.github.tt432.kitchenkarrot.registries.ModBlockEntities;
import io.github.tt432.kitchenkarrot.util.ItemHandlerUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.CapabilityItemHandler;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author DustW
 **/
public class CoasterBlock extends FacingEntityBlock<CoasterBlockEntity> {
    static {
        var part1 = Block.box(4, 0, 3, 4 + 2, 1, 16 - 3);
        var part2 = Block.box(3, 0, 4, 16 - 3, 1, 4 + 2);
        var part3 = Block.box(16 - 4 - 2, 0, 3, 16 - 4, 1, 16 - 3);
        var part4 = Block.box(3, 0, 16 - 4 - 2, 16 - 3, 1, 16 - 4);

        SHAPE = Shapes.or(part1, part2, part3, part4);
    }

    public static final VoxelShape SHAPE;

    public static final BooleanProperty INVISIBLE = BooleanProperty.create("invisible");
    public CoasterBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(defaultBlockState().setValue(INVISIBLE, false));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public BlockEntityType<CoasterBlockEntity> getBlockEntity() {
        return ModBlockEntities.COASTER.get();
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide) {
            // The logic will be passing every single interaction, so sound always plays.
            // Well, it's still weird when you keep right-clicking with empty hand.
            pLevel.playSound(pPlayer, pPos, SoundEvents.WOOD_HIT, SoundSource.BLOCKS, 1, 1);
            return InteractionResult.SUCCESS;
        }

        AtomicBoolean success = new AtomicBoolean(false);

        pLevel.getBlockEntity(pPos).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
            if (handler.getStackInSlot(0).isEmpty() && !pPlayer.getItemInHand(pHand).isEmpty()) {
                ItemHandlerUtils.insertSingle(handler, 0, pPlayer, pHand);
                success.set(true);
            }
            else if (!handler.getStackInSlot(0).isEmpty()) {
                ItemHandlerUtils.extractSingle(handler, 0, pPlayer);
                success.set(true);
            }
        });

        if (success.get()) {
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(INVISIBLE);
    }

}
