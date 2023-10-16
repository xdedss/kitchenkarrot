package io.github.tt432.kitchenkarrot.datagen.loottable;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifierProvider(DataGenerator gen, String modid) {
        super(gen, modid);
    }

    @Override
    protected void start() {}
}
