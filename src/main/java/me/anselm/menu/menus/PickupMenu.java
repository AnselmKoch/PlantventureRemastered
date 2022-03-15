package me.anselm.menu.menus;

import me.anselm.Plantventure;
import me.anselm.game.Game;
import me.anselm.game.powerups.Powerup;
import me.anselm.graphics.game.hud.HUDRenderer;
import me.anselm.graphics.menu.MenuRenderer;
import me.anselm.menu.MenuManagar;
import me.anselm.menu.buttons.Button;
import me.anselm.menu.buttons.Clickable;
import me.anselm.utils.AssetStorage;
import me.anselm.utils.Position;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class PickupMenu extends Menu{

    private Powerup crtPowerup;

    public PickupMenu() {
        super(new Vector3f(0.0f,0.0f,0.0f), 400.0f, 200.0f, 1.0f, Position.BOTTOMLEFT,
                new Vector4f(0.0f,0.0f,0.0f,0.5f), "Found an item!");

        setUpPickupButton();
        setupIgnoreButton();
    }

    private void setUpPickupButton() {
        Button button = new Button("Pickup", new Vector3f(150.0f,50.0f,1.0f),
                50.0f,20.0f,1.0f, AssetStorage.getTexture("button"), Position.CENTER, this);
        button.setClickable(new Clickable() {
            @Override
            public void click(Button button){
                Game.player.pickupPowerup(crtPowerup);
                HUDRenderer.drawInformation(100, "Picked up Item!");
                Game.ticking = true;
                MenuManagar.switchMenu(null);
            }

            @Override
            public void hover(Button button) {
                button.setColor(new Vector4f(0.7f,0.7f,0.7f,1.0f));
                MenuRenderer.renderMesh.changeRenderable(button);
            }

            @Override
            public void unhover(Button button) {
                button.setColor(new Vector4f(1.0f,1.0f,1.0f,1.0f));
                MenuRenderer.renderMesh.changeRenderable(button);
            }
        });
        addButton(button);
    }

    private void setupIgnoreButton() {
        Button button = new Button("Ignore", new Vector3f(250.0f,50.0f,1.0f),
                50.0f,20.0f,1.0f, AssetStorage.getTexture("button"), Position.CENTER, this);
        button.setClickable(new Clickable() {
            @Override
            public void click(Button button){
                Game.ticking = true;
                MenuManagar.switchMenu(null);
            }

            @Override
            public void hover(Button button) {
                button.setColor(new Vector4f(0.7f,0.7f,0.7f,1.0f));
                MenuRenderer.renderMesh.changeRenderable(button);
            }

            @Override
            public void unhover(Button button) {
                button.setColor(new Vector4f(1.0f,1.0f,1.0f,1.0f));
                MenuRenderer.renderMesh.changeRenderable(button);
            }
        });
        addButton(button);
    }

    @Override
    public void init() {

    }

    public Powerup getCrtPowerup() {
        return crtPowerup;
    }

    public void setCrtPowerup(Powerup crtPowerup) {
        this.crtPowerup = crtPowerup;
    }
}
