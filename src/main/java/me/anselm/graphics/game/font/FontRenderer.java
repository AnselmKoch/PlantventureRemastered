package me.anselm.graphics.game.font;

import me.anselm.graphics.Window;
import me.anselm.graphics.shaders.Shader;
import me.anselm.utils.font.RenderChar;
import me.anselm.utils.font.TextFont;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class FontRenderer {

    private static FontMesh renderMesh;
    public static TextFont textFont;

    public static void render() {
        textFont.drawText("FPS:" + Window.fps, new Vector3f(0.0f,10.0f,0.0f), 10,10, new Vector4f(1.0f,1.0f,1.0f,1.0f));
        renderMesh.render();
        renderMesh.clear();

    }

    public static void init() {
        renderMesh = new FontMesh(Shader.STANDART, 500);
        textFont = new TextFont("arial");
    }

    public static void addText(RenderChar[] renderChars) {
            for (RenderChar renderChar : renderChars) {
                if (renderChar == null) {
                    continue;
                }
                try{
                renderMesh.addRenderable(renderChar);

                }catch (Exception e) {
                    renderMesh.clear();
                    renderMesh.addRenderable(renderChar);
                }
            }
        }

}
