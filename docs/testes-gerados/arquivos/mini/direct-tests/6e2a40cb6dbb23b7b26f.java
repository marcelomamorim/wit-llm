/*
 * @test
 * @summary Test jdk.internal.jimage.ImageStream.ensure(int) method behavior
 * @run main ImageStreamWitupTest
 */

import java.nio.ByteOrder;
import jdk.internal.jimage.ImageStream;

public class ImageStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testEnsureWithNegativeNeeds();
        testEnsureWithNeedsLessThanRemaining();
        testEnsureWithNeedsGreaterThanRemainingAndLessThanArrayLength();
        testEnsureWithNeedsGreaterThanArrayLength();
    }

    private static void testEnsureWithNegativeNeeds() {
        ImageStream stream = new ImageStream();
        try {
            stream.ensure(-1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for negative needs");
        } catch (IndexOutOfBoundsException e) {
            if (!e.getMessage().contains("Bad value: -1")) {
                throw new RuntimeException("Exception message mismatch: " + e.getMessage());
            }
        }
    }

    private static void testEnsureWithNeedsLessThanRemaining() {
        ImageStream stream = new ImageStream(16);
        // Initially buffer has capacity 16 and position 0, so remaining = 16
        // ensure with needs less than remaining should not change buffer capacity
        int initialCapacity = stream.buffer.capacity();
        stream.ensure(8);
        if (stream.buffer.capacity() != initialCapacity) {
            throw new RuntimeException("Buffer capacity changed unexpectedly when needs < remaining");
        }
    }

    private static void testEnsureWithNeedsGreaterThanRemainingAndLessThanArrayLength() {
        // Create stream with byte array of length 16
        byte[] bytes = new byte[16];
        ImageStream stream = new ImageStream(bytes);
        // Move position to 8
        for (int i = 0; i < 8; i++) {
            stream.put((byte) i);
        }
        int oldCapacity = stream.buffer.capacity();
        int oldPosition = stream.buffer.position();
        // needs = 12 > remaining (16 - 8 = 8), but needs <= bytes.length (16)
        stream.ensure(12);
        int newCapacity = stream.buffer.capacity();
        if (newCapacity != oldCapacity << 1) {
            throw new RuntimeException("Buffer capacity not doubled as expected when needs <= bytes.length");
        }
        if (stream.buffer.position() != oldPosition) {
            throw new RuntimeException("Buffer position changed unexpectedly after ensure");
        }
    }

    private static void testEnsureWithNeedsGreaterThanArrayLength() {
        // Create stream with byte array of length 16
        byte[] bytes = new byte[16];
        ImageStream stream = new ImageStream(bytes);
        // Move position to 8
        for (int i = 0; i < 8; i++) {
            stream.put((byte) i);
        }
        int oldCapacity = stream.buffer.capacity();
        int oldPosition = stream.buffer.position();
        // needs = 20 > bytes.length (16)
        stream.ensure(20);
        int newCapacity = stream.buffer.capacity();
        if (newCapacity != oldPosition + 20) {
            throw new RuntimeException("Buffer capacity not set to position + needs as expected when needs > bytes.length");
        }
        if (stream.buffer.position() != oldPosition) {
            throw new RuntimeException("Buffer position changed unexpectedly after ensure");
        }
    }
}
