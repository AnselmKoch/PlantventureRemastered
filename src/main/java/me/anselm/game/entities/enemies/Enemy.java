package me.anselm.game.entities.enemies;

import me.anselm.game.Game;
import me.anselm.game.entities.Entity;
import me.anselm.game.physics.CollitionDetector;
import me.anselm.game.world.LevelManager;
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
        this.setSpeed(0.5f);
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
        for (Enemy enemy : Game.levelManager.getCurrentLevel().getEnemyArrayList()) {
            if (CollitionDetector.colides(this, enemy)) {
                Vector2f enemyPos = new Vector2f(enemy.getPosition().x, enemy.getPosition().y).normalize();
                Vector2f currentPos = new Vector2f(this.getPosition().x, this.getPosition().y).normalize();
                enemy.setMomentum(currentPos.mul(-0.25f));
                this.setMomentum(enemyPos.mul(-0.25f));
            }
        }
        move(this.getMomentum());
        EntityRenderer.getRenderMesh().changeRenderable(this);


        doDamageColor();
        EntityRenderer.getRenderMesh().changeRenderable(this);
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
