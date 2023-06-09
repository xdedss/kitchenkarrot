package io.github.tt432.kitchenkarrot.glm;

import io.github.tt432.kitchenkarrot.Kitchenkarrot;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModGlobalLootModifiers {
    public static final DeferredRegister<GlobalLootModifierSerializer<?>> GLM = DeferredRegister.create(ForgeRegistries.Keys.LOOT_MODIFIER_SERIALIZERS, Kitchenkarrot.MOD_ID);

    public static final RegistryObject<GlobalLootModifierSerializer<?>> REPLACE_LOOT_MODIFIER = GLM.register("replace_loot_modifier",ReplaceLootModifier.Serializer::new);
    public static final RegistryObject<GlobalLootModifierSerializer<?>> ADD_ITEM_MODIFIER = GLM.register("add_item_modifier", AddItemModifier.Serializer::new);
}
