package me.anselm.game.world;

import me.anselm.game.entities.enemies.Enemy;
import me.anselm.graphics.game.entity.EntityRenderer;
import me.anselm.graphics.game.world.WorldRenderer;
import org.joml.Vector2f;
import org.joml.Vector2i;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LevelManager {

    private Level currentLevel;

    public static Vector2i levelIndex;

    public static Set<String> levelSet = new HashSet<>();

    public LevelManager() {
        levelIndex = new Vector2i(0,0);
        currentLevel = new Level(levelIndex);
    }

    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    public Level getCurrentLevel() {
        return this.currentLevel;
    }

    public void switchLevel(Vector2i levelDirection) {
        WorldRenderer.getRenderMesh().clear();

        for(Enemy enemy : currentLevel.getEnemyArrayList()) {
            EntityRenderer.getRenderMesh().removeRenderable(enemy);
        }

        levelIndex = levelDirection;
        this.currentLevel = new Level(levelIndex);

    }

}
