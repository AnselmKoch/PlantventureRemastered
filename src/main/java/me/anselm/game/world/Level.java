package me.anselm.game.world;

import me.anselm.game.Game;
import me.anselm.game.entities.enemies.Zombie;
import me.anselm.game.entities.player.items.bullets.Bullet;
import me.anselm.game.physics.CollitionDetector;
import me.anselm.game.powerups.BasicPowerup;
import me.anselm.game.world.tiles.Tile;
import me.anselm.game.world.tiles.tile.DirtTile;
import me.anselm.game.world.tiles.tile.GrassTile;
import me.anselm.game.world.tiles.tile.PoisonTile;
import me.anselm.game.world.tiles.tile.PuddleTile;
import me.anselm.game.world.tiles.tile.StoneTile;
import me.anselm.game.world.tiles.tile.TreasureTile;
import me.anselm.graphics.Window;
import me.anselm.graphics.game.entity.EntityRenderer;
import me.anselm.graphics.game.entity.Healthbar;
import me.anselm.graphics.game.entity.HealthbarRenderer;
import me.anselm.graphics.game.hud.HUDRenderer;
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

    private boolean isDone;

    public Tile[][] tiles;
    public LootedIcon[][] icons;

    public static Zombie zombie;

    private Vector2i location;
    private double[][] simplexIndex;

    private List<Zombie> zombieArrayList;

    public Level(Vector2i location) {
        isDone = false;
        this.zombieArrayList = new ArrayList<>();
        location.x = new Random().nextInt(1000000);
        location.y = new Random().nextInt(1000000);
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

                simplexIndex[i % tilesY][j%tilesX] *= 100.0f;

            }
        }

        createTile();

        tiles[5][10].setPowerup(new BasicPowerup(tiles[5][10].getPosition()));

        WorldRenderer.getRenderMesh().addRenderable(tiles[5][10].getPowerup().getPowerupIcon());


         Random random = new Random();
         int enemyAmount = random.nextInt(7) + 3;
         for(int i = 0; i < 2; i++) {
             int x = random.nextInt(400);
             int y = random.nextInt(200);

             Zombie zombie = new Zombie(new Vector3f(x,y, 1.0f));
             zombieArrayList.add(zombie);
             EntityRenderer.getRenderMesh().addRenderable(zombie);
             HealthbarRenderer.getRenderMesh().addRenderable(zombie.getHealthbar().getBackGround());
             HealthbarRenderer.getRenderMesh().addRenderable(zombie.getHealthbar().getHealthGreen());
             HealthbarRenderer.getRenderMesh().addRenderable(zombie.getHealthbar().getHealthRed());
         }
    }

    public void tick() {
        logger.info("Running tick...");

        for(int i = 0; i < zombieArrayList.size(); i++) {
            Zombie entity = zombieArrayList.get(i);
            entity.tick();

            if(CollitionDetector.colides(Game.player, entity)) {
                Game.player.onDamage((int)entity.getDamage());
            }

            for(int j = 0; j < Game.player.getBullets().size(); j++) {
                Bullet bullet = Game.player.getBullets().get(j);

                if(CollitionDetector.colides(bullet, entity)) {
                    entity.onDamage((int)bullet.getDamage());

                    if(!bullet.isPiercing()) {
                        Game.player.getBullets().remove(j);
                        EntityRenderer.getRenderMesh().removeRenderable(bullet);
                    }
                    checkLevelDone();
                }
            }
        }
    }

    private void createTile() {

        int currX = 0, currY = 0;
        for (int i = 0; i < tilesY; i++) {
            for (int j = 0; j < tilesX; j++) {
                double simplex = simplexIndex[i][j];

                Vector3f currPos = new Vector3f(currX, currY, 0.0f);

                if(simplex < -95.0f && simplex > -100.0f) {
                    tiles[i][j] = new PoisonTile(currPos, tileWidth, tilesHeight, 1.0f, Position.BOTTOMLEFT);
                }
                else if(simplex < -70.0f && simplex > -95.0f) {
                    tiles[i][j] = new StoneTile(currPos, tileWidth, tilesHeight, 1.0f, Position.BOTTOMLEFT);
                }else if(simplex < 40.0f && simplex > -70.0f) {
                    tiles[i][j] = new GrassTile(currPos, tileWidth, tilesHeight, 1.0f, Position.BOTTOMLEFT);
                }else if(simplex > 40.0f && simplex < 90.0f) {
                    tiles[i][j] = new DirtTile(currPos, tileWidth, tilesHeight, 1.0f, Position.BOTTOMLEFT);
                }else if(simplex > 90.0f && simplex <= 95f) {
                    tiles[i][j] = new PuddleTile(currPos, tileWidth, tilesHeight, 1.0f, Position.BOTTOMLEFT);
                }else if(simplex > 95f){
                    tiles[i][j] = new TreasureTile(currPos, tileWidth, tilesHeight, 1.0f, Position.BOTTOMLEFT);
                }

                WorldRenderer.getRenderMesh().addRenderable(tiles[i][j]);


                if (!(tiles[i][j] instanceof GrassTile)) {

                    icons[i][j] = new LootedIcon(new Vector3f(currX, currY, 3.0f), 5.0f, 5.0f, 1.0f,
                            AssetStorage.getTexture("shovel"), Position.BOTTOMLEFT);

                    WorldRenderer.getRenderMesh().addRenderable(icons[i][j]);
                }
                currX += tileWidth;
            }
            currY += tilesHeight;
            currX = 0;
        }
    }

    private void checkLevelDone() {
        if(zombieArrayList.size() == 0) {
            HUDRenderer.setShowPointingArrows(true);
            HUDRenderer.toggleArrows();
            this.isDone = true;
        }
    }

    public List<Zombie> getEnemyArrayList() {
        return this.zombieArrayList;
    }

    public boolean isDone() {
        return isDone;
    }
}
