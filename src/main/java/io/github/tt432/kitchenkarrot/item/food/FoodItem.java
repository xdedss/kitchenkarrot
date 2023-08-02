package io.github.tt432.kitchenkarrot.item.food;

import io.github.tt432.kitchenkarrot.item.EffectEntry;
import io.github.tt432.kitchenkarrot.item.ModBaseItem;
import io.github.tt432.kitchenkarrot.item.ModItems;

public class FoodItem extends ModBaseItem {

    public FoodItem(int nutrition, float saturation, EffectEntry... effectEntries) {
        super(FoodUtil.effectFood(ModItems.defaultProperties(), nutrition, saturation,false, effectEntries));
    }

}
