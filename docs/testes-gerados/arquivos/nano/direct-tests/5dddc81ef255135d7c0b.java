/*
 * @test
 * @summary Test the put method of ImageStream for correct data copying and chaining.
 * @run main ImageStreamWitupTest
 */

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import jdk.internal.jimage.ImageStream;

public class ImageStreamWitupTest {

    public static void main(String[] args) {
        testPutCopiesBufferData();
        testPutReturnsThis();
    }

    private static void testPutCopiesBufferData() {
        byte[] data = {1, 2, 3, 4, 5};
        ByteBuffer buffer = ByteBuffer.wrap(data);
        buffer.order(ByteOrder.BIG_ENDIAN);
        ImageStream sourceStream = new ImageStream(buffer);
        ImageStream targetStream = new ImageStream();
        targetStream.put(sourceStream);
        byte[] targetBytes = new byte[data.length];
        targetStream.buffer.position(0);
        targetStream.buffer.get(targetBytes);
        for (int i = 0; i < data.length; i++) {
            if (targetBytes[i] != data[i]) {
                throw new AssertionError("Data mismatch at index " + i);
            }
        }
    }

    private static void testPutReturnsThis() {
        ImageStream stream1 = new ImageStream();
        ImageStream stream2 = new ImageStream();
        ImageStream result = stream1.put(stream2);
        if (result != stream1) {
            throw new AssertionError("put() does not return the original object for chaining");
        }
    }
}
