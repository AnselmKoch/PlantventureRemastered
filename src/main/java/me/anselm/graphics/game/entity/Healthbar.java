package me.anselm.graphics.game.entity;

import me.anselm.game.entities.Entity;
import me.anselm.utils.LoggerUtils;
import me.anselm.utils.Position;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.slf4j.Logger;

public class Healthbar{
    Logger logger = LoggerUtils.getLogger(Healthbar.class);

    private Rectangle backGround;
    private Rectangle healthGreen;
    private Rectangle healthRed;

    private float healthRatio;
    private Entity entity;
    private float width;
    private float height;
    private int totalHealth;

    public Healthbar(Vector3f position, float width, float height, int totalHealth, Entity entity) {
        healthRatio = width / totalHealth;
        this.width = width;
        this.height = height;
        this.entity = entity;
        this.totalHealth = totalHealth;

        position.y = 5.0f;

        backGround = new Rectangle(position, width + 2.0f, height + 2.0f, 1.0f, Position.CENTER, new Vector4f(0.0f,0.0f,0.0f,1.0f));
        healthGreen = new Rectangle(position, width, height, 1.0f, Position.CENTER, new Vector4f(0.0f,1.0f,0.0f,1.0f));

        position.x -= width / 2f;
        healthRed = new Rectangle(position, 0f, height, 1.0f, Position.BOTTOMLEFT, new Vector4f(1.0f,0.0f,0.0f,1.0f));
    }

    public void update(int newHealth) {
        newHealth = totalHealth - newHealth;
        healthRed.setWidth(healthRatio * newHealth);
        HealthbarRenderer.getRenderMesh().changeRenderable(healthRed);
    }

    public void updatePosition(Vector3f position) {
        position.y -= entity.getHeight() / 2f + 2.0f;
        backGround.setPosition(position);
        healthGreen.setPosition(position);

        position.y -= this.height / 2.0f;
        position.x -= width / 2.0f;
        healthRed.setPosition(position);

        HealthbarRenderer.getRenderMesh().changeRenderable(healthRed);
        HealthbarRenderer.getRenderMesh().changeRenderable(healthGreen);
        HealthbarRenderer.getRenderMesh().changeRenderable(backGround);

    }

    public void destroy() {
        HealthbarRenderer.getRenderMesh().removeRenderable(healthRed);
        HealthbarRenderer.getRenderMesh().removeRenderable(healthGreen);
        HealthbarRenderer.getRenderMesh().removeRenderable(backGround);
    }

    public Rectangle getBackGround() {
        return backGround;
    }

    public Rectangle getHealthGreen() {
        return healthGreen;
    }

    public Rectangle getHealthRed() {
        return healthRed;
    }
}
