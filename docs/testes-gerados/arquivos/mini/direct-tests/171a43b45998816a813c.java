/*
 * @test
 * @summary Test constructor ImageStream(ByteBuffer buffer) for null and valid input
 * @run main ImageStreamWitupTest
 */

import java.nio.ByteBuffer;
import java.util.Objects;

public class ImageStreamWitupTest {
    public static void main(String[] args) {
        testConstructorWithValidBuffer();
        testConstructorWithNullBuffer();
    }

    private static void testConstructorWithValidBuffer() {
        ByteBuffer buf = ByteBuffer.allocate(10);
        jdk.internal.jimage.ImageStream stream = new jdk.internal.jimage.ImageStream(buf);
        // Verify that the stream is not null and has remaining capacity
        if (!stream.hasByte()) {
            throw new RuntimeException("Expected stream to have remaining bytes after construction");
        }
    }

    private static void testConstructorWithNullBuffer() {
        try {
            new jdk.internal.jimage.ImageStream(null);
            throw new RuntimeException("Expected NullPointerException for null buffer");
        } catch (NullPointerException npe) {
            // Expected exception
        }
    }
}
