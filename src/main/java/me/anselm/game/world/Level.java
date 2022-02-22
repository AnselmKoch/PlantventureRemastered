package me.anselm.game.world;

import me.anselm.game.entities.enemies.Enemy;
import me.anselm.game.world.tiles.Tile;
import me.anselm.graphics.Window;
import me.anselm.graphics.game.entity.EntityRenderer;
import me.anselm.graphics.game.world.WorldRenderer;
import me.anselm.graphics.texture.Texture;
import me.anselm.utils.LoggerUtils;
import me.anselm.utils.Position;
import org.joml.Vector3f;
import org.slf4j.Logger;

public class Level {
    private static final Logger logger = LoggerUtils.getLogger(Level.class);

    public static final int tilesX = 20;
    public static final int tilesY = 10;
    public static final int tileWidth = Window.WORLDWITH / tilesX;
    public static final int tilesHeight = Window.WORLDHEIGHT / tilesY;

    public Tile[][] tiles;
    public static Texture grassTex = new Texture("grass");
    public static Texture enemyTex = new Texture("enemy/zombie");
    public static Enemy enemy;

    public Level() {
        tiles = new Tile[tilesY][tilesX];
        grassTex = new Texture("grass");

        int currX = 0, currY = 0;
        for (int i = 0; i < tilesY; i++) {
            for (int j = 0; j < tilesX; j++) {
                tiles[i][j] = new Tile(new Vector3f(currX, currY, 0.0f), tileWidth, tilesHeight, 1.0f, grassTex, Position.BOTTOMLEFT);
                WorldRenderer.getRenderMesh().addRenderable(tiles[i][j]);
                currX += tileWidth;
            }
            currY += tilesHeight;
            logger.info(currY + "--");
            currX = 0;
        }

        enemy = new Enemy(new Vector3f(300.0f,100.0f, 0.0f), 20f,20f,1.0f,enemyTex, Position.CENTER);
         EntityRenderer.getRenderMesh().addRenderable(enemy);
    }

    public void tick() {
        logger.info("Running tick...");
    }
}
