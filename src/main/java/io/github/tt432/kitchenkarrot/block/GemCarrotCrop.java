package io.github.tt432.kitchenkarrot.block;

import io.github.tt432.kitchenkarrot.item.ModBlockItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class GemCarrotCrop extends CropBlock {
    private static final int STAGE_PER_AGE = 2;
    private static final int MAX_AGE = 7;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_7;
    public static final IntegerProperty STAGE = IntegerProperty.create("stage", 0, STAGE_PER_AGE);
    @Override
    public int getMaxAge() {return MAX_AGE;}
    public int getStagePerAge() {return STAGE_PER_AGE;}
    public @NotNull IntegerProperty getAgeProperty() {
        return AGE;
    }
    public IntegerProperty getStageProperty() {return STAGE;}
    protected int getStage(BlockState state){return state.getValue(this.getStageProperty());}
    public BlockState getStateForStage(int stage) {
        return this.defaultBlockState().setValue(this.getStageProperty(), stage);
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE,STAGE);
    }

    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D)};
    public GemCarrotCrop(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(this.getStageProperty(), 0));
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
        if (!pLevel.isAreaLoaded(pPos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (pLevel.getRawBrightness(pPos, 0) >= 9) {
            int i = this.getStage(pState);
            if (i < this.getStagePerAge()) {
                float f = getGrowthSpeed(this, pLevel, pPos);

                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(pLevel, pPos, pState, pRandom.nextInt((int)(25.0F / f) + 1) == 0)) {
                    int a = this.getAge(pState);
                    pLevel.setBlock(pPos, this.getStateForStage(i + 1), 2);
                    if (i == this.getStagePerAge()-1){
                        pLevel.setBlock(pPos,this.getStateForAge(a+1),2);
                        pLevel.setBlock(pPos,this.getStateForStage(0),2);
                    }
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
                }
            }
        }
    }

    @Override
    @NotNull
    public VoxelShape getShape(BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return SHAPE_BY_AGE[pState.getValue(this.getAgeProperty())];
    }

    @Override
    @NotNull
    protected ItemLike getBaseSeedId() {
        return ModBlockItems.GEM_CARROT.get();
    }
}
