package io.github.tt432.kitchenkarrot.client.plate;

import io.github.tt432.kitchenkarrot.Kitchenkarrot;
import io.github.tt432.kitchenkarrot.block.PlateHolderMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author DustW
 **/
public class PlateModelRegistry {

    public static final Map<ResourceLocation, ResourceLocation> MODEL_MAP = new HashMap<>();

    public static ResourceLocation DEFAULT_NAME = new ResourceLocation(Kitchenkarrot.MOD_ID, "plate");
    public static BakedModel DEFAULT_MODEL;

    /*public static BakedModel get(ResourceLocation resourceLocation) {
        return MODEL_MAP.getOrDefault(resourceLocation,
                DEFAULT_MODEL != null ? DEFAULT_MODEL : (DEFAULT_MODEL = MODEL_MAP.get(DEFAULT_NAME))
        );
    }*/

    static ResourceLocation from(ModelResourceLocation modelResourceLocation) {
        return new ResourceLocation(modelResourceLocation.getNamespace(), modelResourceLocation.getPath().split("plates/")[1]);
    }

    public static ResourceLocation to(ResourceLocation resourceLocation) {
        return new ResourceLocation(resourceLocation.getNamespace(), "plates/" + resourceLocation.getPath());
    }


    public static void register(ModelRegistryEvent e) {
        PlateList.INSTANCE.plates.clear();

        Set<String> plates = new HashSet<>();
        PlateHolderMap.plateHolder.forEach((key, value) -> {
            for (int i = 1; i <= value; i++) {
                plates.add(new ResourceLocation(Kitchenkarrot.MOD_ID, Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(key)).getPath()) + "_" + i);
            }
        });

        PlateList.INSTANCE.plates.addAll(plates);

        ForgeModelBakery.addSpecialModel(to(DEFAULT_NAME));
        for (var info : PlateList.INSTANCE.plates) {
            ForgeModelBakery.addSpecialModel(to(new ResourceLocation(info)));
        }

        //bakeModel();

    }

    static BlockModel getModel(String info) {
        ResourceManager manager = Minecraft.getInstance().getResourceManager();
        ResourceLocation name = new ResourceLocation(info);
        String namespace = name.getNamespace();
        String path = name.getPath();
        ResourceLocation modelName = new ResourceLocation(namespace, "models/plates/" + path + ".json");
        if (manager.hasResource(modelName)) {
            try {
                Resource resource = manager.getResource(modelName);
                String json = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
                return BlockModel.fromString(json);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return null;
    }

    /*public static void bakeModel(*//*ModelBakeEvent evt*//*) {
        MODEL_MAP.clear();
        MODEL_MAP.put(DEFAULT_NAME, DEFAULT_NAME);
        for (String info : PlateList.INSTANCE.plates) {
            ModelResourceLocation modelName = to(new ResourceLocation(info));
            MODEL_MAP.put(from(modelName), modelName);
        }
    }*/

}