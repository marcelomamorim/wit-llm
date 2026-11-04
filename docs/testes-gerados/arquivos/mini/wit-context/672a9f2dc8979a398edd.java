/*
 * @test
 * @summary Test BasicImageReader.releaseByteBuffer method for null argument and normal usage
 * @run main BasicImageReaderWitupTest
 */

import java.nio.ByteBuffer;
import java.util.Objects;
import jdk.internal.jimage.BasicImageReader;

public class BasicImageReaderWitupTest {
    public static void main(String[] args) throws Exception {
        testReleaseByteBufferNull();
        testReleaseByteBufferNonNull();
        System.out.println("PASS");
    }

    private static void testReleaseByteBufferNull() {
        try {
            BasicImageReader.releaseByteBuffer(null);
            throw new AssertionError("Expected NullPointerException for null buffer");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testReleaseByteBufferNonNull() {
        // Create a dummy ByteBuffer to pass
        ByteBuffer buffer = ByteBuffer.allocate(1);
        // Call releaseByteBuffer with non-null buffer should not throw
        BasicImageReader.releaseByteBuffer(buffer);
    }
}
