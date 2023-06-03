package io.github.tt432.kitchenkarrot.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.tt432.kitchenkarrot.Kitchenkarrot;
import io.github.tt432.kitchenkarrot.blockentity.BrewingBarrelBlockEntity;
import io.github.tt432.kitchenkarrot.gui.base.KKGui;
import io.github.tt432.kitchenkarrot.gui.widget.ImageButtonWidget;
import io.github.tt432.kitchenkarrot.gui.widget.ProgressWidget;
import io.github.tt432.kitchenkarrot.menu.BrewingBarrelMenu;
import io.github.tt432.kitchenkarrot.net.ModNetManager;
import io.github.tt432.kitchenkarrot.net.client.BrewingBarrelStartC2S;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import java.util.function.Supplier;

/**
 * @author DustW
 **/
public class BrewingBarrelGui extends KKGui<BrewingBarrelMenu> {

    public static final ResourceLocation TEXTURE =
            new ResourceLocation(Kitchenkarrot.MOD_ID, "textures/gui/brewing_barrel.png");

    public BrewingBarrelGui(BrewingBarrelMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, TEXTURE);
    }

    @Override
    protected void init() {
        super.init();
        var be = this.menu.blockEntity;
        be.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).ifPresent(handler -> {
            if (handler instanceof IFluidTank tank) {
                Supplier<Integer> progress = () -> be.getMaxProgress() - be.getProgress();

                addRenderableWidget(new ProgressWidget(this, TEXTURE, leftPos + 21, topPos + 23,
                        182, 0, 9, 42, true,
                        () -> new TextComponent(tank.getFluidAmount() + "mB / " + tank.getCapacity() + "mB"),
                        true, tank::getCapacity, tank::getFluidAmount));

                addRenderableWidget(new ProgressWidget(this, TEXTURE, leftPos + 152, topPos + 23,
                        178, 0, 4, 42, true,
                        () -> {
                            if (be.isStarted()) {
                                return new TextComponent(be.getProgress() * 100 / be.getMaxProgress() + "%");
                            } else {
                                if (tank.getFluidAmount() < be.FLUID_CONSUMPTION) {
                                    return new TranslatableComponent("brewing_barrel.error.not_enough_liquid");
                                }
                                else if (!be.isRecipeSame()) {
                                    return new TranslatableComponent("brewing_barrel.error.error_recipe");
                                }
                                else if (!be.resultEmpty()) {
                                    return new TranslatableComponent("brewing_barrel.error.result_slot_not_empty");
                                }
                                else {
                                    return new TranslatableComponent("brewing_barrel.ok");
                                }
                            }
                        },
                        true, be::getMaxProgress, be::getProgress));
            }
        });

        addRenderableWidget(close(button = new ImageButtonWidget(this, leftPos + 155, topPos + 50,
                12, 12, (b) -> ModNetManager.sendToServer(new BrewingBarrelStartC2S(be.getBlockPos())),
                TEXTURE, 184, 0)));}

    @Override
    public void onClose() {
        super.onClose();
        BrewingBarrelBlockEntity blockEntity = this.getMenu().blockEntity;
        blockEntity.getLevel().setBlock(blockEntity.getBlockPos(), blockEntity.getBlockState().setValue(BarrelBlock.OPEN, Boolean.FALSE), 3);
    }

    ImageButtonWidget button;

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        var be = menu.blockEntity;

        if (be.resultEmpty() && be.hasEnoughWater() && be.isRecipeSame() && !be.isStarted()) {
            open(button);
        }
        else {
            close(button);
        }

        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }
}
