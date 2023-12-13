package io.github.tt432.kitchenkarrot.block;

import io.github.tt432.kitchenkarrot.blockentity.PlateBlockEntity;
import io.github.tt432.kitchenkarrot.recipes.recipe.PlateRecipe;
import io.github.tt432.kitchenkarrot.registries.ModBlockEntities;
import io.github.tt432.kitchenkarrot.registries.ModItems;
import io.github.tt432.kitchenkarrot.registries.ModSoundEvents;
import io.github.tt432.kitchenkarrot.registries.RecipeTypes;
import io.github.tt432.kitchenkarrot.tag.ModItemTags;
import io.github.tt432.kitchenkarrot.util.ItemHandlerUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootContext;
// import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import static io.github.tt432.kitchenkarrot.block.PlateHolderMap.plateHolder;

/**
 * @author DustW
 **/

@SuppressWarnings("deprecation")
@ParametersAreNonnullByDefault
public class PlateBlock extends ModBaseEntityBlock<PlateBlockEntity> {

    public static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 3, 14);
    public static final BooleanProperty CREATIVE = BooleanProperty.create("creative");
    public static final IntegerProperty DEGREE = IntegerProperty.create("degree", 0, 360);

    public PlateBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(defaultBlockState().setValue(CREATIVE, false));
        this.registerDefaultState(defaultBlockState().setValue(DEGREE, 0));
    }

    @Override
    public BlockEntityType<PlateBlockEntity> getBlockEntity() {
        return ModBlockEntities.PLATE.get();
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos,
            CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @NotNull
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand,
            BlockHitResult hit) {

        AtomicBoolean success = new AtomicBoolean(false);
        BlockEntity blockEntity = level.getBlockEntity(pos);

        if (!state.getValue(CREATIVE) || player.isCreative()) {
            if (blockEntity != null) {
                blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
                    ItemStack heldItem = player.getItemInHand(hand);
                    ItemStack dishItem = handler.getStackInSlot(0);
                    if (player.isShiftKeyDown()) {
                        if (heldItem.isEmpty()) {
                            ItemStack stack = new ItemStack(this);
                            blockEntity.saveToItem(stack);
                            setPlate(stack, dishItem);
                            if (stack.getOrCreateTag().contains("plate_type") && !dishItem.is(Items.AIR)) {
                                String inputName = dishItem.getDisplayName().getString().replace("[", "").replace("]",
                                        "");
                                stack.setHoverName((Component.translatable("info.kitchenkarrot.dished", inputName))
                                        .setStyle(Style.EMPTY.withItalic(false)));
                            }
                            player.setItemInHand(hand, stack);
                            level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
                        }
                    } else {
                        if (hand == InteractionHand.MAIN_HAND) {
                            success.set(interactWithDish(dishItem, heldItem, level, player, handler, pos));
                        } else if (!heldItem.isEmpty() && !success.get()) {
                            success.set(interactWithDish(dishItem, heldItem, level, player, handler, pos));
                        }
                    }
                });
            }
        }

        if (success.get()) {
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    private boolean canHoldItem(IItemHandler handler, ItemStack heldItem) {
        ItemStack dishItem = handler.getStackInSlot(0);
        return plateHolder.containsKey(heldItem.getItem()) &&
                (dishItem.isEmpty() || (dishItem.is(heldItem.getItem())
                        && dishItem.getCount() < plateHolder.get(dishItem.getItem())));
    }

    private boolean interactWithDish(ItemStack dishItem, ItemStack heldItem, Level level, Player player,
            IItemHandler handler, BlockPos pos) {
        AtomicBoolean result = new AtomicBoolean(false);
        if (canHoldItem(handler, heldItem)) {
            result.set(addToPlate(handler, heldItem, player));
            level.playSound(player, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS);
        } else if (!dishItem.isEmpty() && heldItem.isEmpty() || heldItem.is(ModItemTags.INTERACT_WITH_PLATE)) {
            result.set(removeFromPlate(level, player, handler, dishItem, heldItem));
            level.playSound(player, pos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS);
        }
        return result.get();
    }

    private boolean removeFromPlate(Level level, Player player, IItemHandler handler, ItemStack input,
            ItemStack heldItem) {
        Optional<PlateRecipe> recipe = level.getRecipeManager()
                .getAllRecipesFor(RecipeTypes.PLATE.get())
                .stream()
                .filter(r -> r.matches(Collections.singletonList(input)) &&
                        r.canCut(heldItem, input))
                .findFirst();

        AtomicBoolean result = new AtomicBoolean(false);

        recipe.ifPresent(r -> {
            if (giveRecipeResult(r, handler)) {
                level.playSound(player, player.getOnPos(), ModSoundEvents.CHOP.get(), player.getSoundSource(), 0.5F,
                        level.random.nextFloat() * 0.4F + 0.8F);
                result.set(true);
            }
        });

        if (recipe.isEmpty()) {
            ItemHandlerUtils.extractSingle(handler, 0, player);
            result.set(true);
        }

        return result.get();
    }

    private boolean addToPlate(IItemHandler handler, ItemStack heldItem, Player player) {
        AtomicBoolean result = new AtomicBoolean(false);
        ItemHandlerUtils.insertSingle(handler, 0, player, heldItem);
        result.set(true);
        return result.get();
    }

    private boolean giveRecipeResult(PlateRecipe recipe, IItemHandler handler) {
        AtomicBoolean result = new AtomicBoolean(false);
        handler.extractItem(0, 64, false);
        handler.insertItem(0, recipe.getResultStack(), false);
        result.set(true);

        return result.get();
    }

    public static void setPlate(ItemStack self, ItemStack content) {
        self.getOrCreateTag().putInt("plate_amount", content.getCount());
        if (ForgeRegistries.ITEMS.getKey(content.getItem()) != null) {
            self.getOrCreateTag().putString("plate_type", ForgeRegistries.ITEMS.getKey(content.getItem()).toString());
        }
    }

    @Override
    public List<ItemStack> getDrops(BlockState p_287732_, LootContext.Builder p_287596_) {
        return List.of(new ItemStack(ModItems.PLATE_PIECES.get()));
    }

    @Override
    protected void spawnDestroyParticles(Level pLevel, Player pPlayer, BlockPos pPos, BlockState pState) {
    }

    @Override
    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        pLevel.playSound(null, pPos, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 1, 1);
        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(CREATIVE);
        builder.add(DEGREE);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        int rotation = (int) context.getRotation();
        if (context.getLevel().isClientSide) {
            while (rotation >= 180 || rotation < -180) {
                if (rotation >= 180)
                    rotation -= 360;
                if (rotation < -180)
                    rotation += 360;
            }
        }
        return this.defaultBlockState().setValue(DEGREE, (rotation) + 180);
    }
}
