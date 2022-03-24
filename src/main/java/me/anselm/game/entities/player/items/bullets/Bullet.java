package me.anselm.game.entities.player.items.bullets;

import me.anselm.game.Game;
import me.anselm.game.entities.Entity;
import me.anselm.game.entities.player.Player;
import me.anselm.graphics.game.entity.EntityRenderer;
import me.anselm.graphics.texture.Texture;
import me.anselm.utils.LoggerUtils;
import me.anselm.utils.Position;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.slf4j.Logger;

public abstract class Bullet extends Entity {
    private static final Logger logger = LoggerUtils.getLogger(Bullet.class);

    private boolean isHoming;
    private boolean isPiercing;
    private boolean isBouncing;


    public Bullet(boolean bouncing, boolean piercing, boolean homing, int damage, float shotSpeed, Vector3f position, float width, float height, float size, Texture texture, Position orientation) {
        super(position, width, height, size, texture, orientation, false, 0);
        this.setShotspeed(shotSpeed);
        this.setDamage(damage);
        this.isHoming = homing;
        this.isPiercing = piercing;
        this.isBouncing = bouncing;
    }

    @Override
    public void onRender() {

    }

    @Override
    public void move(Vector2f momentum) {
        this.addToPosition(momentum, 0.0f);
        EntityRenderer.getRenderMesh().changeRenderable(this);
    }

    @Override
    public void tick() {
        calculateHoming();
        calculateBounce();


        this.move(getMomentum());
    }

    private void calculateBounce() {
        if(!this.isBouncing) {
            return;
        }

        if(this.getPosition().x < 0) {
            this.getMomentum().x *= -1;
        }

        if(this.getPosition().x > 400) {
            this.getMomentum().x *= -1;
        }

        if(this.getPosition().y < 0) {
            this.getMomentum().y *= -1;
        }

        if(this.getPosition().y > 200) {
            this.getMomentum().y *= -1;
        }
    }

    private void calculateHoming() {
        if(!this.isHoming) {
            return;
        }


        Entity nearestEntity = calculateNearestEnemy();

        if(nearestEntity == null || nearestEntity instanceof Player) {
            return;
        }

        this.getPosition().z = 0;

        Vector3f nearestEntityPosition = new Vector3f().set(nearestEntity.getPosition());
        nearestEntityPosition.z = 0.0f;
        Vector3f direction =   nearestEntityPosition.sub(this.getPosition()).normalize(0.1f);
        this.setMomentum(new Vector2f(direction.x, direction.y).mul(Game.player.getShotspeed()).mul(this.getShotspeed()));
    }


    @Override
    public void die() {

    }

    private Entity calculateNearestEnemy() {
        if(Game.levelManager.getCurrentLevel().getEnemyArrayList().size() == 0) {
            return null;
        }

        Entity nearest = Game.levelManager.getCurrentLevel().getEnemyArrayList().get(0);
        for(Entity entity : Game.levelManager.getCurrentLevel().getEnemyArrayList()) {
            Vector3f vectorLength = new Vector3f().set(entity.getPosition());
            Vector3f length = vectorLength.sub(this.getPosition());

            Vector3f nearestPos = new Vector3f().set(nearest.getPosition());

            if(length.length() < nearestPos.sub(this.getPosition()).length()) {
                nearest = entity;
            }
        }

        return nearest;
    }

    public boolean isHoming() {
        return isHoming;
    }

    public void setHoming(boolean homing) {
        isHoming = homing;
    }

    public boolean isPiercing() {
        return isPiercing;
    }

    public void setPiercing(boolean piercing) {
        isPiercing = piercing;
    }
}
