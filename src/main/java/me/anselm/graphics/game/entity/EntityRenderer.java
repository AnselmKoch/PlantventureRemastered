package me.anselm.graphics.game.entity;

import me.anselm.game.Game;
import me.anselm.game.entities.enemies.Zombie;
import me.anselm.game.entities.player.Player;
import me.anselm.game.world.levels.Level;
import me.anselm.graphics.shaders.mesh.RenderMesh;
import me.anselm.graphics.shaders.Shader;
import me.anselm.utils.LoggerUtils;
import org.slf4j.Logger;

public class EntityRenderer {

    private final static Logger logger = LoggerUtils.getLogger(EntityRenderer.class);

    private static Player player = Game.player;
    private static Zombie zombie = Level.zombie;
    private static RenderMesh renderMesh;

    public static void render() {
        logger.info("Rendering entities...");

        renderMesh.render();
    }

    public static void init() {
        renderMesh = new RenderMesh(Shader.PLAYER, 100);
    }

    public static RenderMesh getRenderMesh() {
        return renderMesh;
    }
}
