package me.anselm.game;

import me.anselm.graphics.Window;
import me.anselm.menu.MenuManagar;
import me.anselm.utils.LoggerUtils;
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

                if(Window.mousePos == null) {
                    return;
                }
                Window.handleMouseClick();
            }
        }
    };

    private static GLFWKeyCallback keyCallback = new GLFWKeyCallback() {

        //W = 87 A = 65 S=83 D=68
        //1 = 49, 2 = 50, 3 = 51, 4 =52, 5=53
        @Override
        public void invoke(long window, int key, int scancode, int action, int mods) {
            if(action == GLFW.GLFW_PRESS) {
                logger.info("KEY: " + key);
                switch (key) {
                    case 49: Game.player.switchBullet(0); break;
                    case 50: Game.player.switchBullet(1); break;
                    case 51: Game.player.switchBullet(2); break;
                    case 52: Game.player.switchBullet(3); break;
                    case 53: Game.player.switchBullet(4); break;
                    case 87: pressedKeys[0] = true; break;
                    case 65: pressedKeys[1] = true;break;
                    case 83: pressedKeys[2] = true; break;
                    case 68: pressedKeys[3] = true;break;
                    case 69: Game.player.interact(Game.player.currentTile); break;
                    case GLFW.GLFW_KEY_ESCAPE: if(Game.ticking) {
                        Game.ticking = false;
                        MenuManagar.switchMenu(MenuManagar.menuMap.get(MenuManagar.GAME_PAUSE_MENU));
                    } else {
                        Game.ticking = true;
                        MenuManagar.switchMenu(null);
                    }
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
