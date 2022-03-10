package me.anselm.utils;

import me.anselm.graphics.texture.Texture;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Locale;

public class AssetStorage {
    private static final Logger logger = LoggerUtils.getLogger(AssetStorage.class);

    private static HashMap<String, Texture> textureHashMap = new HashMap<>();

    public static void init() {
        textureHashMap.put("basicbullet", new Texture("player/bullets/basicBullet"));
        textureHashMap.put("stonebullet", new Texture("player/bullets/stonebullet"));
        textureHashMap.put("player", new Texture("player"));
        textureHashMap.put("grass", new Texture("tiles/grass"));
        textureHashMap.put("dirt", new Texture("tiles/dirt"));
        textureHashMap.put("itemContainer".toLowerCase(), new Texture("HUD/ItemContainer"));
        textureHashMap.put("zombie", new Texture("enemy/zombie"));
        textureHashMap.put("shovel", new Texture("tiles/shovel"));
        textureHashMap.put("arrow", new Texture("util/arrow"));
        textureHashMap.put("arrowup", new Texture("util/arrowup"));
        textureHashMap.put("fullheart", new Texture("player/heart/fullheart"));
        textureHashMap.put("emptyheart", new Texture("player/heart/emptyheart"));
        textureHashMap.put("halfheart", new Texture("player/heart/halfheart"));
        textureHashMap.put("background", new Texture("util/background"));
        textureHashMap.put("button", new Texture("util/button"));
        textureHashMap.put("puddle", new Texture("tiles/puddle"));
        textureHashMap.put("waterbullet", new Texture("player/bullets/waterBullet"));
        textureHashMap.put("basicitem", new Texture("powerups/basicItem"));
        textureHashMap.put("stone", new Texture("tiles/stone"));
        textureHashMap.put("poison", new Texture("tiles/poison"));
        textureHashMap.put("treasure", new Texture("tiles/treasure"));
        textureHashMap.put("poisonbullet", new Texture("player/bullets/poisonBullet"));
    }

    public static void addToHashmap(String key, Texture texture) {
        textureHashMap.put(key.toLowerCase(), texture);
    }

    public static Texture getTexture(String key) {
        return textureHashMap.get(key.toLowerCase());
    }
}
