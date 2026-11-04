/* @test    @summary Tests for jdk.internal.jimage.ImageStream.ensure(int needs) method    @run main ImageStreamWitupTest */

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ImageStreamWitupTest {

    public static void main(String[] args) {
        testEnsureWithNegativeNeeds();
        testEnsureWithLargeNeeds();
        testEnsureWithSmallNeeds();
    }

    private static void testEnsureWithNegativeNeeds() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        jdk.internal.jimage.ImageStream stream = new jdk.internal.jimage.ImageStream(buffer);
        try {
            stream.ensure(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative needs");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    private static void testEnsureWithLargeNeeds() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        jdk.internal.jimage.ImageStream stream = new jdk.internal.jimage.ImageStream(buffer);
        int initialCapacity = buffer.capacity();
        int needs = initialCapacity + 20;
        stream.ensure(needs);
        // Verify that buffer capacity is at least needs
        ByteBuffer buf = getBufferFromStream(stream);
        if (buf.capacity() < needs) {
            throw new AssertionError("Buffer capacity " + buf.capacity() + " is less than needs " + needs);
        }
    }

    private static void testEnsureWithSmallNeeds() {
        ByteBuffer buffer = ByteBuffer.allocate(20);
        jdk.internal.jimage.ImageStream stream = new jdk.internal.jimage.ImageStream(buffer);
        int initialCapacity = buffer.capacity();
        int needs = 10;
        stream.ensure(needs);
        // Buffer should remain unchanged
        ByteBuffer buf = getBufferFromStream(stream);
        if (buf.capacity() != initialCapacity) {
            throw new AssertionError("Buffer capacity changed unexpectedly");
        }
    }

    private static ByteBuffer getBufferFromStream(jdk.internal.jimage.ImageStream stream) {
        try {
            java.lang.reflect.Field bufferField = jdk.internal.jimage.ImageStream.class.getDeclaredField("buffer");
            bufferField.setAccessible(true);
            return (ByteBuffer) bufferField.get(stream);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Reflection failed to access buffer field", e);
        }
    }
}
