package io.github.tt432.kitchenkarrot.entity;

import io.github.tt432.kitchenkarrot.registries.ModEntities;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityAttributeEvent {
    @SubscribeEvent
    public static void onEvent(EntityAttributeCreationEvent event) {
        event.put(ModEntities.CAN.get(), CanEntity.createAttributes().build());
    }
}
