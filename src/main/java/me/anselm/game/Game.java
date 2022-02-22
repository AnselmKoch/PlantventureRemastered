package me.anselm.game;

import me.anselm.game.entities.player.Player;
import me.anselm.game.entities.player.items.BasicItem;
import me.anselm.game.entities.player.items.Bullet;
import me.anselm.game.physics.CollitionDetector;
import me.anselm.game.world.Level;
import me.anselm.game.world.LevelManager;
import me.anselm.graphics.Window;
import me.anselm.graphics.game.entity.EntityRenderer;
import me.anselm.graphics.texture.Texture;
import me.anselm.utils.LoggerUtils;
import me.anselm.utils.Position;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;

import java.nio.DoubleBuffer;

public class Game {
    private static final Logger logger = LoggerUtils.getLogger(Game.class);

    public static Player player;
    public static LevelManager levelManager;

    public static Vector2f mousePos;

    private static DoubleBuffer posX = BufferUtils.createDoubleBuffer(1), posY = BufferUtils.createDoubleBuffer(1);
    public static void init() {
        logger.info("Initializing game...");
        player = new Player(new Vector3f(200f,100f,1.0f), 15,15,1.0f, new Texture("player"), Position.CENTER);
        EntityRenderer.getRenderMesh().addRenderable(player);

        for(int i = 0; i <99; i++) {
            player.getInventory().addItem(BasicItem.class, new BasicItem("Bullet", 0, Bullet.bulletTex));
        }

        levelManager = new LevelManager();
    }

    public static void tick() {
        logger.info("Running game tick...");

        GLFW.glfwGetCursorPos(Window.window, posX,posY);
        mousePos = new Vector2f((float)posX.get(0), Window.HEIGHT - (float)posY.get(0));

        movePlayer();
        player.calculateCurrentTile();
        player.tick();


        for(int i = 0; i < Game.player.getBullets().size(); i++) {
            Bullet bullet = Game.player.getBullets().get(i);
            bullet.tick();
        }

        if(player.getBullets().isEmpty()) {
            return;
        }

        if(Level.enemy == null) {
           return;
        }

        Level.enemy.tick();

        for(int i = 0; i < Game.player.getBullets().size(); i++) {
            Bullet bullet = Game.player.getBullets().get(i);

            if(CollitionDetector.colides(bullet, Level.enemy)) {
                EntityRenderer.getRenderMesh().removeRenderable(bullet);
                Game.player.getBullets().remove(i);
                Level.enemy.onDamage();
            }
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
