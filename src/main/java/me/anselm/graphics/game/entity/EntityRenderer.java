package me.anselm.graphics.game.entity;

import me.anselm.game.Game;
import me.anselm.game.entities.enemies.Enemy;
import me.anselm.game.entities.player.Player;
import me.anselm.game.world.Level;
import me.anselm.graphics.shaders.mesh.RenderMesh;
import me.anselm.graphics.shaders.Shader;
import me.anselm.utils.LoggerUtils;
import org.slf4j.Logger;

public class EntityRenderer {

    private final static Logger logger = LoggerUtils.getLogger(EntityRenderer.class);

    private static Player player = Game.player;
    private static Enemy enemy = Level.enemy;
    private static RenderMesh renderMesh;

    public static void render() {
        logger.info("Rendering entities...");

        Level.enemy.onRender();
        renderMesh.render();
    }

    public static void init() {
        logger.info("Initializing EntityRenderer...");
        renderMesh = new RenderMesh(Shader.PLAYER, 100);
    }

    public static RenderMesh getRenderMesh() {
        return renderMesh;
    }
}
