/*
 * @test
 * @summary Verifies that BasicImageReader.releaseByteBuffer rejects null with NullPointerException
 * @modules java.base/jdk.internal.jimage
 * @run main BasicImageReaderWitupTest
 */

import java.nio.ByteBuffer;
import jdk.internal.jimage.BasicImageReader;

public class BasicImageReaderWitupTest {
    public static void main(String[] args) throws Exception {
        testNullBufferThrowsNPE();
    }

    private static void testNullBufferThrowsNPE() {
        boolean thrown = false;
        try {
            BasicImageReader.releaseByteBuffer(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NullPointerException for null buffer");
        }
    }
}
