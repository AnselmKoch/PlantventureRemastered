package me.anselm.game.entities;

import me.anselm.game.entities.player.Player;
import me.anselm.graphics.game.Renderable;
import me.anselm.graphics.game.hud.HUDRenderer;
import me.anselm.graphics.texture.Texture;
import me.anselm.utils.LoggerUtils;
import me.anselm.utils.Position;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.slf4j.Logger;

public abstract class Entity extends Renderable {
    private static final Logger logger = LoggerUtils.getLogger(Entity.class);


    private Vector2f momentum;

    private float damage, speed, shotspeed, cooldown;

    private boolean doDamangeAnimation;
    private boolean isInvincible;
    private  final int invincTime;
    private int crtInvincTime;
    private int health;
    public static final int MAX_HEALTH = 10;


    private boolean doTransparencyOnDamage;
    private float damageFrame;

    public Entity(Vector3f position, float width, float height, float size, Texture texture, Position center, boolean transparency) {
        super(position, width, height, size, texture, center);
        this.momentum = new Vector2f(0.0f,0.0f);
        this.doDamangeAnimation = false;
        this.isInvincible = false;
        this.invincTime = 60;
        this.crtInvincTime = 0;
        this.doTransparencyOnDamage = transparency;
    }

    public abstract void onRender();

    public abstract void move(Vector2f momentum);

    public abstract void tick();

    public Vector2f getMomentum() {
        return this.momentum;
    }

    public void setMomentum(Vector2f momentum) {
        this.momentum.add(momentum).normalize(this.speed);
    }

    public void setMomentumTotal(Vector2f momentum) {
        this.momentum = momentum;
    }


    public void doDamageColor() {
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

        Vector4f color;
        if(this.doTransparencyOnDamage) {
            color = new Vector4f(1.0f, 1.0f - this.getDamageFrame(), 1.0f - this.getDamageFrame(), 1.0f - this.getDamageFrame());
        }else{
            color = new Vector4f(1.0f, 1.0f - this.getDamageFrame(),1.0f - this.getDamageFrame(), 1.0f);
        }
        this.setColor(color);
    }


    public void onDamage(int damage) {

        if(isInvincible) {
            return;
        }

        logger.info("HEALTH BEFORE : " + this.health);

        this.health -= damage;

        logger.info("HEALTH AFTER : " + this.health);

        if(health <= 0) {
            this.die();
        }
        this.doDamangeAnimation = true;
        isInvincible = true;

        this.setDamageFrame(0.0f);

        if(this instanceof Player) {
            HUDRenderer.updatePlayerHearts();
        }
    }

    public abstract void die();

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

    public boolean isInvincible() {
        return isInvincible;
    }

    public void setInvincible(boolean invincible) {
        isInvincible = invincible;
    }

    public int getInvincTime() {
        return invincTime;
    }

    public int getCrtInvincTime() {
        return crtInvincTime;
    }

    public void setCrtInvincTime(int crtInvincTime) {
        this.crtInvincTime = crtInvincTime;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
