/*
 * @test
 * @summary Test jdk.internal.jimage.ImageStream.put(ImageStream) method
 * @run main ImageStreamWitupTest
 */

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ImageStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testPutCopiesBytesCorrectly();
        testPutWithEmptyStream();
    }

    private static void testPutCopiesBytesCorrectly() {
        byte[] data = new byte[] {1, 2, 3, 4, 5};
        ImageStream source = new ImageStream(data, ByteOrder.BIG_ENDIAN);
        // Position set to length of data to simulate written bytes
        source.buffer.position(data.length);

        ImageStream target = new ImageStream(10, ByteOrder.BIG_ENDIAN);
        target.put(source);

        // After put, target buffer position should be advanced by source position
        if (target.buffer.position() != data.length) {
            throw new AssertionError("Target buffer position incorrect after put");
        }

        // The bytes copied should match source bytes
        byte[] copied = new byte[data.length];
        int oldPos = target.buffer.position();
        target.buffer.position(0);
        target.buffer.get(copied);
        target.buffer.position(oldPos);

        for (int i = 0; i < data.length; i++) {
            if (copied[i] != data[i]) {
                throw new AssertionError("Byte at index " + i + " differs after put");
            }
        }
    }

    private static void testPutWithEmptyStream() {
        ImageStream emptySource = new ImageStream(new byte[0], ByteOrder.BIG_ENDIAN);
        // Position is zero, no bytes to copy
        emptySource.buffer.position(0);

        ImageStream target = new ImageStream(10, ByteOrder.BIG_ENDIAN);
        int initialPosition = target.buffer.position();
        target.put(emptySource);

        // Position should remain unchanged as nothing copied
        if (target.buffer.position() != initialPosition) {
            throw new AssertionError("Target buffer position changed after put with empty source");
        }
    }
}