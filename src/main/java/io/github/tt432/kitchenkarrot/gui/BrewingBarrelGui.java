package io.github.tt432.kitchenkarrot.gui;

import io.github.tt432.kitchenkarrot.Kitchenkarrot;
import io.github.tt432.kitchenkarrot.blockentity.BrewingBarrelBlockEntity;
import io.github.tt432.kitchenkarrot.gui.base.KKGui;
import io.github.tt432.kitchenkarrot.gui.widget.ProgressWidget;
import io.github.tt432.kitchenkarrot.menu.BrewingBarrelMenu;
// import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.IFluidTank;

/**
 * @author DustW
 **/
public class BrewingBarrelGui extends KKGui<BrewingBarrelMenu> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(Kitchenkarrot.MOD_ID,
            "textures/gui/brewing_barrel.png");

    public BrewingBarrelGui(BrewingBarrelMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, TEXTURE);
    }

    @Override
    protected void init() {
        super.init();
        var be = this.menu.blockEntity;
        be.getCapability(ForgeCapabilities.FLUID_HANDLER).ifPresent(handler -> {
            if (handler instanceof IFluidTank tank) {

                addRenderableWidget(new ProgressWidget(this, TEXTURE, leftPos + 21, topPos + 23,
                        182, 0, 9, 42, true,
                        () -> Component.translatable(tank.getFluidAmount() + "mB / " + tank.getCapacity() + "mB"),
                        true, tank::getCapacity, tank::getFluidAmount));

                addRenderableWidget(new ProgressWidget(this, TEXTURE, leftPos + 152, topPos + 23,
                        178, 0, 4, 42, true,
                        () -> {
                            if (be.isStarted()) {
                                return Component.literal(be.getProgress() * 100 / be.getMaxProgress() + "%");
                            } else {
                                if (tank.getFluidAmount() < be.FLUID_CONSUMPTION) {
                                    return Component.translatable("brewing_barrel.error.not_enough_liquid");
                                } else if (!be.isRecipeSame()) {
                                    return Component.translatable("brewing_barrel.error.error_recipe");
                                } else if (!be.resultEmpty()) {
                                    return Component.translatable("brewing_barrel.error.result_slot_not_empty");
                                } else {
                                    return Component.empty();
                                }
                            }
                        },
                        true, be::getMaxProgress, be::getProgress));
            }
        });
    }

    @Override
    public void onClose() {
        super.onClose();
        BrewingBarrelBlockEntity blockEntity = this.getMenu().blockEntity;
        Kitchenkarrot.getInstance().getNetworking().sendUpdateBarrel(blockEntity.getBlockPos(), false);
    }
}
