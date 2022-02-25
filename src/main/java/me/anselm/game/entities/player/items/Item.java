package me.anselm.game.entities.player.items;

import me.anselm.graphics.game.Renderable;
import me.anselm.graphics.texture.Texture;
import me.anselm.utils.AssetStorage;
import me.anselm.utils.Position;
import org.joml.Vector3f;
import org.lwjgl.opengl.ARBTextureMirrorClampToEdge;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class Item{

    private String name;
    private int id;
    private Texture texture;

    public Item(String name, int id, Texture texture) {
        this.name = name;
        this.id = id;
        this.texture = texture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public static Item createInstanceFromItem(Class clazz) {
        Item currentBulletInstance = null;
        Constructor[] constructors = clazz.getConstructors();

        try {
            Constructor<?> cons = clazz.getConstructor(String.class, int.class);
            try {
                currentBulletInstance = (Item)cons.newInstance("basic", 0);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return currentBulletInstance;
    }
}
