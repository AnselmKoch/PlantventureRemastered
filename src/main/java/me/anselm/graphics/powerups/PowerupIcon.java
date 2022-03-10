package me.anselm.graphics.powerups;


import me.anselm.graphics.game.Renderable;
import me.anselm.graphics.texture.Texture;
import me.anselm.utils.Position;
import org.joml.Vector3f;

public class PowerupIcon extends Renderable {

    private final Class clazz;

    public PowerupIcon(Class clazz, Vector3f position, Texture texture) {
        super(position, 20.0f, 20.0f, 1.0f, texture, Position.BOTTOMLEFT);
        this.clazz = clazz;
    }


    public Class getClazz() {
        return clazz;
    }

}
