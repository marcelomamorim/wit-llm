/*
 * @test
 * @summary Test ImageStream.ensure(int) method for boundary and exception cases
 * @run main ImageStreamWitupTest
 */

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import jdk.internal.jimage.ImageStream;

public class ImageStreamWitupTest {
    public static void main(String[] args) {
        testEnsureNegativeThrows();
        testEnsureNoResizeWhenEnoughRemaining();
        testEnsureResizeWhenNotEnoughRemaining();
    }

    private static void testEnsureNegativeThrows() {
        ImageStream stream = new ImageStream();
        try {
            stream.ensure(-1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for negative needs");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
            if (!e.getMessage().contains("Bad value")) {
                throw new RuntimeException("Exception message unexpected: " + e.getMessage());
            }
        }
    }

    private static void testEnsureNoResizeWhenEnoughRemaining() {
        // Create ImageStream with default size 1024
        ImageStream stream = new ImageStream(1024, ByteOrder.nativeOrder());
        int initialCapacity = stream.buffer.capacity();
        int initialPosition = stream.buffer.position();

        // ensure with needs less or equal to remaining should not resize
        int needs = 512;
        stream.ensure(needs);

        if (stream.buffer.capacity() != initialCapacity) {
            throw new RuntimeException("Buffer capacity changed unexpectedly when enough remaining");
        }
        if (stream.buffer.position() != initialPosition) {
            throw new RuntimeException("Buffer position changed unexpectedly when enough remaining");
        }
    }

    private static void testEnsureResizeWhenNotEnoughRemaining() {
        // Create ImageStream with small size
        int initialSize = 8;
        ImageStream stream = new ImageStream(initialSize, ByteOrder.nativeOrder());

        // Fill buffer partially
        for (int i = 0; i < 4; i++) {
            stream.buffer.put((byte) i);
        }

        int positionBefore = stream.buffer.position();
        int capacityBefore = stream.buffer.capacity();

        // Request ensure with needs greater than remaining
        int needs = 10; // greater than remaining (capacity - position = 8 - 4 = 4)
        stream.ensure(needs);

        int capacityAfter = stream.buffer.capacity();
        int positionAfter = stream.buffer.position();

        if (capacityAfter <= capacityBefore) {
            throw new RuntimeException("Buffer capacity did not increase after ensure with large needs");
        }

        if (positionAfter != positionBefore) {
            throw new RuntimeException("Buffer position changed unexpectedly after ensure");
        }

        // Check that previously put bytes are preserved
        stream.buffer.position(0);
        for (int i = 0; i < positionBefore; i++) {
            int val = stream.buffer.get() & 0xFF;
            if (val != i) {
                throw new RuntimeException("Buffer content corrupted after ensure at index " + i + ": expected " + i + " but got " + val);
            }
        }
    }
}