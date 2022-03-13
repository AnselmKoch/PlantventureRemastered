package me.anselm.game.world.levels;

import me.anselm.game.Game;
import me.anselm.game.entities.Entity;
import me.anselm.game.entities.enemies.Igel;
import me.anselm.game.entities.enemies.Zombie;
import me.anselm.game.entities.player.items.bullets.Bullet;
import me.anselm.game.physics.CollitionDetector;
import me.anselm.game.world.tiles.Tile;
import me.anselm.game.world.tiles.tile.DirtTile;
import me.anselm.game.world.tiles.tile.GrassTile;
import me.anselm.game.world.tiles.tile.PoisonTile;
import me.anselm.game.world.tiles.tile.PuddleTile;
import me.anselm.game.world.tiles.tile.StoneTile;
import me.anselm.game.world.tiles.tile.TreasureTile;
import me.anselm.graphics.Window;
import me.anselm.graphics.game.entity.EntityRenderer;
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
    private float test;
    private double[][] simplexIndex;

    private List<Entity> zombieArrayList;
    private List<Entity> projectileList;

    public Level(Vector2i location) {
        isDone = false;
        this.zombieArrayList = new ArrayList<>();
        this.projectileList = new ArrayList<>();
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



         Random random = new Random();
         int enemyAmount = random.nextInt(7) + 3;

         for(int i = 0; i < enemyAmount; i++) {
          /*
             Class clazz = LevelManager.possibleEnemies.get(new Random().nextInt(LevelManager.possibleEnemies.size()));
             spawnEntity(clazz);
            */

          }

        spawnEntity(Igel.class);


    }

    public void tick() {
        logger.info("Running tick...");

        for(int i = 0; i < zombieArrayList.size(); i++) {
            Entity entity = zombieArrayList.get(i);
            entity.processTick();

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

        for(int i = 0; i < projectileList.size(); i++) {
            Entity entity = projectileList.get(i);
            entity.tick();
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

    public Entity spawnEntity(Class clazz, Vector3f pos) {
        Entity entity = Entity.createInstance(clazz,pos);

        zombieArrayList.add(entity);
        EntityRenderer.getRenderMesh().addRenderable(entity);
        HealthbarRenderer.getRenderMesh().addRenderable(entity.getHealthbar().getBackGround());
        HealthbarRenderer.getRenderMesh().addRenderable(entity.getHealthbar().getHealthGreen());
        HealthbarRenderer.getRenderMesh().addRenderable(entity.getHealthbar().getHealthRed());


        return entity;
    }

    public Entity spawnEntity(Class clazz) {

        Entity entity = Entity.createInstance(clazz,calucateRandomLocation(Game.player.getPosition()));

        zombieArrayList.add(entity);
        EntityRenderer.getRenderMesh().addRenderable(entity);
        HealthbarRenderer.getRenderMesh().addRenderable(entity.getHealthbar().getBackGround());
        HealthbarRenderer.getRenderMesh().addRenderable(entity.getHealthbar().getHealthGreen());
        HealthbarRenderer.getRenderMesh().addRenderable(entity.getHealthbar().getHealthRed());


        return entity;
    }


    public Vector3f calucateRandomLocation(Vector3f playerPos) {
        int x;
        int y;

        Random random = new Random();
        x = random.nextInt(400);
        y = random.nextInt(200);

        Vector3f distanceVector = new Vector3f(x,y,0.0f);
        while(distanceVector.distance(playerPos) < 200) {
            distanceVector = new Vector3f(random.nextInt(400), random.nextInt(200), 0.0f);
        }

        return distanceVector;
    }



    public List<Entity> getEnemyArrayList() {
        return this.zombieArrayList;
    }

    public boolean isDone() {
        return isDone;
    }

    public List<Entity> getProjectileList() {
        return projectileList;
    }
}
