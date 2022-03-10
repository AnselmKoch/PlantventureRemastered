package me.anselm.game.entities.player.items;

import me.anselm.utils.AssetStorage;

public class WaterBulletItem extends Item{

    public WaterBulletItem(String name, int id) {
        super(name, id, AssetStorage.getTexture("waterBullet"));
    }
}
