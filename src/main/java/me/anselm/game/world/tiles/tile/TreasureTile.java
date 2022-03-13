package me.anselm.game.world.tiles.tile;

import me.anselm.game.Game;
import me.anselm.game.entities.player.Player;
import me.anselm.game.powerups.Powerup;
import me.anselm.game.world.tiles.Tile;
import me.anselm.graphics.game.hud.HUDRenderer;
import me.anselm.graphics.menu.MenuRenderer;
import me.anselm.graphics.texture.Texture;
import me.anselm.menu.MenuManagar;
import me.anselm.menu.menus.Menu;
import me.anselm.menu.menus.PickupMenu;
import me.anselm.utils.AssetStorage;
import me.anselm.utils.Position;
import org.joml.Vector3f;

public class TreasureTile extends Tile {

    public TreasureTile(Vector3f position, float width, float height, float size, Position center) {
        super(position, width, height, size, AssetStorage.getTexture("treasure"), center);
    }

    @Override
    public void setInteractable(boolean interactable) {
        this.setLooted(interactable);
    }

    @Override
    public boolean isInteractable() {
        return this.isLooted();
    }

    @Override
    public void onInteract(Player player) {

        if(!this.isInteractable()) {
            HUDRenderer.drawInformation(100, "already looted");
            return;
        }


        Powerup powerup = Powerup.getRandomPowerup(new Vector3f(200.0f, 100.0f, 0.0f));
        Game.ticking = false;
        PickupMenu pickupMenu = (PickupMenu) MenuManagar.menuMap.get(MenuManagar.PICK_MENU);
        pickupMenu.setCrtPowerup(powerup);
        MenuManagar.switchMenu(pickupMenu);
        MenuRenderer.renderMesh.addRenderable(powerup.getPowerupIcon());

        this.setInteractable(false);
    }
}
