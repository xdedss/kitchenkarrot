package io.github.tt432.kitchenkarrot.registries;

import io.github.tt432.kitchenkarrot.Kitchenkarrot;
import io.github.tt432.kitchenkarrot.entity.CanEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITYS = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Kitchenkarrot.MOD_ID);
//    public static final DeferredRegister<EntityType<?>> ENTITYS = DeferredRegister.create(ForgeRegistries.ENTITIES, Kitchenkarrot.MOD_ID);

    public static final RegistryObject<EntityType<CanEntity>> CAN = ENTITYS.register("can", () -> EntityType.Builder
            .of(CanEntity::new, MobCategory.MISC)
            .sized(0.25f, 0.25f).build("can")
    );
}
