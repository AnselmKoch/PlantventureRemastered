package me.anselm.game.powerups;

import me.anselm.graphics.powerups.PowerupIcon;
import me.anselm.utils.AssetStorage;
import org.joml.Vector3f;

public class BasicPowerup extends Powerup{

    public BasicPowerup(Vector3f position) {
        super(2, 0.5f, 3.0f, 1, new PowerupIcon(BasicPowerup.class, position, AssetStorage.getTexture("basicitem")));
    }
}
