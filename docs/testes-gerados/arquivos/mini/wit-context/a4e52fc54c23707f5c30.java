/*
 * @test
 * @summary Test BasicImageReader.getAttributes(int) for valid and invalid offsets
 * @run main BasicImageReaderWitupTest
 */

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.BasicImageReader;

public class BasicImageReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testGetAttributesValidOffset();
        testGetAttributesInvalidNegativeOffset();
        testGetAttributesInvalidTooLargeOffset();
        System.out.println("PASS");
    }

    private static void testGetAttributesValidOffset() throws Exception {
        // Prepare a BasicImageReader with a dummy Path and ByteOrder
        // Since BasicImageReader is abstracted and protected constructors,
        // we use the protected constructor with Path and ByteOrder.
        // We create a minimal valid ByteBuffer for locations with capacity > 0

        Path dummyPath = Paths.get("/dev/null");
        BasicImageReader reader = new BasicImageReader(dummyPath, ByteOrder.BIG_ENDIAN) {
            // Override locations to a ByteBuffer with capacity 10
            {
                java.lang.reflect.Field f = BasicImageReader.class.getDeclaredField("locations");
                f.setAccessible(true);
                f.set(this, ByteBuffer.allocate(10));
            }

            // Override slice method to return a slice of the buffer
            @Override
            protected ByteBuffer slice(ByteBuffer buffer, int offset, int length) {
                ByteBuffer dup = buffer.duplicate();
                dup.position(offset);
                dup.limit(offset + length);
                return dup.slice();
            }
        };

        // Call getAttributes with offset 0 (valid)
        long[] attrs = reader.getAttributes(0);
        if (attrs == null) {
            throw new AssertionError("getAttributes returned null for valid offset");
        }
    }

    private static void testGetAttributesInvalidNegativeOffset() throws Exception {
        Path dummyPath = Paths.get("/dev/null");
        BasicImageReader reader = new BasicImageReader(dummyPath, ByteOrder.BIG_ENDIAN) {
            {
                java.lang.reflect.Field f = BasicImageReader.class.getDeclaredField("locations");
                f.setAccessible(true);
                f.set(this, ByteBuffer.allocate(10));
            }

            @Override
            protected ByteBuffer slice(ByteBuffer buffer, int offset, int length) {
                ByteBuffer dup = buffer.duplicate();
                dup.position(offset);
                dup.limit(offset + length);
                return dup.slice();
            }
        };

        try {
            reader.getAttributes(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative offset");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testGetAttributesInvalidTooLargeOffset() throws Exception {
        Path dummyPath = Paths.get("/dev/null");
        BasicImageReader reader = new BasicImageReader(dummyPath, ByteOrder.BIG_ENDIAN) {
            {
                java.lang.reflect.Field f = BasicImageReader.class.getDeclaredField("locations");
                f.setAccessible(true);
                f.set(this, ByteBuffer.allocate(10));
            }

            @Override
            protected ByteBuffer slice(ByteBuffer buffer, int offset, int length) {
                ByteBuffer dup = buffer.duplicate();
                dup.position(offset);
                dup.limit(offset + length);
                return dup.slice();
            }
        };

        try {
            reader.getAttributes(10); // offset == limit
            throw new AssertionError("Expected IndexOutOfBoundsException for offset >= limit");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
