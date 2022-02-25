package me.anselm.game;

import me.anselm.game.entities.enemies.Enemy;
import me.anselm.game.entities.player.Player;
import me.anselm.game.entities.player.items.Bullet;
import me.anselm.game.physics.CollitionDetector;
import me.anselm.game.world.Level;
import me.anselm.game.world.LevelManager;
import me.anselm.graphics.Window;
import me.anselm.graphics.game.entity.EntityRenderer;
import me.anselm.utils.AssetStorage;
import me.anselm.utils.LoggerUtils;
import me.anselm.utils.Position;
import org.joml.Random;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;

import java.nio.DoubleBuffer;

public class Game {
    private static final Logger logger = LoggerUtils.getLogger(Game.class);

    public static Player player;
    public static LevelManager levelManager;

    public static int seed;

    public static Vector2f mousePos;

    private static DoubleBuffer posX = BufferUtils.createDoubleBuffer(1), posY = BufferUtils.createDoubleBuffer(1);
    public static void init() {
        logger.info("Initializing game...");

        seed = new Random().nextInt(10000);

        player = new Player(new Vector3f(200f,100f,1.0f), 15,15,1.0f, AssetStorage.getTexture("player"), Position.CENTER);
        EntityRenderer.getRenderMesh().addRenderable(player);


        levelManager = new LevelManager();
    }

    public static void tick() {
        logger.info("Running game tick...");

        GLFW.glfwGetCursorPos(Window.window, posX,posY);
        mousePos = new Vector2f((float)posX.get(0), Window.HEIGHT - (float)posY.get(0));

        movePlayer();
        player.calculateCurrentTile();
        player.tick();

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
