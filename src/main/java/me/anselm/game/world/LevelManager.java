package me.anselm.game.world;

import me.anselm.game.Game;
import me.anselm.graphics.game.entity.EntityRenderer;
import me.anselm.graphics.game.hud.HUDRenderer;
import me.anselm.graphics.game.world.WorldRenderer;
import org.joml.Vector2i;

import java.util.HashSet;
import java.util.Random;
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

        Game.player.getBullets().clear();
        EntityRenderer.getRenderMesh().clear();

        EntityRenderer.getRenderMesh().addRenderable(Game.player);

        HUDRenderer.setShowPointingArrows(false);
        HUDRenderer.toggleArrows();

        levelIndex = levelDirection;
        Game.seed = new Random().nextInt(1000000);
        this.currentLevel = new Level(levelIndex);

    }

}
