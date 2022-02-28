package me.anselm.game.entities.player;

import me.anselm.game.Game;
import me.anselm.game.entities.Entity;
import me.anselm.game.entities.player.inventory.Inventory;
import me.anselm.game.entities.player.inventory.ItemStack;
import me.anselm.game.entities.player.items.BasicBullet;
import me.anselm.game.entities.player.items.Bullet;
import me.anselm.game.entities.player.items.Item;
import me.anselm.game.world.Interactable;
import me.anselm.game.world.Level;
import me.anselm.game.world.LevelManager;
import me.anselm.game.world.hints.PlayerHeart;
import me.anselm.game.world.tiles.Tile;
import me.anselm.graphics.Window;
import me.anselm.graphics.game.entity.EntityRenderer;
import me.anselm.graphics.game.hud.HUDRenderer;
import me.anselm.graphics.game.world.WorldRenderer;
import me.anselm.graphics.texture.Texture;
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
    private float currentCooldown = 0.0f;

    private static final float cooldownPerTick = 1.0f / 60.0f;

    public Player(Vector3f position) {
        super(position, 15.0f, 15gi.0f, 1.0f, AssetStorage.getTexture("player"), Position.CENTER, true);
        bullets = new ArrayList<>();
        this.inventory = new Inventory();
        this.setHealth(MAX_HEALTH);

        this.setCooldown(0.1f);
        this.setDamage(1.0f);
        this.setShotspeed(3.0f);
        this.setSpeed(3.0f);
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

        int currX = (int)this.getPosition().x / xInd;
        int currY = (int) this.getPosition().y / yInd;

        this.currentTileX = currX;
        this.currentTileY = currY;

        try {
            this.currentTile = level.tiles[currY][currX];
        }catch (Exception e) {
            return;
        }
    }

    public void switchBullet(int key) {
        ItemStack itemStack = this.inventory.getItemStack(key);

        if(itemStack == null) {
            return;
        }

        Class clazz = itemStack.getItemClass();
        this.currentBullet = clazz;
        this.currentBulletInstance = Item.createInstanceFromItem(currentBullet);
        this.inventory.setCurrentItemstack(key);
    }

    public void shoot(Vector2f momentum) {
        if(currentBullet == null) {
            return;
        }

        if(!this.getInventory().containsInInventory(currentBullet)) {
            return;
        }

        if(this.currentCooldown >= 0.0f) {
            return;
        }



        Bullet bullet = new Bullet(new Vector3f().set(this.getPosition()), 5.0f,5.0f,1.0f,currentBulletInstance.getTexture(),Position.CENTER, false);
        bullet.setMomentumTotal(momentum.mul(this.getShotspeed()));
        bullets.add(bullet);
        EntityRenderer.getRenderMesh().addRenderable(bullet);
        this.getInventory().removeItem(currentBullet);
        this.currentCooldown = this.getCooldown();
    }

    public void interact(Interactable interactable) {
        if(interactable == null) {
            return;
        }

        if(interactable.isInteractable()) {
            WorldRenderer.getRenderMesh().removeRenderable(Game.levelManager.getCurrentLevel().icons[currentTileY][currentTileX]);
        }

        interactable.onInteract(this);
    }

    @Override
    public void move(Vector2f momentum) {
        this.addToPosition(momentum.mul(this.getSpeed()));

        if(this.getPosition().x > Window.WORLDWITH) {
            Game.levelManager.switchLevel(LevelManager.levelIndex.add(Level.tilesX,0));
        }
        if(this.getPosition().x < 0) {
            Game.levelManager.switchLevel(LevelManager.levelIndex.add(-Level.tilesX,0));
        }

        if(this.getPosition().y > Window.WORLDHEIGHT) {
            Game.levelManager.switchLevel(LevelManager.levelIndex.add(0,Level.tilesY));
        }
        if(this.getPosition().y < 0) {
            Game.levelManager.switchLevel(LevelManager.levelIndex.add(0,-Level.tilesY));
        }
     }

     public void resetPosition() {
        this.setPosition(new Vector3f(200,100,1.0f));
        EntityRenderer.getRenderMesh().changeRenderable(this);
     }

    @Override
    public void tick() {

        this.doDamageColor();

        if (this.getCooldown() == 0.0f) {
            return;
        }

        if (this.getCooldown() >= 0.0f) {
            this.currentCooldown -= cooldownPerTick;
        }

        EntityRenderer.getRenderMesh().changeRenderable(this);

        if(!this.isInvincible()) {
            return;
        }
        this.setCrtInvincTime(this.getCrtInvincTime() + 1);

        if(this.getCrtInvincTime() >= this.getInvincTime()) {
            this.setInvincible(false);
            this.setCrtInvincTime(0);
        }
    }

    @Override
    public void die() {

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
        this.currentBullet =currentBullet;
        this.currentBulletInstance = Item.createInstanceFromItem(currentBullet);
    }

}
