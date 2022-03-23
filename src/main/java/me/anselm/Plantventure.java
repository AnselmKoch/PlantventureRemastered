package me.anselm;

import me.anselm.game.Game;
import me.anselm.graphics.game.entity.EntityRenderer;
import me.anselm.graphics.game.entity.HealthbarRenderer;
import me.anselm.graphics.game.font.FontRenderer;
import me.anselm.graphics.game.hud.HUDRenderer;
import me.anselm.graphics.game.world.WorldRenderer;
import me.anselm.graphics.menu.MenuRenderer;
import me.anselm.menu.MenuManagar;
import me.anselm.utils.AssetStorage;
import org.slf4j.Logger;
import me.anselm.graphics.Window;
import me.anselm.graphics.shaders.Shader;
import me.anselm.utils.LoggerUtils;
import org.lwjgl.Version;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;

import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public class Plantventure {
    private static final Logger logger = LoggerUtils.getLogger(Plantventure.class);

    public static boolean running;


    public static int ups;


    public void run() {
        logger.info("Starting game...");
        logger.info("LWJGL: " + Version.getVersion() + "!");

        running = true;

        Window.init();
        Shader.init();
        AssetStorage.init();
        MenuRenderer.init();
        WorldRenderer.init();
        HUDRenderer.init();
        EntityRenderer.init();
        HealthbarRenderer.init();
        FontRenderer.init();
        MenuManagar.init();

        long lastTime = System.nanoTime();
        double delta = 0.0;
        double ns = 1000000000.0 / 60.0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1.0) {
                Window.handleMouse();

                if(Game.ticking) {
                    Game.tick();
                }
                updates++;
                delta--;
            }

            Window.render();
            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                Window.ups = updates;
                Window.fps = frames;
                logger.info(updates + " ups, " + frames + " fps");
                updates = 0;
                frames = 0;
            }

            if(glfwWindowShouldClose(Window.window)) {
                // Free the window callbacks and destroy the window
                running = false;
                glfwFreeCallbacks(Window.window);
                glfwDestroyWindow(Window.window);

                // Terminate GLFW and free the error callback
                glfwTerminate();
            }
        }
    }

    public static void main(String[] args) {
        new Plantventure().run();
    }
}
