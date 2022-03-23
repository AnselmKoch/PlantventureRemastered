package me.anselm.game.entities.enemies;

import me.anselm.graphics.texture.Texture;
import me.anselm.utils.AssetStorage;
import me.anselm.utils.Position;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Reflector extends Enemy{

    public Reflector(Vector3f position) {
        super(position, 10.0f, 10.0f, 1.0f, AssetStorage.getTexture("..."),
                Position.CENTER, false, 10);
    }

    @Override
    public void onRender() {

    }

    @Override
    public void move(Vector2f momentum) {

    }

    @Override
    public void tick() {

    }

    @Override
    public void die() {

    }

    @Override
    public void attack() {

    }
}
