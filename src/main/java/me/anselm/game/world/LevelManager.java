package me.anselm.game.world;

import org.joml.Vector2f;
import org.joml.Vector2i;

public class LevelManager {

    private Level currentLevel;

    public LevelManager() {
        currentLevel = new Level(new Vector2i(0,0));
    }

    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    public Level getCurrentLevel() {
        return this.currentLevel;
    }
}
