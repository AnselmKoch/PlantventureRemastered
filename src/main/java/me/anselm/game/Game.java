package me.anselm.game;

import me.anselm.game.entities.Entity;
import me.anselm.game.entities.player.Player;
import me.anselm.game.entities.player.items.bullets.Bullet;
import me.anselm.game.powerups.BasicPowerup;
import me.anselm.game.powerups.Powerup;
import me.anselm.game.world.levels.LevelManager;
import me.anselm.graphics.game.entity.EntityRenderer;
import me.anselm.graphics.game.hud.HUDRenderer;
import me.anselm.graphics.game.world.WorldRenderer;
import me.anselm.utils.LoggerUtils;
import org.joml.Random;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.slf4j.Logger;

public class Game {
    private static final Logger logger = LoggerUtils.getLogger(Game.class);

    public static Player player;
    public static LevelManager levelManager;

    public static int seed;
    public static int monsterDeathCounter;

    public static Vector2f mousePos;

    public static boolean rendering = false;
    public static boolean ticking = false;

    public static void init() {
        logger.info("Initializing game...");

        rendering = true;
        ticking = true;

        monsterDeathCounter = 0;
        seed = new Random().nextInt(1000000);

        player = new Player(new Vector3f(200f,100f,1.0f));
        EntityRenderer.getRenderMesh().addRenderable(player);

        HUDRenderer.updatePlayerHearts();
        HUDRenderer.toggleArrows();

        Powerup.powerups.add(BasicPowerup.class);

        levelManager = new LevelManager();

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
