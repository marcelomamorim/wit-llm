/*
 * @test
 * @summary Tests for jdk.internal.jimage.BasicImageReader.getAttributes(int)
 * @run main BasicImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.ByteOrder;

public class BasicImageReaderWitupTest {

    public static void main(String[] args) throws IOException {
        testGetAttributesWithValidOffset();
        testGetAttributesWithNegativeOffset();
        testGetAttributesWithOffsetExceedingLimit();
    }

    private static void testGetAttributesWithValidOffset() throws IOException {
        Path path = Paths.get("/path/to/valid/image");
        ByteOrder order = ByteOrder.BIG_ENDIAN;
        jdk.internal.jimage.BasicImageReader reader = new jdk.internal.jimage.BasicImageReader(path, order);
        int validOffset = 0; // assuming 0 is valid for test; in real test, set accordingly
        try {
            long[] result = reader.getAttributes(validOffset);
            // No exception expected, result should be non-null
            if (result == null) {
                throw new AssertionError("Expected non-null result for valid offset");
            }
        } catch (IndexOutOfBoundsException e) {
            throw new AssertionError("Unexpected IndexOutOfBoundsException for valid offset", e);
        }
    }

    private static void testGetAttributesWithNegativeOffset() throws IOException {
        Path path = Paths.get("/path/to/valid/image");
        ByteOrder order = ByteOrder.BIG_ENDIAN;
        jdk.internal.jimage.BasicImageReader reader = new jdk.internal.jimage.BasicImageReader(path, order);
        int invalidOffset = -1;
        try {
            reader.getAttributes(invalidOffset);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative offset");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    private static void testGetAttributesWithOffsetExceedingLimit() throws IOException {
        Path path = Paths.get("/path/to/valid/image");
        ByteOrder order = ByteOrder.BIG_ENDIAN;
        jdk.internal.jimage.BasicImageReader reader = new jdk.internal.jimage.BasicImageReader(path, order);
        int offsetExceedingLimit = Integer.MAX_VALUE; // or a large number beyond locations limit
        try {
            reader.getAttributes(offsetExceedingLimit);
            throw new AssertionError("Expected IndexOutOfBoundsException for offset exceeding limit");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
