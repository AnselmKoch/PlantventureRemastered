package me.anselm.game.entities.player.items.bullets;

import me.anselm.graphics.texture.Texture;
import me.anselm.utils.AssetStorage;
import me.anselm.utils.Position;
import org.joml.Vector3f;

public class WaterBullet extends Bullet{


    public WaterBullet(int damage, float shotSpeed, Vector3f position) {


        super(true, false, false, damage, shotSpeed,
                position, 8.0f, 8.0f, 1.0f, AssetStorage.getTexture("waterbullet"), Position.CENTER);
    }
}