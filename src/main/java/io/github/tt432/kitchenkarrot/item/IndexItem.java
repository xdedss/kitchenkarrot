package io.github.tt432.kitchenkarrot.item;

import net.minecraft.world.item.Item;

public class IndexItem extends Item {
    private int index;
    public int getIndex() {
        return index;
    }

    public IndexItem setIndex(int index) {
        this.index = index;
        return this;
    }
    public IndexItem(Properties pProperties) {
        super(pProperties);
    }
    public IndexItem(Properties pProperties,int index) {
        super(pProperties);
        this.setIndex(index);
    }
}
