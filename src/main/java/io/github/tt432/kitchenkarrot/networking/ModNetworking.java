package io.github.tt432.kitchenkarrot.networking;

import io.github.tt432.kitchenkarrot.Kitchenkarrot;
import io.github.tt432.kitchenkarrot.networking.packet.C2SUpdateBarrelPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.*;

import java.util.function.BiConsumer;

public class ModNetworking {
    private static int ID = 0;
    private static int nextId() {
        return ID++;
    }

    private final SimpleChannel channel;

    public ModNetworking() {
//        channel = NetworkRegistry.newSimpleChannel(
//                new ResourceLocation(Kitchenkarrot.MOD_ID, "channel"),
//                () -> Kitchenkarrot.VERSION,
//                Kitchenkarrot.VERSION::equals,
//                Kitchenkarrot.VERSION::equals
//        );
        ChannelBuilder channelBuilder = ChannelBuilder.named(new ResourceLocation(Kitchenkarrot.MOD_ID, "channel"))
                .networkProtocolVersion(Kitchenkarrot.VERSION)
                .clientAcceptedVersions(Channel.VersionTest.exact(Kitchenkarrot.VERSION))
                .serverAcceptedVersions(Channel.VersionTest.exact(Kitchenkarrot.VERSION));
        channel = channelBuilder.simpleChannel();
        channel.messageBuilder(C2SUpdateBarrelPacket.class, nextId(), NetworkDirection.PLAY_TO_SERVER)
                .encoder(C2SUpdateBarrelPacket::write)
                .decoder(C2SUpdateBarrelPacket::new)
                .consumerMainThread(C2SUpdateBarrelPacket::handle);
    }

    public void sendUpdateBarrel(BlockPos pos, boolean value) {
//        channel.sendToServer(new C2SUpdateBarrelPacket(pos, value));
        channel.send(new C2SUpdateBarrelPacket(pos, value), PacketDistributor.SERVER.noArg());
    }
}
