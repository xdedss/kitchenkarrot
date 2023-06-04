package io.github.tt432.kitchenkarrot.networking.packet;

import io.github.tt432.kitchenkarrot.block.BrewingBarrelBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SUpdateBarrelPacket {

    private BlockPos pos;
    private boolean value;

    public C2SUpdateBarrelPacket(BlockPos pos, boolean value) {
        this.pos = pos;
        this.value = value;
    }

    public C2SUpdateBarrelPacket(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.value = buf.readBoolean();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeBoolean(value);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        var context = supplier.get();
        context.enqueueWork(() -> {
            var player = context.getSender();
            if (player == null) {
                return;
            }

            var level = player.getLevel();

            if (level.isLoaded(pos)) {
                var state = level.getBlockState(pos);
                if (state.getBlock() instanceof BrewingBarrelBlock) {
                    level.setBlock(pos, state.setValue(BrewingBarrelBlock.OPEN, value), Block.UPDATE_ALL);
                }
            }
        });
    }
}
