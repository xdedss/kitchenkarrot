package io.github.tt432.kitchenkarrot.networking;

import io.github.tt432.kitchenkarrot.Kitchenkarrot;
import io.github.tt432.kitchenkarrot.networking.packet.C2SUpdateBarrelPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModNetworking {
    private static int ID = 0;
    private static int nextId() {
        return ID++;
    }

    private final SimpleChannel channel;

    public ModNetworking() {
        channel = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(Kitchenkarrot.MOD_ID, "channel"),
                () -> Kitchenkarrot.VERSION,
                Kitchenkarrot.VERSION::equals,
                Kitchenkarrot.VERSION::equals
        );

        channel.registerMessage(nextId(), C2SUpdateBarrelPacket.class, C2SUpdateBarrelPacket::write, C2SUpdateBarrelPacket::new, C2SUpdateBarrelPacket::handle);
    }

    public void sendUpdateBarrel(BlockPos pos, boolean value) {
        channel.sendToServer(new C2SUpdateBarrelPacket(pos, value));
    }
}
