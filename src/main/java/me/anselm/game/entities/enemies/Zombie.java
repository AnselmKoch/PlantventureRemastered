package me.anselm.game.entities.enemies;

import me.anselm.game.Game;
import me.anselm.game.entities.Entity;
import me.anselm.game.physics.CollitionDetector;
import me.anselm.graphics.game.entity.EntityRenderer;
import me.anselm.graphics.texture.Texture;
import me.anselm.utils.AssetStorage;
import me.anselm.utils.LoggerUtils;
import me.anselm.utils.Position;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.slf4j.Logger;

public class Zombie extends Entity {
    private static final Logger logger = LoggerUtils.getLogger(Zombie.class);

    private boolean doDamangeAnimation;

    public Zombie(Vector3f position) {
        super(position, 20.0f, 20.0f, 1.0f, AssetStorage.getTexture("zombie"), Position.CENTER,  false);
        this.setSpeed(0.5f);
        this.setHealth(10);
    }

    @Override
    public void onRender() {

    }

    @Override
    public void move(Vector2f momentum) {
        this.addToPosition(momentum.normalize().mul(this.getSpeed()));
    }

    @Override
    public void tick() {
        Vector3f playerPos = new Vector3f().set(Game.player.getPosition());

        playerPos.sub(this.getPosition()).normalize();
        Vector2f momentum = new Vector2f(playerPos.x,playerPos.y);
        this.setMomentum(momentum);
        for (Zombie zombie : Game.levelManager.getCurrentLevel().getEnemyArrayList()) {
            if (CollitionDetector.colides(this, zombie)) {
                Vector2f enemyPos = new Vector2f(zombie.getPosition().x, zombie.getPosition().y).normalize();
                Vector2f currentPos = new Vector2f(this.getPosition().x, this.getPosition().y).normalize();
                zombie.setMomentum(currentPos.mul(-0.25f));
                this.setMomentum(enemyPos.mul(-0.25f));
            }
        }
        move(this.getMomentum());
        EntityRenderer.getRenderMesh().changeRenderable(this);


        this.doDamageColor();
        EntityRenderer.getRenderMesh().changeRenderable(this);

        if(!this.isInvincible()) {
            return;
        }
        this.setCrtInvincTime(this.getCrtInvincTime() + 1);

        if(this.getCrtInvincTime() >= this.getInvincTime()) {
            this.setInvincible(false);
            this.setCrtInvincTime(0);
        }
    }


    @Override
    public void die() {
        Game.monsterDeathCounter++;
        Game.levelManager.getCurrentLevel().getEnemyArrayList().remove(this);
        EntityRenderer.getRenderMesh().removeRenderable(this);
    }

}
