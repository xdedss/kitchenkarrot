package io.github.tt432.kitchenkarrot.glm;

import com.mojang.serialization.Codec;
import io.github.tt432.kitchenkarrot.Kitchenkarrot;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModGlobalLootModifiers {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> GLM = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Kitchenkarrot.MOD_ID);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> REPLACE_LOOT_MODIFIER = GLM.register("replace_loot_modifier", ReplaceLootModifier.CODEC);
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_ITEM_MODIFIER = GLM.register("add_item_modifier", AddItemModifier.CODEC);
}
