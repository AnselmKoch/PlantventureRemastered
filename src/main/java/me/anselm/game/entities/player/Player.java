package me.anselm.game.entities.player;

import me.anselm.game.Game;
import me.anselm.game.entities.Entity;
import me.anselm.game.entities.Shield;
import me.anselm.game.entities.player.inventory.Inventory;
import me.anselm.game.entities.player.inventory.ItemStack;
import me.anselm.game.entities.player.items.BasicBulletItem;
import me.anselm.game.entities.player.items.PoisonBulletItem;
import me.anselm.game.entities.player.items.bullets.BasicBullet;
import me.anselm.game.entities.player.items.bullets.Bullet;
import me.anselm.game.entities.player.items.Item;
import me.anselm.game.entities.player.items.StoneBulletItem;
import me.anselm.game.entities.player.items.WaterBulletItem;
import me.anselm.game.entities.player.items.bullets.PoisonBullet;
import me.anselm.game.entities.player.items.bullets.StoneBullet;
import me.anselm.game.entities.player.items.bullets.WaterBullet;
import me.anselm.game.powerups.Powerup;
import me.anselm.game.world.Interactable;
import me.anselm.game.world.levels.Level;
import me.anselm.game.world.levels.LevelManager;
import me.anselm.game.world.tiles.Tile;
import me.anselm.graphics.Window;
import me.anselm.graphics.game.entity.EntityRenderer;
import me.anselm.graphics.game.hud.HUDRenderer;
import me.anselm.graphics.game.world.WorldRenderer;
import me.anselm.graphics.texture.Texture;
import me.anselm.menu.MenuManagar;
import me.anselm.utils.AssetStorage;
import me.anselm.utils.LoggerUtils;
import me.anselm.utils.Position;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.slf4j.Logger;

import java.util.ArrayList;

public class Player extends Entity {
    private static final Logger logger = LoggerUtils.getLogger(Player.class);
    public Tile currentTile;
    private ArrayList<Bullet> bullets;
    private Inventory inventory;
    private Class currentBullet;
    private int currentTileY, currentTileX;
    private Item currentBulletInstance;

    public Player(Vector3f position) {
        super(position, 15.0f, 15.0f, 1.0f, AssetStorage.getTexture("player"), Position.CENTER, true, MAX_HEALTH);
        bullets = new ArrayList<>();
        this.inventory = new Inventory();
        this.setHealth(MAX_HEALTH);

        this.setCooldown(0.1f);
        this.setDamage(1.0f);
        this.setShotspeed(3.0f);
        this.setSpeed(3.0f);

        this.getShield();

        this.setAnimationDelay(50);


        this.setTextures(new Texture[]{
                AssetStorage.getTexture("player0"),   AssetStorage.getTexture("player1"),  AssetStorage.getTexture("player2"),
        });
    }

    @Override
    public void onRender() {

    }

    public void calculateCurrentTile() {
        Level level = Game.levelManager.getCurrentLevel();
        int tilesX = Level.tilesX;
        int tilesY = Level.tilesY;
        int xInd = Window.WORLDWITH / tilesX;
        int yInd = Window.WORLDHEIGHT / tilesY;

        int currX = (int) this.getPosition().x / xInd;
        int currY = (int) this.getPosition().y / yInd;

        this.currentTileX = currX;
        this.currentTileY = currY;

        try {
            this.currentTile = level.tiles[currY][currX];
        } catch (Exception e) {
            return;
        }
    }

    public void switchBullet(int key) {
        ItemStack itemStack = this.inventory.getItemStack(key);

        if (itemStack == null) {
            return;
        }

        Class clazz = itemStack.getItemClass();
        this.currentBullet = clazz;
        this.currentBulletInstance = Item.createInstanceFromItem(currentBullet);
        this.inventory.setCurrentItemstack(key);
    }

    public void shoot(Vector2f momentum) {
        if (currentBullet == null) {
            return;
        }

        if (!this.getInventory().containsInInventory(currentBullet)) {
            return;
        }

        if (this.getCurrentCooldown() >= 0.0f) {
            return;
        }


        Bullet bullet = null;

        if (currentBulletInstance instanceof BasicBulletItem) {
            bullet = new BasicBullet(1, 1.5f,  new Vector3f().set(this.getPosition()));

        } else if (currentBulletInstance instanceof StoneBulletItem) {

            bullet = new StoneBullet(3, 1.5f,  new Vector3f().set(this.getPosition()));

        } else if (currentBulletInstance instanceof WaterBulletItem) {

            bullet = new WaterBullet(1, 1.5f,  new Vector3f().set(this.getPosition()));

        } else if (currentBulletInstance instanceof PoisonBulletItem) {
            bullet = new PoisonBullet(1, 1.5f, new Vector3f().set(this.getPosition()));
        }

        bullet.setMomentumTotal(momentum.mul(bullet.getShotspeed()).mul(this.getShotspeed()));
        bullets.add(bullet);
        EntityRenderer.getRenderMesh().addRenderable(bullet);
        this.getInventory().removeItem(currentBullet);
        this.setCurrentCooldown(this.getCooldown());
    }

    public void pickupPowerup(Powerup powerup) {
        this.setDamage(this.getDamage() + powerup.getDamage());
        this.setHealth(this.getHealth() + powerup.getHealth());
        this.setSpeed(this.getSpeed() + powerup.getSpeed());
        this.setShotspeed(this.getShotspeed() + powerup.getShotSpeed());
        HUDRenderer.drawItemPickUp(300, powerup);
        HUDRenderer.updatePlayerHearts();
    }

    public void interact(Interactable interactable) {
        if (interactable == null) {
            return;
        }

        if (interactable.isInteractable()) {
            WorldRenderer.getRenderMesh().removeRenderable(Game.levelManager.getCurrentLevel().icons[currentTileY][currentTileX]);
        }

        interactable.onInteract(this);
    }

    @Override
    public void move(Vector2f momentum) {
        this.addToPosition(momentum.mul(this.getSpeed()), 0.0f);

        if(momentum.x < 0) {
            this.rotateY(160);
        }else{
            this.rotateY(0);
        }

        Level level = Game.levelManager.getCurrentLevel();
        if (this.getPosition().x > Window.WORLDWITH) {

            if (level.isDone()) {
                Game.switchLevel();
            } else {
                this.getPosition().sub(new Vector3f(momentum.x, momentum.y, 0.0f).mul(this.getSpeed()));
            }
        }

        if (this.getPosition().x < 0) {
            if (level.isDone()) {
                Game.switchLevel();

            } else {
                this.getPosition().sub(new Vector3f(momentum.x, momentum.y, 0.0f).mul(this.getSpeed()));
            }
        }

        if (this.getPosition().y > Window.WORLDHEIGHT) {

            if (level.isDone()) {
                Game.switchLevel();

            } else {
                this.getPosition().sub(new Vector3f(momentum.x, momentum.y, 0.0f).mul(this.getSpeed()));
            }
        }
        if (this.getPosition().y < 0) {

            if (level.isDone()) {
             Game.switchLevel();

            } else {
                this.getPosition().sub(new Vector3f(momentum.x, momentum.y, 0.0f).mul(this.getSpeed()));
            }
        }
        this.getShield().setPosition(new Vector3f().set(this.getPosition()));
        EntityRenderer.getRenderMesh().changeRenderable(this.getShield());
        EntityRenderer.getRenderMesh().changeRenderable(this);
    }

    public void resetPosition() {
        this.setPosition(new Vector3f(200, 100, 1.0f));
        EntityRenderer.getRenderMesh().changeRenderable(this);
    }

    @Override
    public void tick() {


       // this.setTexture(this.getTextures()[animationIndex / 30]);

      //  if(animationIndex >= 60) {
      //      animationIndex = 0;
      //  }
        this.doDamageColor();

    }

    @Override
    public void die() {
        Game.ticking = false;
        MenuManagar.switchMenu(MenuManagar.menuMap.get(MenuManagar.DIED_MENU));
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Class getCurrentBullet() {
        return this.currentBullet;
    }

    public void setCurrentBullet(Class currentBullet) {
        this.currentBullet = currentBullet;
        this.currentBulletInstance = Item.createInstanceFromItem(currentBullet);
    }

}
