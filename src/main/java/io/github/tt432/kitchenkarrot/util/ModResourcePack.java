package io.github.tt432.kitchenkarrot.util;

import io.github.tt432.kitchenkarrot.Kitchenkarrot;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.resource.PathResourcePack;

import java.nio.file.Path;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModResourcePack {
    @SubscribeEvent
    public static void registerResourcePack(AddPackFindersEvent event) {
        IModFile file = ModList.get().getModFileById(Kitchenkarrot.MOD_ID).getFile();
        Path path = file.findResource("resourcepack/legacy_art");
        event.addRepositorySource((consumer, packConstructor) -> {
                Pack pack = Pack.create(
                        new ResourceLocation(Kitchenkarrot.MOD_ID, "legacy_art").toString(),
                        false,
                        () -> new PathResourcePack(new TranslatableComponent("resource.kitchenkarrot.legacy_art").getString(), path),
                        packConstructor,
                        Pack.Position.TOP,
                        PackSource.BUILT_IN
                );
                if (pack != null) consumer.accept(pack);
            }
        );
    }
}
