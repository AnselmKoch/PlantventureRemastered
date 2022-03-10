package me.anselm.graphics.game.hud;

import me.anselm.graphics.game.items.ItemIcon;

public class InformationRenderable{

    private ItemIcon itemIcon;
    private String text;

    public static int counter = 0;

    private int maxFrames;
    private int currentFrame;
    private float transparency;

    public InformationRenderable(String text, int maxFrames) {
        this.text = text;
        this.maxFrames = maxFrames;
        this.transparency = 0.0f;
    }

    public InformationRenderable(ItemIcon itemIcon, int maxFrames) {
        this.itemIcon = itemIcon;
        this.text = "+" + counter;
        this.maxFrames = maxFrames;
        this.currentFrame = 0;
        this.transparency = 0.0f;
    }

    public float getTransparency() {
        return this.transparency;
    }

    public void setTransparency(float transparency) {
        this.transparency = transparency;
    }

    public ItemIcon getItemIcon() {
        return itemIcon;
    }

    public void setItemIcon(ItemIcon itemIcon) {
        this.itemIcon = itemIcon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getMaxFrames() {
        return maxFrames;
    }

    public void setMaxFrames(int maxFrames) {
        this.maxFrames = maxFrames;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.transparency = (float) this.maxFrames / (float) this.currentFrame;
        this.currentFrame = currentFrame;
    }
}
