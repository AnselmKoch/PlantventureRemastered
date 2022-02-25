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
import me.anselm.game.world.tiles.Tile;
import me.anselm.graphics.Window;
import me.anselm.graphics.game.entity.EntityRenderer;
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
    private Item currentBulletInstance;

    private float currentCooldown = 0.0f;

    private static final float cooldownPerTick = 1.0f / 60.0f;

    public Player(Vector3f position, float width, float height, float size, Texture texture, Position center) {
        super(position, width, height, size, texture, center);
        bullets = new ArrayList<>();
        this.inventory = new Inventory();

        this.setCooldown(0.1f);
        this.setDamage(1.0f);
        this.setShotspeed(3.0f);
        this.setSpeed(3.0f);
        currentBullet = BasicBullet.class;
        currentBulletInstance = Item.createInstanceFromItem(currentBullet);
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

        try {
            this.currentTile = level.tiles[currY][currX];
        }catch (Exception e) {
            return;
        }
    }

    public void switchBullet(int key) {
        ItemStack itemStack = this.inventory.getItemStacks()[key];

        if(itemStack == null) {
            return;
        }

        Class clazz = itemStack.getItemClass();
        this.currentBullet = clazz;
        this.currentBulletInstance = Item.createInstanceFromItem(currentBullet);
        this.inventory.setCurrentItemstack(key);
    }

    public void shoot(Vector2f momentum) {
        if(! this.getInventory().containsInInventory(currentBullet)) {
            return;
        }

        if(this.currentCooldown >= 0.0f) {
            return;
        }



        Bullet bullet = new Bullet(new Vector3f().set(this.getPosition()), 5.0f,5.0f,1.0f,currentBulletInstance.getTexture(),Position.CENTER);
        bullet.setMomentum(momentum.mul(this.getShotspeed()));
        bullets.add(bullet);
        EntityRenderer.getRenderMesh().addRenderable(bullet);
        this.getInventory().removeItem(currentBullet);
        this.currentCooldown = this.getCooldown();
    }

    public void interact(Interactable interactable) {
        interactable.onInteract(this);
    }

    @Override
    public void move(Vector2f momentum) {
        this.addToPosition(momentum.mul(this.getSpeed()));
        EntityRenderer.getRenderMesh().changeRenderable(this);
    }

    @Override
    public void tick() {
        if(this.getCooldown() == 0.0f) {
            return;
        }

        if(this.getCooldown() >= 0.0f) {
            this.currentCooldown -= cooldownPerTick;
        }
    }

    @Override
    public void onDamage() {

    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
