package me.anselm.game.entities.player.items;

import com.sun.org.apache.bcel.internal.generic.LOOKUPSWITCH;
import me.anselm.game.Game;
import me.anselm.game.entities.Entity;
import me.anselm.graphics.Window;
import me.anselm.graphics.game.Renderable;
import me.anselm.graphics.game.entity.EntityRenderer;
import me.anselm.graphics.texture.Texture;
import me.anselm.utils.LoggerUtils;
import me.anselm.utils.Position;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.slf4j.Logger;

public class Bullet extends Entity {
    private static final Logger logger = LoggerUtils.getLogger(Bullet.class);


    public Bullet(Vector3f position, float width, float height, float size, Texture texture, Position orientation) {
        super(position, width, height, size, texture, orientation);
    }

    @Override
    public void onRender() {

    }

    @Override
    public void move(Vector2f momentum) {
        this.addToPosition(momentum);
        EntityRenderer.getRenderMesh().changeRenderable(this);
    }

    @Override
    public void tick() {
        this.move(getMomentum());
    }

    @Override
    public void onDamage() {

    }
}
