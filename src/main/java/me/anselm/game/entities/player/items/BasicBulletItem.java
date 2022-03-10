package me.anselm.game.entities.player.items;

import me.anselm.utils.AssetStorage;

public class BasicBulletItem extends Item{

    public BasicBulletItem(String name, int id) {
        super(name, id, AssetStorage.getTexture("basicbullet"));
    }
}
