package me.anselm.game.entities.player.items;

import me.anselm.graphics.texture.Texture;
import me.anselm.utils.AssetStorage;

public class StoneBullet extends Item{
    public StoneBullet(String name, int id) {
        super(name, id, AssetStorage.getTexture("stonebullet"));
    }
}
