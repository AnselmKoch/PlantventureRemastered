package me.anselm.graphics.game.world;

import me.anselm.game.Game;
import me.anselm.game.world.Level;
import me.anselm.game.world.tiles.Tile;
import me.anselm.graphics.shaders.mesh.RenderMesh;
import me.anselm.graphics.shaders.Shader;
import me.anselm.utils.LoggerUtils;
import org.joml.Matrix4f;
import org.slf4j.Logger;

import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClipPlane;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glIndexPointer;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.glActiveTexture;


public class WorldRenderer {
    private static final Logger logger = LoggerUtils.getLogger(WorldRenderer.class);

    private static Matrix4f perspective;
    private static RenderMesh renderMesh;

    private static Tile tile;
    public static void render() {
        if (Game.levelManager == null) {
            return;
        }

        logger.info("Rendering world...");

        Shader.TILE.setUniformBool("drawText", true);


        renderMesh.render();

       Shader.TILE.setUniformBool("drawText", false);

        if(Game.player.currentTile != null) {
            Tile tile = Game.player.currentTile;
            glLineWidth(3.0f);
            glBegin(GL_LINE_STRIP);
            glVertex3f(tile.getPosition().x, tile.getPosition().y, 2.0f);
            glVertex3f((tile.getPosition().x), (tile.getPosition().y + tile.height), 2.0f);
            glVertex3f((tile.getPosition().x + tile.width), (tile.getPosition().y + tile.height), 2.0f);
            glVertex3f((tile.getPosition().x + tile.width), (tile.getPosition().y), 2.0f);
            glVertex3f(tile.getPosition().x, tile.getPosition().y, 2.0f);
            glEnd();
        }
        Shader.TILE.disable();

    }

    public static void init() {
        logger.info("Initializing...");
        renderMesh = new RenderMesh(Shader.TILE, 500);
    }

    public static RenderMesh getRenderMesh() {
        return renderMesh;
    }
    public Matrix4f getPerspective() {
        return perspective;
    }

    public void setPerspective(Matrix4f perspective) {
        this.perspective = perspective;
    }
}
