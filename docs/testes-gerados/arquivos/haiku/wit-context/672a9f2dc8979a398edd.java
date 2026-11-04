/*
 * @test
 * @summary Witup-style tests for BasicImageReader.releaseByteBuffer
 * @modules java.base/jdk.internal.jimage
 * @run main BasicImageReaderWitupTest
 */
import java.nio.ByteBuffer;
import jdk.internal.jimage.BasicImageReader;

public class BasicImageReaderWitupTest {
    public static void main(String[] args) throws Exception {
        testNullBufferThrowsNPE();
        testNonNullBufferDoesNotThrow();
    }

    static void testNullBufferThrowsNPE() {
        try {
            BasicImageReader.releaseByteBuffer(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new AssertionError("Expected NullPointerException");
    }

    static void testNonNullBufferDoesNotThrow() {
        BasicImageReader.releaseByteBuffer(ByteBuffer.allocate(16));
    }
}
