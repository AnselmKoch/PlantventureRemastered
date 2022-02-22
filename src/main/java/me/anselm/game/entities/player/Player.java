package me.anselm.game.entities.player;

import me.anselm.game.Game;
import me.anselm.game.entities.Entity;
import me.anselm.game.entities.player.inventory.Inventory;
import me.anselm.game.entities.player.items.BasicItem;
import me.anselm.game.entities.player.items.Bullet;
import me.anselm.game.world.Level;
import me.anselm.game.world.tiles.Tile;
import me.anselm.graphics.Window;
import me.anselm.graphics.game.entity.EntityRenderer;
import me.anselm.graphics.texture.Texture;
import me.anselm.utils.LoggerUtils;
import me.anselm.utils.Position;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.slf4j.Logger;

import java.util.ArrayList;

public class Player extends Entity {
    private static final Logger logger = LoggerUtils.getLogger(Player.class);

    public Tile currentTile;

    private float shotSpeed;

    private ArrayList<Bullet> bullets;
    private Inventory inventory;

    public Player(Vector3f position, float width, float height, float size, Texture texture, Position center) {
        super(position, width, height, size, texture, center);
        shotSpeed = 1.0f;
        bullets = new ArrayList<>();
        this.inventory = new Inventory();
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

    public void shoot(Vector2f momentum) {
        Bullet bullet = new Bullet(new Vector3f().set(this.getPosition()), 5.0f,5.0f,1.0f, Bullet.bulletTex,Position.CENTER);
        bullet.setMomentum(momentum.mul(shotSpeed));
        bullets.add(bullet);
        this.getInventory().removeItem(BasicItem.class);
        EntityRenderer.getRenderMesh().addRenderable(bullet);
    }

    @Override
    public void move(Vector2f momentum) {
        this.addToPosition(momentum);
        EntityRenderer.getRenderMesh().changeRenderable(this);
    }

    @Override
    public void tick() {
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
