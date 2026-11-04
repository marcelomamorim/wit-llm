/*
 * @test
 * @summary Test BasicImageReader.releaseByteBuffer with valid and null ByteBuffer
 * @run main BasicImageReaderWitupTest
 */

import java.nio.ByteBuffer;
import java.util.Objects;

public class BasicImageReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testReleaseByteBufferWithValidBuffer();
        testReleaseByteBufferWithNullBuffer();
    }

    private static void testReleaseByteBufferWithValidBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(1);
        // Should not throw any exception
        jdk.internal.jimage.BasicImageReader.releaseByteBuffer(buffer);
    }

    private static void testReleaseByteBufferWithNullBuffer() {
        try {
            jdk.internal.jimage.BasicImageReader.releaseByteBuffer(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // Expected exception
        }
    }
}
