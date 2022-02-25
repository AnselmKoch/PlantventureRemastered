package me.anselm.game.world;

import me.anselm.game.Game;
import me.anselm.game.entities.enemies.Enemy;
import me.anselm.game.world.tiles.Tile;
import me.anselm.game.world.tiles.tile.DirtTile;
import me.anselm.game.world.tiles.tile.GrassTile;
import me.anselm.graphics.Window;
import me.anselm.graphics.game.entity.EntityRenderer;
import me.anselm.graphics.game.world.WorldRenderer;
import me.anselm.graphics.texture.Texture;
import me.anselm.utils.AssetStorage;
import me.anselm.utils.LoggerUtils;
import me.anselm.utils.Position;
import me.anselm.utils.SimplexNoise;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.slf4j.Logger;

public class Level {
    private static final Logger logger = LoggerUtils.getLogger(Level.class);

    public static final int tilesX = 20;
    public static final int tilesY = 10;
    public static final int tileWidth = Window.WORLDWITH / tilesX;
    public static final int tilesHeight = Window.WORLDHEIGHT / tilesY;

    public Tile[][] tiles;
    public static Enemy enemy;

    private Vector2i location;
    private double[][] simplexIndex;

    public Level(Vector2i location) {
        this.location = location;
        tiles = new Tile[tilesY][tilesX];
        simplexIndex = new double[tilesY][tilesX];

        double simplexStepSizeX = 1.0d / (double)tilesX;
        double simplexStepSizeY = 1.0d / (double) tilesY;


        int currX = 0, currY = 0;
        for (int i = 0; i < tilesY; i++) {
            for (int j = 0; j < tilesX; j++) {
                simplexIndex[i][j] = SimplexNoise.noise(j * simplexStepSizeX, i * simplexStepSizeY, Game.seed);
                if(simplexIndex[i][j] < -0.50f) {
                    tiles[i][j] = new DirtTile(new Vector3f(currX, currY, 0.0f), tileWidth, tilesHeight, 1.0f, Position.BOTTOMLEFT);
                }else{
                    tiles[i][j] = new GrassTile(new Vector3f(currX, currY, 0.0f), tileWidth, tilesHeight, 1.0f, Position.BOTTOMLEFT);
                }
                WorldRenderer.getRenderMesh().addRenderable(tiles[i][j]);
                currX += tileWidth;
            }
            currY += tilesHeight;
            logger.info(currY + "--");
            currX = 0;
        }

        enemy = new Enemy(new Vector3f(300.0f,100.0f, 0.0f), 20f,20f,1.0f,AssetStorage.getTexture("zombie"), Position.CENTER);
         EntityRenderer.getRenderMesh().addRenderable(enemy);
    }

    public void tick() {
        logger.info("Running tick...");
    }
}
