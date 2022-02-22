package me.anselm.utils.buffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BufferUtils {

    private BufferUtils() {
    }

    public static ByteBuffer createByteBuffer(byte[] array) {
        ByteBuffer result = org.lwjgl.BufferUtils.createByteBuffer(array.length).order(ByteOrder.nativeOrder());
        result.put(array).flip();
        return result;
    }

    public static FloatBuffer createFloatBuffer(float[] array) {
        FloatBuffer result = org.lwjgl.BufferUtils.createByteBuffer(array.length << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
        result.put(array).flip();
        return result;
    }

    public static IntBuffer createIntBuffer(int[] array) {
        IntBuffer result = org.lwjgl.BufferUtils.createByteBuffer(array.length << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
        result.put(array).flip();
        return result;
    }

    public static void destroyFloatBuffer() {
    }
}
