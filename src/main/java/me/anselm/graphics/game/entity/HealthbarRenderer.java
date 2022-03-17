package me.anselm.graphics.game.entity;

import me.anselm.graphics.shaders.Shader;
import me.anselm.graphics.mesh.RenderMesh;

public class HealthbarRenderer {

    private static RenderMesh renderMesh;

    public static void render() {

        renderMesh.render();
    }

    public static void init() {
        renderMesh = new RenderMesh(Shader.HEALTH, 100);
    }

    public static RenderMesh getRenderMesh() {
        return renderMesh;
    }
}
