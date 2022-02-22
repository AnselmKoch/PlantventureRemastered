package me.anselm.graphics;

import me.anselm.graphics.shaders.Shader;
import me.anselm.utils.LoggerUtils;
import me.anselm.utils.buffer.BufferUtils;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.slf4j.Logger;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glCopyTexImage1D;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_COPY;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glBufferSubData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Vao {
    private static final Logger logger = LoggerUtils.getLogger(Vao.class);

    private final int vaoId, vboId;
    private int indeceId;
    private int[] indeces;
    private Vector3f[] vertices;
    private Vector2f[] texCoords;

    public FloatBuffer vaoInfosBuffer;
    public IntBuffer indeceInfosBuffer;

    public Vao (Vector3f[] vertices, Vector2f[] texCords, int[] indeces) {
        logger.info("Creating vao...");

        this.indeces = indeces;
        this.texCoords = texCords;
        this.vertices = vertices;
        this.vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        float[] vaoInfos = new float[vertices.length * 3 + texCords.length * 2];

        int index = 0;
        for(int i = 0; i < 4; i++) {
            vaoInfos[index++] = vertices[i].x;
            vaoInfos[index++] = vertices[i].y;
            vaoInfos[index++] = vertices[i].z;
            vaoInfos[index++] = texCords[i].x;
            vaoInfos[index++] = texCords[i].y;
        }

        indeceInfosBuffer = org.lwjgl.BufferUtils.createIntBuffer(indeces.length);
        vaoInfosBuffer = org.lwjgl.BufferUtils.createFloatBuffer(vaoInfos.length);

        for(int i : indeces) {
            indeceInfosBuffer.put(i);
        }
        indeceInfosBuffer.flip();

        for(float fl : vaoInfos) {
            vaoInfosBuffer.put(fl);
        }
        vaoInfosBuffer.flip();

        indeceId = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indeceId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indeceInfosBuffer, GL_DYNAMIC_DRAW);


        vboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, vaoInfosBuffer, GL_DYNAMIC_DRAW);

        glVertexAttribPointer(Shader.VERTEX_ATTRIB, 3, GL_FLOAT, false,   5 * Float.BYTES, 0);
        glEnableVertexAttribArray(Shader.VERTEX_ATTRIB);


        glVertexAttribPointer(Shader.TCOORD_ATTRIB, 2, GL_FLOAT, false, 5 * Float.BYTES, 3 * Float.BYTES);
        glEnableVertexAttribArray(Shader.TCOORD_ATTRIB);

        glBindVertexArray(0);
    }

    public void updateBuffers(Vector3f[] vertices) {
        float[] vaoInfos = new float[vertices.length * 3 + texCoords.length * 2];

        int index = 0;
        for(int i = 0; i < 4; i++) {
            vaoInfos[index++] = vertices[i].x;
            vaoInfos[index++] = vertices[i].y;
            vaoInfos[index++] = vertices[i].z;
            vaoInfos[index++] = texCoords[i].x;
            vaoInfos[index++] = texCoords[i].y;
        }

        vaoInfosBuffer.clear();
        for(float fl : vaoInfos) {
            vaoInfosBuffer.put(fl);
        }
        vaoInfosBuffer.flip();

        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, vaoInfosBuffer, GL_DYNAMIC_DRAW);
    }

    public void bind() {
        glBindVertexArray(vaoId);
    }

    public void unbind() {
        glBindVertexArray(0);
    }

    private void draw() {
        glDrawElements(GL_TRIANGLES, indeces.length, GL_UNSIGNED_INT, 0);
    }

    public void render() {
        bind();
        draw();
        unbind();
    }

    public int getIndeces() {
        return indeceId;
    }

    public int getVaoId() {
        return vaoId;
    }

    public int getVboId() {
        return vboId;
    }
}
