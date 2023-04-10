package io.github.tt432.kitchenkarrot.item;


import io.github.tt432.kitchenkarrot.Kitchenkarrot;
import io.github.tt432.kitchenkarrot.client.plate.PlateList;
import io.github.tt432.kitchenkarrot.util.json.JsonUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.io.InputStreamReader;
import java.util.List;

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

    private boolean canPutOnPlate(){
        ResourceManager manager = Minecraft.getInstance().getResourceManager();
        try {
            ResourceLocation resourceLocation = new ResourceLocation(Kitchenkarrot.MOD_ID, "plate/list.json");
            if (manager.hasResource(resourceLocation)){
                List<Resource> resources = manager.getResources(resourceLocation);
                for (Resource resource : resources){
                    InputStreamReader reader = new InputStreamReader(resource.getInputStream());
                    PlateList list = JsonUtils.INSTANCE.noExpose.fromJson(reader, PlateList.class);
                    for (String string:list.plates){
                        int last = string.lastIndexOf("_");
                        string = string.substring(0, last);
                        if (this.getRegistryName().toString().equals(string))
                        {return true;}
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> tooltip, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, tooltip, pIsAdvanced);
        if (this.canPutOnPlate()){
            tooltip.add(new TranslatableComponent("info.kitchenkarrot.can_be_dished"));
        }
    }
}
