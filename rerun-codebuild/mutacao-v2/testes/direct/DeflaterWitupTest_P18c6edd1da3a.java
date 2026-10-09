/*
 * @test
 * @summary Test Deflater.setInput(ByteBuffer) method behavior
 * @run main DeflaterWitupTest_P18c6edd1da3a
 */

import java.util.zip.Deflater;
import java.nio.ByteBuffer;
import java.util.Objects;

public class DeflaterWitupTest_P18c6edd1da3a {
    public static void main(String[] args) {
        testSetInputWithValidByteBuffer();
        testSetInputWithNullThrowsNPE();
    }

    private static void testSetInputWithValidByteBuffer() {
        Deflater deflater = new Deflater();
        byte[] data = {1, 2, 3, 4, 5};
        ByteBuffer buffer = ByteBuffer.wrap(data);
        deflater.setInput(buffer);
        // No direct getter to verify internal state, but no exception means success
        // We test determinism by calling setInput again with same buffer
        deflater.setInput(buffer);
    }

    private static void testSetInputWithNullThrowsNPE() {
        Deflater deflater = new Deflater();
        try {
            deflater.setInput(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // Expected exception
        }
    }
}
