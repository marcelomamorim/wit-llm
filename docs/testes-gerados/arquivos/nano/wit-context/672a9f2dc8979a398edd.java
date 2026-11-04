/* @test    @summary Test for BasicImageReader.releaseByteBuffer(ByteBuffer) handling null buffer    @run main BasicImageReaderWitupTest */

import java.nio.ByteBuffer;
import jdk.internal.jimage.BasicImageReader;

public class BasicImageReaderWitupTest {

    public static void main(String[] args) {
        testReleaseByteBufferWithNull();
        testReleaseByteBufferWithValidBuffer();
    }

    private static void testReleaseByteBufferWithNull() {
        boolean exceptionThrown = false;
        try {
            BasicImageReader.releaseByteBuffer(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null buffer");
        }
    }

    private static void testReleaseByteBufferWithValidBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        try {
            BasicImageReader.releaseByteBuffer(buffer);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid buffer: " + e);
        }
    }
}
