package me.anselm.game.entities.enemies;

import me.anselm.game.entities.Entity;
import me.anselm.graphics.game.entity.EntityRenderer;
import me.anselm.graphics.texture.Texture;
import me.anselm.utils.AssetStorage;
import me.anselm.utils.LoggerUtils;
import me.anselm.utils.Position;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.slf4j.Logger;

public class Bat extends Enemy{
    private static final Logger logger = LoggerUtils.getLogger(Bat.class);

    private boolean isAttacking;
    private Vector2f straveVec;
    private float attackTime = 1.0f;
    private float crtAttackTime = 0.0f;

    public Bat(Vector3f position) {
        super(position, 10.0f, 8.0f, 1.0f, AssetStorage.getTexture("bat"), Position.CENTER, false, 10);
        isAttacking = false;
        this.setCooldown(1.5f);
        this.setSpeed(0.75f);
    }

    @Override
    public void onRender() {

    }

    @Override
    public void move(Vector2f momentum) {

        if(!this.isAttacking) {
            this.doCollition(-3f);
            momentum.set(this.getMomentum());
            momentum.normalize().mul(this.getSpeed());
            this.addToPosition(momentum, 0.0f);

        }else{
            this.crtAttackTime += Entity.cooldownPerTick;
            if(this.crtAttackTime >= attackTime) {
                this.isAttacking = false;
                this.crtAttackTime = 0.0f;
                this.setSpeed(0.75f);
            }
            this.addToPosition(straveVec, 0.0f);
        }
    }

    @Override
    public void tick() {
        this.setMomentum(this.calculateDirectionToPlayer());
        move(this.calculateDirectionToPlayer());

    }

    @Override
    public void die() {

    }

    @Override
    public void attack() {
        isAttacking = true;
        straveVec = new Vector2f(this.getMomentum()).mul(4.0f);
        this.setSpeed(7.0f);
    }
}
