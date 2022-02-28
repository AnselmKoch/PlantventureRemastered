package me.anselm.game.world;

import me.anselm.game.Game;
import me.anselm.game.entities.enemies.Zombie;
import me.anselm.game.entities.player.items.Bullet;
import me.anselm.game.physics.CollitionDetector;
import me.anselm.game.world.tiles.Tile;
import me.anselm.game.world.tiles.tile.DirtTile;
import me.anselm.game.world.tiles.tile.GrassTile;
import me.anselm.graphics.Window;
import me.anselm.graphics.game.entity.EntityRenderer;
import me.anselm.graphics.game.world.LootedIcon;
import me.anselm.graphics.game.world.WorldRenderer;
import me.anselm.utils.AssetStorage;
import me.anselm.utils.LoggerUtils;
import me.anselm.utils.Position;
import me.anselm.utils.SimplexNoise;
import org.joml.Random;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private static final Logger logger = LoggerUtils.getLogger(Level.class);

    public static final int tilesX = 20;
    public static final int tilesY = 10;
    public static final int tileWidth = Window.WORLDWITH / tilesX;
    public static final int tilesHeight = Window.WORLDHEIGHT / tilesY;

    public Tile[][] tiles;
    public LootedIcon[][] icons;
    public static Zombie zombie;

    private Vector2i location;
    private double[][] simplexIndex;

    private List<Zombie> zombieArrayList;

    public Level(Vector2i location) {
        this.zombieArrayList = new ArrayList<>();
        this.location = location;
        if(this.location.x < 0) {
            this.location.x = Integer.MAX_VALUE - this.location.x;
        }
        if(this.location.y < 0) {
            this.location.y = Integer.MAX_VALUE - this.location.y;
        }

        tiles = new Tile[tilesY][tilesX];
        simplexIndex = new double[tilesY][tilesX];
        icons = new LootedIcon[tilesY][tilesX];

        Game.player.resetPosition();

        double simplexStepSizeX = 1.0d / (double)tilesX;
        double simplexStepSizeY = 1.0d / (double) tilesY;

        for(int i = 0; i < tilesY ; i++) {
            for(int j = 0; j < tilesX; j++) {
                simplexIndex[i % tilesY][j % tilesX] = SimplexNoise.noise(((double)j + location.x) * simplexStepSizeX ,
                        ((double)i + location.y )* simplexStepSizeY, Game.seed);

            }
        }

        int currX = 0, currY = 0;
        for (int i = 0; i < tilesY; i++) {
            for (int j = 0; j < tilesX; j++) {
                if(simplexIndex[i][j] < -0.5f) {
                    tiles[i][j] = new DirtTile(new Vector3f(currX, currY, 0.0f), tileWidth, tilesHeight, 1.0f, Position.BOTTOMLEFT);
                }else{
                    tiles[i][j] = new GrassTile(new Vector3f(currX, currY, 0.0f), tileWidth, tilesHeight, 1.0f, Position.BOTTOMLEFT);
                }
                icons[i][j] = new LootedIcon(new Vector3f(currX, currY, 3.0f), 5.0f,5.0f,1.0f,
                        AssetStorage.getTexture("shovel"), Position.BOTTOMLEFT);
                WorldRenderer.getRenderMesh().addRenderable(tiles[i][j]);
                WorldRenderer.getRenderMesh().addRenderable(icons[i][j]);
                currX += tileWidth;
            }
            currY += tilesHeight;
            logger.info(currY + "--");
            currX = 0;
        }


         Random random = new Random();
         int enemyAmount = random.nextInt(7) + 3;
         for(int i = 0; i < 1; i++) {
             int x = random.nextInt(400);
             int y = random.nextInt(200);

             Zombie zombie = new Zombie(new Vector3f(x,y, 1.0f));
             zombieArrayList.add(zombie);
             EntityRenderer.getRenderMesh().addRenderable(zombie);
         }
    }

    public void tick() {
        logger.info("Running tick...");

        for(int i = 0; i < zombieArrayList.size(); i++) {
            Zombie entity = zombieArrayList.get(i);
            entity.tick();

            if(CollitionDetector.colides(Game.player, entity)) {
                Game.player.onDamage(1);
            }

            for(int j = 0; j < Game.player.getBullets().size(); j++) {
                Bullet bullet = Game.player.getBullets().get(j);

                if(CollitionDetector.colides(bullet, entity)) {
                    entity.onDamage(1);
                    Game.player.getBullets().remove(j);
                    EntityRenderer.getRenderMesh().removeRenderable(bullet);
                }
            }
        }
    }

    public List<Zombie> getEnemyArrayList() {
        return this.zombieArrayList;
    }
}
