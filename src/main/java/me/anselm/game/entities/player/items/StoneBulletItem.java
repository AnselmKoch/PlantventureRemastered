package me.anselm.game.entities.player.items;

import me.anselm.utils.AssetStorage;

public class StoneBulletItem extends Item{
    public StoneBulletItem(String name, int id) {
        super(name, id, AssetStorage.getTexture("stonebullet"));
    }
}
