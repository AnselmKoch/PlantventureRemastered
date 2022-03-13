package me.anselm.game.powerups;

import me.anselm.graphics.powerups.PowerupIcon;
import org.joml.Vector3f;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Powerup {

    private final int health;
    private final float speed;
    private final float shotSpeed;
    private final int damage;
    private final PowerupIcon powerupIcon;

    public static List<Class> powerups = new ArrayList<>();

    public Powerup(int upHealth, float upSpeed, float upShotspeed, int upDamage, PowerupIcon powerupIcon) {
        this.health = upHealth;
        this.damage = upDamage;
        this.speed = upSpeed;
        this.shotSpeed = upShotspeed;
        this.powerupIcon = powerupIcon;
    }

    private static Powerup createInstance(Class clazz, Vector3f position) {
        try {
            Constructor constructor = clazz.getConstructor(Vector3f.class);
            try {
                Powerup powerup = (Powerup) constructor.newInstance(position);
                return powerup;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Powerup getRandomPowerup(Vector3f position) {
        return createInstance(powerups.get(new Random().nextInt(powerups.size())), position);
    }

    public PowerupIcon getPowerupIcon() {
        return this.powerupIcon;
    }

    public int getHealth() {
        return health;
    }

    public float getSpeed() {
        return speed;
    }

    public float getShotSpeed() {
        return shotSpeed;
    }

    public int getDamage() {
        return damage;
    }
}
