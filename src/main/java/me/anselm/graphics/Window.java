package me.anselm.graphics;

import me.anselm.game.Input;
import me.anselm.graphics.game.entity.EntityRenderer;
import me.anselm.graphics.game.font.FontRenderer;
import me.anselm.graphics.game.hud.HUDRenderer;
import me.anselm.graphics.game.world.WorldRenderer;
import me.anselm.graphics.shaders.Shader;
import me.anselm.utils.ResizeCallback;
import org.joml.Matrix4f;
import org.slf4j.Logger;
import me.anselm.utils.LoggerUtils;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import sun.font.TextRecord;
import sun.java2d.pipe.TextRenderer;

import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_NORMAL;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwMaximizeWindow;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearDepth;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGetString;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private static final Logger logger = LoggerUtils.getLogger(Window.class);
    public static long window;
    private static GLFWVidMode glfwGetVideoMode;
    public static int WIDTH = 1060, HEIGHT = 800, TARGETWITDTH = 1920, TARGETHEIGHT = 1020;
    public static float aspectRatio;
    public static float targetAspectRatio;
    public static float minX = -100.0f * aspectRatio;
    public static final int WORLDWITH = 400, WORLDHEIGHT = 200;
    public static final int orthoMinX = 0, orthoMinY = 0, orthoMaxY = 200, orthoMaxX = 400;

    public static Matrix4f perspective;
    public static Matrix4f view = new Matrix4f().identity();

    public static int fps;

    public static void render() {

         perspective = new Matrix4f().ortho(orthoMinX,
                orthoMaxX , orthoMinY,
               orthoMaxY, 0.0f, -100.0f);

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

        WorldRenderer.render();
        EntityRenderer.render();
        HUDRenderer.render();
        FontRenderer.render();

        glfwSwapBuffers(window);
        glfwPollEvents();

    }

    public static void init() {
        logger.info("Initializing window...");
        if(!glfwInit()) {
            logger.info("Could not initialize GLFW");
        }
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        window = glfwCreateWindow(1920,1080, "Plantventure v0.01", NULL, NULL);
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);

        if (window == NULL) {
            System.err.println("Could not create window...");
            return;
        }

        glfwGetVideoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        TARGETHEIGHT = glfwGetVideoMode.height();
        TARGETWITDTH = glfwGetVideoMode.width();
        aspectRatio = (float)TARGETWITDTH/ (float)TARGETHEIGHT;
        targetAspectRatio = aspectRatio;

        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        glfwSwapInterval(0);
        glClearDepth(1.0f);
        //glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL);
        glActiveTexture(GL_TEXTURE1);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        logger.info("OpenGL: " + glGetString(GL_VERSION) + "...");
        glfwShowWindow(window);

        glfwSetKeyCallback(window, Input.getKeyCallback());
        glfwSetMouseButtonCallback(window, Input.getClickCallBack());

        glfwSetWindowSizeCallback(window, ResizeCallback::resizeCallback);
        ResizeCallback.resizeCallback(window, TARGETWITDTH, TARGETHEIGHT);
    }


}
