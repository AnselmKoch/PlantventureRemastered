package me.anselm.game;

import me.anselm.game.entities.Entity;
import me.anselm.game.entities.player.Player;
import me.anselm.game.entities.player.items.bullets.Bullet;
import me.anselm.game.powerups.BasicPowerup;
import me.anselm.game.powerups.Powerup;
import me.anselm.game.world.levels.Level;
import me.anselm.game.world.levels.LevelManager;
import me.anselm.game.world.levels.layouts.Difficulty;
import me.anselm.graphics.game.entity.EntityRenderer;
import me.anselm.graphics.game.hud.HUDRenderer;
import me.anselm.graphics.game.world.WorldRenderer;
import me.anselm.utils.LoggerUtils;
import org.joml.Random;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.slf4j.Logger;

public class Game {
    private static final Logger logger = LoggerUtils.getLogger(Game.class);

    public static Player player;
    public static LevelManager levelManager;

    public static int seed;
    public static int monsterDeathCounter;

    public static Vector2f mousePos;
    public static int levelCounter;

    public static boolean rendering = false;
    public static boolean ticking = false;

    public static Difficulty difficultyLevel;

    public static void init() {
        logger.info("Initializing game...");

        levelCounter = 0;
        difficultyLevel = Difficulty.PEACEFUL;

        rendering = true;
        ticking = true;

        monsterDeathCounter = 0;
        seed = new Random().nextInt(1000000);

        player = new Player(new Vector3f(200f,100f,1.0f));
        EntityRenderer.getRenderMesh().addRenderable(player);

        HUDRenderer.updatePlayerHearts();


        HUDRenderer.setShowPointingArrows(true);
        HUDRenderer.toggleArrows();

        Powerup.powerups.add(BasicPowerup.class);

        levelManager = new LevelManager();

    }

    public static void switchLevel() {
        WorldRenderer.getRenderMesh().clear();

        Game.player.getBullets().clear();
        EntityRenderer.getRenderMesh().clear();

        EntityRenderer.getRenderMesh().addRenderable(Game.player);

        HUDRenderer.setShowPointingArrows(false);
        HUDRenderer.toggleArrows();

        Game.levelCounter++;
        if(levelCounter == 1) {
            difficultyLevel = Difficulty.EASY;
        }

        Game.seed = new java.util.Random().nextInt(1000000);
        levelManager.setCurrentLevel(new Level(difficultyLevel));
    }

    public static void reset() {
        HUDRenderer.resetInventory();
        HUDRenderer.setShowPointingArrows(false);
        HUDRenderer.toggleArrows();

        for(Entity entity : levelManager.getCurrentLevel().getEnemyArrayList()) {
            entity.getHealthbar().destroy();
        }

        EntityRenderer.getRenderMesh().clear();
        WorldRenderer.getRenderMesh().clear();
        ticking = false;
    }

    public static void tick() {
        logger.info("Running game tick...");

        HUDRenderer.tick();

        movePlayer();
        player.calculateCurrentTile();
        player.processTick();

        levelManager.getCurrentLevel().tick();

        for(int i = 0; i < Game.player.getBullets().size(); i++) {
            Bullet bullet = Game.player.getBullets().get(i);
            bullet.tick();
        }

        if(player.getBullets().isEmpty()) {
            return;
        }


    }

    private static void movePlayer() {
        if(Input.pressedKeys[0]) {
            player.move(new Vector2f(0.0f,0.5f));
        }
        if(Input.pressedKeys[1]) {
            player.move(new Vector2f(-0.5f,0.0f));
        }
        if(Input.pressedKeys[2]) {
            player.move(new Vector2f(0.0f,-0.5f));
        }
        if(Input.pressedKeys[3]) {
            player.move(new Vector2f(0.5f, 0.0f));
        }
    }
}
