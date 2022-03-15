package me.anselm.graphics.game;

import me.anselm.graphics.texture.Texture;
import me.anselm.utils.LoggerUtils;
import me.anselm.utils.Position;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.slf4j.Logger;

public abstract class Renderable {
    private static final Logger logger = LoggerUtils.getLogger(Renderable.class);

    private Vector3f position;
    public float width, height, size;
    private Vector4f color;
    private Texture texture;
    private Position orientation;
    private Vector3f center;
    private Vector2f[] texCoords;
    private Vector3f[] positions;

    public Renderable(Vector3f position, float width, float height, float size, Texture texture, Position orientation) {
        logger.info("Instantiating renderable with texture...");
        this.position = position;
        this.width = width;
        this.height = height;
        this.size = size;
        this.texture = texture;
        this.orientation = orientation;

        Vector2f texCord1 = new Vector2f(0,0);
        Vector2f texCord2 = new Vector2f(0,1);
        Vector2f texCord3 = new Vector2f(1,1);
        Vector2f texCord4 = new Vector2f(1,0);

        Vector2f[] texCords = new Vector2f[] {
                texCord1, texCord2, texCord3, texCord4
        };

        this.texCoords = texCords;

        Vector3f corner1 = null;
        Vector3f corner2 = null;
        Vector3f corner3 = null;
        Vector3f corner4 = null;

        if (orientation == Position.CENTER) {
            corner1 = new Vector3f(position.x - (width / 2), position.y + (height / 2), position.z);
            corner2 = new Vector3f(position.x - (width / 2), position.y - (height / 2), position.z);
            corner3 = new Vector3f(position.x + (width / 2), position.y - (height / 2), position.z);
            corner4 = new Vector3f(position.x + (width / 2), position.y + (height / 2), position.z);
            center = position;
        } else if (orientation == Position.TOPRIGHT) {
            corner1 = new Vector3f(position.x, position.y, position.z);
            corner2 = new Vector3f(position.x, position.y - height, position.z);
            corner3 = new Vector3f(position.x - width, position.y - height, position.z);
            corner4 = new Vector3f(position.x - width, position.y, position.z);
            center = new Vector3f(position.x - (width / 2), position.y - (height/2), position.z);
        } else if (orientation == Position.TOPLEFT) {
            corner1 = new Vector3f(position.x + width, position.y, position.z);
            corner2 = new Vector3f(position.x + width, position.y - height, position.z);
            corner3 = new Vector3f(position.x, position.y - height, position.z);
            corner4 = new Vector3f(position.x, position.y, position.z);
            center = new Vector3f(position.x + (width /2), position.y - (height / 2 ), position.z);
        } else if(orientation == Position.BOTTOMLEFT) {
            corner1 = new Vector3f(position.x + width, position.y + height, position.z);
            corner2 = new Vector3f(position.x + width, position.y, position.z);
            corner3 = new Vector3f(position.x, position.y, position.z);
            corner4 = new Vector3f(position.x, position.y + height, position.z);
            center = new Vector3f(position.x + (width / 2), position.y + (height / 2), position.z);
        } else if(orientation == Position.BOTTOMRIGHT) {
            corner1 = new Vector3f(position.x, position.y + height, position.z);
            corner2 = new Vector3f(position.x, position.y, position.z);
            corner3 = new Vector3f(position.x - width, position.y, position.z);
            corner4 = new Vector3f(position.x - width, position.y + height, position.z);
            center = new Vector3f(position.x - (width / 2), position.y + (width / 2), position.z);
        }

        Vector3f[] corners = new Vector3f[]{
                corner1,corner2,corner3,corner4
        };

        this.color = new Vector4f(1.0f,1.0f,1.0f,1.0f);

        this.positions = corners;
    }

    public Renderable(Vector3f position, Vector2f[] texCords, float width, float height, float size, Texture texture, Position orientation) {
        logger.info("Instantiating renderable with texture...");
        this.position = position;
        this.width = width;
        this.height = height;
        this.size = size;
        this.texture = texture;
        this.orientation = orientation;

        this.texCoords = texCords;

        Vector3f corner1 = null;
        Vector3f corner2 = null;
        Vector3f corner3 = null;
        Vector3f corner4 = null;

        if (orientation == Position.CENTER) {
            corner1 = new Vector3f(position.x - (width / 2), position.y + (height / 2), position.z);
            corner2 = new Vector3f(position.x - (width / 2), position.y - (height / 2), position.z);
            corner3 = new Vector3f(position.x + (width / 2), position.y - (height / 2), position.z);
            corner4 = new Vector3f(position.x + (width / 2), position.y + (height / 2), position.z);
            center = position;
        } else if (orientation == Position.TOPRIGHT) {
            corner1 = new Vector3f(position.x, position.y, position.z);
            corner2 = new Vector3f(position.x, position.y - height, position.z);
            corner3 = new Vector3f(position.x - width, position.y - height, position.z);
            corner4 = new Vector3f(position.x - width, position.y, position.z);
            center = new Vector3f(position.x - (width / 2), position.y - (height/2), position.z);
        } else if (orientation == Position.TOPLEFT) {
            corner1 = new Vector3f(position.x + width, position.y, position.z);
            corner2 = new Vector3f(position.x + width, position.y - height, position.z);
            corner3 = new Vector3f(position.x, position.y - height, position.z);
            corner4 = new Vector3f(position.x, position.y, position.z);
            center = new Vector3f(position.x + (width /2), position.y - (height / 2 ), position.z);
        } else if(orientation == Position.BOTTOMLEFT) {
            corner1 = new Vector3f(position.x + width, position.y + height, position.z);
            corner2 = new Vector3f(position.x + width, position.y, position.z);
            corner3 = new Vector3f(position.x, position.y, position.z);
            corner4 = new Vector3f(position.x, position.y + height, position.z);
            center = new Vector3f(position.x + (width / 2), position.y + (height / 2), position.z);
        } else if(orientation == Position.BOTTOMRIGHT) {
            corner1 = new Vector3f(position.x, position.y + height, position.z);
            corner2 = new Vector3f(position.x, position.y, position.z);
            corner3 = new Vector3f(position.x - width, position.y, position.z);
            corner4 = new Vector3f(position.x - width, position.y + height, position.z);
            center = new Vector3f(position.x - (width / 2), position.y + (width / 2), position.z);
        }

        Vector3f[] corners = new Vector3f[]{
                corner1,corner2,corner3,corner4
        };

        this.color = new Vector4f(1.0f,1.0f,1.0f,1.0f);

        this.positions = corners;
    }

    public Renderable(Vector3f position, float width, float height, float size, Position orientation, Vector4f color) {
        logger.info("Instantiating renderable without texture...");
        this.position = position;
        this.width = width;
        this.height = height;
        this.size = size;
        this.orientation = orientation;

        Vector2f texCord1 = new Vector2f(0, 0);
        Vector2f texCord2 = new Vector2f(0, 1);
        Vector2f texCord3 = new Vector2f(1, 1);
        Vector2f texCord4 = new Vector2f(1, 0);

        Vector2f[] texCords = new Vector2f[]{
                texCord1, texCord2, texCord3, texCord4
        };

        this.texCoords = texCords;

        Vector3f corner1 = null;
        Vector3f corner2 = null;
        Vector3f corner3 = null;
        Vector3f corner4 = null;

        if (orientation == Position.CENTER) {
            corner1 = new Vector3f(position.x - (width / 2), position.y + (height / 2), position.z);
            corner2 = new Vector3f(position.x - (width / 2), position.y - (height / 2), position.z);
            corner3 = new Vector3f(position.x + (width / 2), position.y - (height / 2), position.z);
            corner4 = new Vector3f(position.x + (width / 2), position.y + (height / 2), position.z);
            center = position;
        } else if (orientation == Position.TOPRIGHT) {
            corner1 = new Vector3f(position.x, position.y, position.z);
            corner2 = new Vector3f(position.x, position.y - height, position.z);
            corner3 = new Vector3f(position.x - width, position.y - height, position.z);
            corner4 = new Vector3f(position.x - width, position.y, position.z);
            center = new Vector3f(position.x - (width / 2), position.y - (height / 2), position.z);
        } else if (orientation == Position.TOPLEFT) {
            corner1 = new Vector3f(position.x + width, position.y, position.z);
            corner2 = new Vector3f(position.x + width, position.y - height, position.z);
            corner3 = new Vector3f(position.x, position.y - height, position.z);
            corner4 = new Vector3f(position.x, position.y, position.z);
            center = new Vector3f(position.x + (width / 2), position.y - (height / 2), position.z);
        } else if (orientation == Position.BOTTOMLEFT) {
            corner1 = new Vector3f(position.x + width, position.y + height, position.z);
            corner2 = new Vector3f(position.x + width, position.y, position.z);
            corner3 = new Vector3f(position.x, position.y, position.z);
            corner4 = new Vector3f(position.x, position.y + height, position.z);
            center = new Vector3f(position.x + (width / 2), position.y + (height / 2), position.z);
        } else if (orientation == Position.BOTTOMRIGHT) {
            corner1 = new Vector3f(position.x, position.y + height, position.z);
            corner2 = new Vector3f(position.x, position.y, position.z);
            corner3 = new Vector3f(position.x - width, position.y, position.z);
            corner4 = new Vector3f(position.x - width, position.y + height, position.z);
            center = new Vector3f(position.x - (width / 2), position.y + (width / 2), position.z);
        }

        Vector3f[] corners = new Vector3f[]{
                corner1,corner2,corner3,corner4
        };

        this.positions = corners;
        this.color = color;
    }



        public void updateVertices() {
        Vector3f corner1 = null;
        Vector3f corner2 = null;
        Vector3f corner3 = null;
        Vector3f corner4 = null;

        if (orientation == Position.CENTER) {
            corner1 = new Vector3f(position.x - (width / 2), position.y + (height / 2), 0.0f);
            corner2 = new Vector3f(position.x - (width / 2), position.y - (height / 2), 0.0f);
            corner3 = new Vector3f(position.x + (width / 2), position.y - (height / 2), 0.0f);
            corner4 = new Vector3f(position.x + (width / 2), position.y + (height / 2), 0.0f);
            position.z = 0.0f;
            center = position;
        } else if (orientation == Position.TOPRIGHT) {
            corner1 = new Vector3f(position.x, position.y, position.z);
            corner2 = new Vector3f(position.x, position.y - height, position.z);
            corner3 = new Vector3f(position.x - width, position.y - height, position.z);
            corner4 = new Vector3f(position.x - width, position.y, position.z);
            center = new Vector3f(position.x - (width / 2), position.y - (height/2), position.z);
        } else if (orientation == Position.TOPLEFT) {
            corner1 = new Vector3f(position.x + width, position.y, position.z);
            corner2 = new Vector3f(position.x + width, position.y - height, position.z);
            corner3 = new Vector3f(position.x, position.y - height, position.z);
            corner4 = new Vector3f(position.x, position.y, position.z);
            center = new Vector3f(position.x + (width /2), position.y - (height / 2 ), position.z);
        } else if(orientation == Position.BOTTOMLEFT) {
            corner1 = new Vector3f(position.x + width, position.y + height, position.z);
            corner2 = new Vector3f(position.x + width, position.y, position.z);
            corner3 = new Vector3f(position.x, position.y, position.z);
            corner4 = new Vector3f(position.x, position.y + height, position.z);
            center = new Vector3f(position.x + (width / 2), position.y + (height / 2), position.z);
        } else if(orientation == Position.BOTTOMRIGHT) {
            corner1 = new Vector3f(position.x, position.y + height, position.z);
            corner2 = new Vector3f(position.x, position.y, position.z);
            corner3 = new Vector3f(position.x - width, position.y, position.z);
            corner4 = new Vector3f(position.x - width, position.y + height, position.z);
            center = new Vector3f(position.x - (width / 2), position.y + (width / 2), position.z);
        }


            Quaternionf dest1 = new Quaternionf().identity();
            Quaternionf dest2 = new Quaternionf().identity();

            Vector3f[] corners = new Vector3f[]{
                corner1,corner2,corner3,corner4
        };



        this.positions = corners;

    }

    public void rotate270() {
        Vector2f texCord1 = new Vector2f(1,1);
        Vector2f texCord2 = new Vector2f(0,1);
        Vector2f texCord3 = new Vector2f(0,0);
        Vector2f texCord4 = new Vector2f(1,0);

        Vector2f[] texCords = new Vector2f[] {
                texCord1, texCord2, texCord3, texCord4
        };

        this.texCoords = texCords;
    }

    public void rotateStandart() {

        Vector2f texCord1 = new Vector2f(0,0);
        Vector2f texCord2 = new Vector2f(0,1);
        Vector2f texCord3 = new Vector2f(1,1);
        Vector2f texCord4 = new Vector2f(1,0);

        Vector2f[] texCords = new Vector2f[] {
                texCord1, texCord2, texCord3, texCord4
        };

        this.texCoords = texCords;
    }

    public void rotate180() {
        Vector2f texCord1 = new Vector2f(1,1);
        Vector2f texCord2 = new Vector2f(1,0);
        Vector2f texCord3 = new Vector2f(0,0);
        Vector2f texCord4 = new Vector2f(0,1);

        Vector2f[] texCords = new Vector2f[] {
                texCord1, texCord2, texCord3, texCord4
        };

        this.texCoords = texCords;
    }

    public void rotate90() {
        Vector2f texCord1 = new Vector2f(0,0);
        Vector2f texCord2 = new Vector2f(1,0);
        Vector2f texCord3 = new Vector2f(1,1);
        Vector2f texCord4 = new Vector2f(0,1);


        Vector2f[] texCords = new Vector2f[] {
                texCord1, texCord2, texCord3, texCord4
        };

        this.texCoords = texCords;
    }

    public void addToPosition(Vector2f toAdd, float rotation) {
        this.position.add(toAdd.x, toAdd.y, 0.0f);
        updateVertices();
    }

    public void rotateZ(float amount) {
        for(int i = 0; i < this.getPositions().length; i++) {
            Vector3f vector3f = this.getPositions()[i];

            Vector3f origin = new Vector3f().set(vector3f).sub(this.getCenter());

            origin.rotateZ(amount);

            this.getPositions()[i] = origin.add(this.getCenter());

        }
    }


    public void rotateX(float amount) {
        for(int i = 0; i < this.getPositions().length; i++) {
            Vector3f vector3f = new Vector3f(this.getPositions()[i]);

            Vector3f origin = new Vector3f().set(vector3f).sub(this.getCenter());

            origin.rotateX(amount);

            this.getPositions()[i] = origin.add(this.getCenter());


        }
    }

    public void rotateY(float amount) {
        for(int i = 0; i < this.getPositions().length; i++) {
            Vector3f vector3f = this.getPositions()[i];

            Vector3f origin = new Vector3f().set(vector3f).sub(this.getCenter());

            origin.rotateY(amount);

            this.getPositions()[i] = origin.add(this.getCenter());


        }
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
        updateVertices();
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Vector3f getCenter() {
        return this.center;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public Vector4f getColor() {
        return color;
    }

    public void setColor(Vector4f color) {
        this.color = color;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Vector2f[] getTextureCords() {
        return this.texCoords;
    }

    public Vector3f[] getPositions() {
        return positions;
    }
}
