package io.github.tt432.kitchenkarrot.client.renderer.be;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import io.github.tt432.kitchenkarrot.block.CoasterBlock;
import io.github.tt432.kitchenkarrot.blockentity.PlateBlockEntity;
import io.github.tt432.kitchenkarrot.client.ModModelRegistry;
import io.github.tt432.kitchenkarrot.client.plate.PlateModelRegistry;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.ParametersAreNonnullByDefault;

import static io.github.tt432.kitchenkarrot.client.plate.PlateModelRegistry.to;

/**
 * @author DustW
 **/
public class PlateBlockEntityRenderer implements BlockEntityRenderer<PlateBlockEntity> {

    private final ModelManager modelManager;
    private final ModelBlockRenderer modelRenderer;

    public PlateBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.modelManager = context.getBlockRenderDispatcher().getBlockModelShaper().getModelManager();
        this.modelRenderer = context.getBlockRenderDispatcher().getModelRenderer();
    }


    //TODO 重写渲染系统
    @Override
    @ParametersAreNonnullByDefault
    public void render(PlateBlockEntity pBlockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        pBlockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
            ItemStack stack = handler.getStackInSlot(0);
            //BakedModel model = PlateModelRegistry.get(new ResourceLocation(stack.getItem().getRegistryName() + "_" + stack.getCount()));
            //ResourceLocation modelLocation = PlateModelRegistry.MODEL_MAP.get(new ResourceLocation(stack.getItem().getRegistryName() + "_" + stack.getCount()));
            BakedModel model = this.modelManager.getModel(to(stack.isEmpty() ? PlateModelRegistry.DEFAULT_NAME : new ResourceLocation(stack.getItem().getRegistryName() + "_" + stack.getCount())));

            poseStack.pushPose();
            BlockState state = pBlockEntity.getBlockState();
            poseStack.translate(.5, .5, .5);
            poseStack.mulPose(Vector3f.YP.rotationDegrees(
                switch (state.getValue(CoasterBlock.FACING)) {
                    case EAST -> 90;
                    case WEST -> -90;
                    case SOUTH -> 180;
                    default -> 0;
                }
            ));

            poseStack.translate(-.5, -.5, -.5);
            //ModModelRegistry.render(model, bufferSource, pBlockEntity, poseStack, packedLight, packedOverlay);
            this.modelRenderer.renderModel(poseStack.last(), bufferSource.getBuffer(RenderType.solid()), pBlockEntity.getBlockState(), model, 1.0F, 1.0F, 1.0F, packedLight, packedOverlay, EmptyModelData.INSTANCE);
            poseStack.popPose();
        });
    }

}