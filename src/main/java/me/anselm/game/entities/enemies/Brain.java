package me.anselm.game.entities.enemies;

import me.anselm.game.Game;
import me.anselm.graphics.game.entity.EntityRenderer;
import me.anselm.graphics.game.entity.Rectangle;
import me.anselm.graphics.texture.Texture;
import me.anselm.utils.AssetStorage;
import me.anselm.utils.Position;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

public class Brain extends Enemy{

    private Rectangle rectangle;
    private Vector2f momentum;

    public Brain(Vector3f position) {
        super(position, 15, 15, 1.0f, AssetStorage.getTexture("brain"), Position.CENTER, false, 10);
        this.setCooldown(1.0f);
        this.getShield();
    }

    @Override
    public void onRender() {

    }

    @Override
    public void move(Vector2f momentum) {

    }

    @Override
    public void tick() {
        if(rectangle == null) {
            return;
        }
    }

    @Override
    public void die() {

    }

    @Override
    public void attack() {
        if(this.isShieldActive()) {
            return;
        }


        this.getShield();
    }
}
