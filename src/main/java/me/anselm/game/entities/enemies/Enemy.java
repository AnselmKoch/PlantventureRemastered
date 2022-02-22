package me.anselm.game.entities.enemies;

import me.anselm.game.entities.Entity;
import me.anselm.graphics.game.entity.EntityRenderer;
import me.anselm.graphics.texture.Texture;
import me.anselm.utils.LoggerUtils;
import me.anselm.utils.Position;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.slf4j.Logger;

public class Enemy extends Entity {
    private static final Logger logger = LoggerUtils.getLogger(Enemy.class);

    private boolean doDamangeAnimation;

    public Enemy(Vector3f position, float width, float height, float size, Texture texture, Position center) {
        super(position, width, height, size, texture, center);
    }

    @Override
    public void onRender() {
        doDamageColor();
        EntityRenderer.getRenderMesh().changeRenderable(this);
    }

    @Override
    public void move(Vector2f momentum) {
        this.addToPosition(momentum);
    }

    @Override
    public void tick() {
        move(this.getMomentum());
    }

    private void doDamageColor() {
        if(doDamangeAnimation) {
            this.setDamageFrame(this.getDamageFrame() + 0.05f);
            if(getDamageFrame() > 1.0f) {
                this.doDamangeAnimation = false;
            }
        }else{
            if(this.getDamageFrame() > 0.05f) {
                this.setDamageFrame(this.getDamageFrame() - 0.05f);
            }
        }

        this.setColor(new Vector4f(1.0f, 1.0f - this.getDamageFrame(),1.0f - this.getDamageFrame(), 1.0f));
    }

    @Override
    public void onDamage() {
        this.doDamangeAnimation = true;
        this.setDamageFrame(0.0f);
    }
}
