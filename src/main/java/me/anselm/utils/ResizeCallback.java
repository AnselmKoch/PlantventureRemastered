package me.anselm.utils;

import me.anselm.graphics.Window;
import me.anselm.graphics.shaders.Shader;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11.glViewport;

public class ResizeCallback {

    public static void resizeCallback(long glfwWindow, int screenWidth, int screenHeight) {
        calculateViewPort(screenWidth, screenHeight);
    }

    public static void calculateViewPort(int screenWidth, int screenHeight) {
        Window.WIDTH = screenWidth;
        Window.HEIGHT = screenHeight;

        // Figure out the largest area that fits this target aspect ratio
        int aspectWidth = screenWidth;
        int aspectHeight = (int)((float)aspectWidth / Window.targetAspectRatio);
        if (aspectHeight > screenHeight) {
            // It doesn't fit our height, we must switch to pillarbox
            aspectHeight = screenHeight;
            aspectWidth = (int)((float)aspectHeight * Window.targetAspectRatio);
        }

        // Center rectangle
        int vpX = (int)(((float)screenWidth / 2f) - ((float)aspectWidth / 2f));
        int vpY = (int)(((float)screenHeight / 2f) - ((float)aspectHeight / 2f));

        glViewport(vpX, vpY, aspectWidth, aspectHeight);
    }
}
