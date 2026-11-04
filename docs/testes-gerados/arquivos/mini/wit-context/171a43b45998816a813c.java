/*
 * @test
 * @summary Test constructor ImageStream(ByteBuffer) for null and non-null inputs
 * @run main ImageStreamWitupTest
 */

import java.nio.ByteBuffer;
import java.util.Objects;

public class ImageStreamWitupTest {
    public static void main(String[] args) {
        testConstructorWithNonNullBuffer();
        testConstructorWithNullBuffer();
    }

    private static void testConstructorWithNonNullBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        jdk.internal.jimage.ImageStream stream = new jdk.internal.jimage.ImageStream(buffer);
        if (stream == null) {
            throw new AssertionError("ImageStream instance is null");
        }
    }

    private static void testConstructorWithNullBuffer() {
        try {
            new jdk.internal.jimage.ImageStream(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
