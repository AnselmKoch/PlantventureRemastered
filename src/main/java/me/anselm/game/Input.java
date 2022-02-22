package me.anselm.game;

import me.anselm.game.entities.player.items.Bullet;
import me.anselm.graphics.Window;
import me.anselm.utils.LoggerUtils;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.slf4j.Logger;

public class Input {
    private static final Logger logger = LoggerUtils.getLogger(Input.class);

    public static boolean[] pressedKeys = new boolean[] {
            false,false,false,false,false
    };

    private static GLFWMouseButtonCallback clickCallback = new GLFWMouseButtonCallback() {
        @Override
        public void invoke(long window, int button, int action, int mods) {
            if (action == GLFW.GLFW_PRESS) {

                Vector3f world = new Matrix4f(Window.view).mul(Window.perspective).unproject(Game.mousePos.x, Game.mousePos.y, 0.0f,
                        new int[]{0, 0, Window.TARGETWITDTH, Window.TARGETHEIGHT}, new Vector3f());

                world.sub(Game.player.getPosition()).normalize();
                Game.player.shoot(new Vector2f(world.x, world.y));
            }
        }
    };

    private static GLFWKeyCallback keyCallback = new GLFWKeyCallback() {

        //W = 87 A = 65 S=83 D=68
        @Override
        public void invoke(long window, int key, int scancode, int action, int mods) {
            if(action == GLFW.GLFW_PRESS) {
                switch (key) {
                    case 87: pressedKeys[0] = true; break;
                    case 65: pressedKeys[1] = true;break;
                    case 83: pressedKeys[2] = true; break;
                    case 68: pressedKeys[3] = true;break;
                }
            }
            if(action == GLFW.GLFW_RELEASE) {
                switch (key) {
                    case 87: pressedKeys[0] = false; break;
                    case 65: pressedKeys[1] = false; break;
                    case 83: pressedKeys[2] = false; break;
                    case 68: pressedKeys[3] = false; break;
                }
            }
        }
    };

    private Input() {

    }

    public static GLFWKeyCallback getKeyCallback() {
        return keyCallback;
    }

    public static GLFWMouseButtonCallback getClickCallBack() {
        return clickCallback;
    }
}
