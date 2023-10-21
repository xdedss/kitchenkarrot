package io.github.tt432.kitchenkarrot.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.tt432.kitchenkarrot.Kitchenkarrot;
import io.github.tt432.kitchenkarrot.blockentity.AirCompressorBlockEntity;
import io.github.tt432.kitchenkarrot.gui.base.KKGui;
import io.github.tt432.kitchenkarrot.gui.widget.ProgressWidget;
import io.github.tt432.kitchenkarrot.menu.AirCompressorMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

/**
 * @author DustW
 **/
public class AirCompressorGui extends KKGui<AirCompressorMenu> {

    private static final ResourceLocation GUI =
            new ResourceLocation(Kitchenkarrot.MOD_ID, "textures/gui/air_compressor.png");

    AirCompressorBlockEntity be;

    public AirCompressorGui(AirCompressorMenu container, Inventory inv, Component name) {
        super(container, inv, name, GUI);
        be = container.blockEntity;
    }

    @Override
    protected void init() {
        super.init();

        addRenderableWidget(new ProgressWidget(this, GUI,
                leftPos + 105, topPos + 50, 184, 0, 20, 19, false,
                be::getMaxProgress, be::getProgress));
        addRenderableWidget(new ProgressWidget(this, GUI,
                leftPos + 49, topPos + 8, 176, 0, 8, 60, true,
                () -> 12, be::getAtomicEnergy));
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
