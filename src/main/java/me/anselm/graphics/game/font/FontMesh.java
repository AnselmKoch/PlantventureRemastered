package me.anselm.graphics.game.font;

import me.anselm.graphics.mesh.RenderMesh;
import me.anselm.graphics.shaders.Shader;

public class FontMesh extends RenderMesh {
    public FontMesh(Shader shader) {
        super(shader);
    }

    public void flush() {
        for(int i = 0; i < this.vertices.length; i++) {
            vertices[i] = 0.0f;
        }
        this.renderables.clear();
        this.amount = 0;
    }
}
