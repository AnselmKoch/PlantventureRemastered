package me.anselm.game.entities;

import me.anselm.graphics.game.Renderable;
import me.anselm.graphics.texture.Texture;
import me.anselm.utils.Position;
import org.joml.Vector2f;
import org.joml.Vector3f;

public abstract class Entity extends Renderable {


    private Vector2f momentum;

    private float damage, speed, shotspeed, cooldown;

    private float damageFrame;

    public Entity(Vector3f position, float width, float height, float size, Texture texture, Position center) {
        super(position, width, height, size, texture, center);
        this.momentum = new Vector2f(0.0f,0.0f);
    }

    public abstract void onRender();

    public abstract void move(Vector2f momentum);

    public abstract void tick();

    public Vector2f getMomentum() {
        return this.momentum;
    }

    public void setMomentum(Vector2f momentum) {
        this.momentum = momentum;
    }

    public abstract void onDamage();

    public float getDamageFrame() {
        return damageFrame;
    }

    public void setDamageFrame(float damageFrame) {
        this.damageFrame = damageFrame;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getShotspeed() {
        return shotspeed;
    }

    public void setShotspeed(float shotspeed) {
        this.shotspeed = shotspeed;
    }

    public float getCooldown() {
        return cooldown;
    }

    public void setCooldown(float cooldown) {
        this.cooldown = cooldown;
    }
}
