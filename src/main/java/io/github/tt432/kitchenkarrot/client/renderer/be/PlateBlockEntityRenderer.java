package io.github.tt432.kitchenkarrot.client.renderer.be;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import io.github.tt432.kitchenkarrot.Kitchenkarrot;
import io.github.tt432.kitchenkarrot.block.CoasterBlock;
import io.github.tt432.kitchenkarrot.block.PlateBlock;
import io.github.tt432.kitchenkarrot.blockentity.PlateBlockEntity;
import io.github.tt432.kitchenkarrot.client.plate.PlateModelRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.RenderTypeHelper;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.registries.ForgeRegistries;

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
        pBlockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            ItemStack stack = handler.getStackInSlot(0);
            BakedModel model = this.modelManager.getModel(to(stack.isEmpty() ? PlateModelRegistry.DEFAULT_NAME : new ResourceLocation(Kitchenkarrot.MOD_ID, stack.getItem() + "_" + stack.getCount())));

            poseStack.pushPose();
            BlockState state = pBlockEntity.getBlockState();
            poseStack.translate(.5, .5, .5);
            poseStack.mulPose(Vector3f.YP.rotationDegrees(- state.getValue(PlateBlock.DEGREE) - 180));

            poseStack.translate(-.5, -.5, -.5);
            model.getRenderTypes(pBlockEntity.getBlockState(), RandomSource.create(), ModelData.EMPTY).forEach(renderType ->
                    this.modelRenderer.renderModel(
                            poseStack.last(),
                            bufferSource.getBuffer(RenderTypeHelper.getEntityRenderType(renderType, false)),
                            pBlockEntity.getBlockState(),
                            model, 1.0F, 1.0F, 1.0F,
                            packedLight, packedOverlay, ModelData.EMPTY, renderType)
            );
            poseStack.popPose();
        });
    }

}