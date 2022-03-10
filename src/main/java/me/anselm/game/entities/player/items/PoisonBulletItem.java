package me.anselm.game.entities.player.items;

import me.anselm.graphics.texture.Texture;
import me.anselm.utils.AssetStorage;

public class PoisonBulletItem extends Item{

    public PoisonBulletItem(String name, int id) {
        super(name, id, AssetStorage.getTexture("poisonbullet"));
    }
}
