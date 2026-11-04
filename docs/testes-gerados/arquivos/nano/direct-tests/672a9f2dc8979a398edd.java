/*
 * @test
 * @summary Test BasicImageReader.releaseByteBuffer for null and non-null ByteBuffer
 * @run main BasicImageReaderWitupTest
 */

import java.nio.ByteBuffer;
import jdk.internal.jimage.BasicImageReader;

public class BasicImageReaderWitupTest {

    public static void main(String[] args) {
        testReleaseByteBufferWithNull();
        testReleaseByteBufferWithValidBuffer();
    }

    static void testReleaseByteBufferWithNull() {
        boolean exceptionThrown = false;
        try {
            BasicImageReader.releaseByteBuffer(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException for null ByteBuffer");
        }
    }

    static void testReleaseByteBufferWithValidBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        // No exception expected
        BasicImageReader.releaseByteBuffer(buffer);
        // Additional check: buffer should still be accessible
        if (buffer.capacity() != 10) {
            throw new AssertionError("Buffer capacity changed unexpectedly");
        }
    }
}
