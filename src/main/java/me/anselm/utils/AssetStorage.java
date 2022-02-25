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
    }

    public static void addToHashmap(String key, Texture texture) {
        textureHashMap.put(key.toLowerCase(), texture);
    }

    public static Texture getTexture(String key) {
        return textureHashMap.get(key.toLowerCase());
    }
}
