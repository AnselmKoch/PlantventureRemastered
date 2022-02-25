package me.anselm.game.entities.player.items;

import me.anselm.utils.AssetStorage;

public class BasicBullet extends Item{

    public BasicBullet(String name, int id) {
        super(name, id, AssetStorage.getTexture("basicbullet"));
    }
}
