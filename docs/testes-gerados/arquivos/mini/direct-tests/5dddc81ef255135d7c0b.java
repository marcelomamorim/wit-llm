/*
 * @test
 * @summary Test jdk.internal.jimage.ImageStream.put(ImageStream) method
 * @run main ImageStreamWitupTest
 */

import jdk.internal.jimage.ImageStream;
import java.nio.ByteOrder;

public class ImageStreamWitupTest {
    public static void main(String[] args) {
        testPutCopiesBytesCorrectly();
        testPutReturnsThis();
    }

    private static void testPutCopiesBytesCorrectly() {
        byte[] data = new byte[] {10, 20, 30, 40};
        ImageStream source = new ImageStream(data, ByteOrder.BIG_ENDIAN);
        // Move position to full length to simulate written bytes
        source.buffer.position(data.length);

        ImageStream target = new ImageStream(10, ByteOrder.BIG_ENDIAN);
        target.put(source);

        // Verify that target buffer contains the bytes from source
        byte[] targetArray = target.buffer.array();
        for (int i = 0; i < data.length; i++) {
            if (targetArray[i] != data[i]) {
                throw new RuntimeException("Byte at index " + i + " differs: expected " + data[i] + " but got " + targetArray[i]);
            }
        }

        // Verify target position advanced correctly
        if (target.buffer.position() != data.length) {
            throw new RuntimeException("Target buffer position expected to be " + data.length + " but was " + target.buffer.position());
        }
    }

    private static void testPutReturnsThis() {
        ImageStream source = new ImageStream(new byte[] {1, 2, 3}, ByteOrder.nativeOrder());
        source.buffer.position(3);
        ImageStream target = new ImageStream(10);
        ImageStream returned = target.put(source);
        if (returned != target) {
            throw new RuntimeException("put(ImageStream) should return this instance");
        }
    }
}
