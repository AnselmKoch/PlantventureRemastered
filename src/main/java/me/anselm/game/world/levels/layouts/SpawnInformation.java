package me.anselm.game.world.levels.layouts;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class SpawnInformation {

    private Class enemyType;
    private Vector3f position;

    public SpawnInformation(Class clazz, Vector3f location) {
        this.enemyType = clazz;
        this.position = location;
    }

    public Class getEnemyType() {
        return enemyType;
    }

    public void setEnemyType(Class enemyType) {
        this.enemyType = enemyType;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setVector3f(Vector3f vector2f) {
        this.position = vector2f;
    }
}
