package me.anselm.game.entities;

import me.anselm.game.entities.enemies.Zombie;
import me.anselm.game.entities.player.Player;
import me.anselm.graphics.game.Renderable;
import me.anselm.graphics.game.entity.Healthbar;
import me.anselm.graphics.game.entity.HealthbarRenderer;
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
    private int invincTime;
    private int crtInvincTime;
    private int health;
    public static final int MAX_HEALTH = 10;

    private Healthbar healthbar;

    private Vector3f healthBarPos;

    private boolean doTransparencyOnDamage;
    private float damageFrame;

    public Entity(Vector3f position, float width, float height, float size, Texture texture, Position center, boolean transparency, int health) {
        super(position, width, height, size, texture, center);
        this.momentum = new Vector2f(0.0f,0.0f);
        this.doDamangeAnimation = false;
        this.isInvincible = false;
        this.invincTime = 60;
        this.crtInvincTime = 0;
        this.doTransparencyOnDamage = transparency;
        this.health = health;

        if(this instanceof Player) {
            return;
        }

        this.healthBarPos = new Vector3f().set(this.getPosition());
        this.healthBarPos.y -= this.height;

        this.healthbar = new Healthbar(healthBarPos, width, 3.0f, this.getHealth(), this);
    }

    public abstract void onRender();

    public abstract void move(Vector2f momentum);

    public abstract void tick();

    public Vector2f getMomentum() {
        return this.momentum;
    }

    public void setMomentum(Vector2f momentum) {
        if(this.speed != 0.0f) {
            this.momentum.add(momentum).normalize(this.speed);
        }else{
            this.momentum.add(momentum).normalize(this.shotspeed);
        }
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

        this.health -= damage;

        if(this instanceof Zombie) {
            this.healthbar.update(this.getHealth());
            HealthbarRenderer.getRenderMesh().changeRenderable(this.healthbar.getHealthRed());
        }

        if(health <= 0) {
            this.die();
        }
        this.doDamangeAnimation = true;
        isInvincible = true;

        this.setDamageFrame(0.0f);

        if(this instanceof Player) {
            HUDRenderer.updatePlayerHearts();
        }

        if(this.healthbar == null) {
            return;
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

    public void setInvincTime(int time) {
        this.invincTime = time;
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

    public Healthbar getHealthbar() {
        return healthbar;
    }
}
