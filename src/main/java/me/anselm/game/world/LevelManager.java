package me.anselm.game.world;

public class LevelManager {

    private Level currentLevel;

    public LevelManager() {
        currentLevel = new Level();
    }

    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    public Level getCurrentLevel() {
        return this.currentLevel;
    }
}
