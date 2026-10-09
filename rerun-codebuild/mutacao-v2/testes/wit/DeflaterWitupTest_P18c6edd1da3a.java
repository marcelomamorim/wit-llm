/*
 * @test
 * @summary Test java.util.zip.Deflater.setInput(ByteBuffer) for null input and normal usage
 * @run main DeflaterWitupTest_P18c6edd1da3a
 */

import java.util.zip.Deflater;
import java.nio.ByteBuffer;
import java.util.Objects;

public class DeflaterWitupTest_P18c6edd1da3a {
    public static void main(String[] args) {
        testSetInputNull();
        testSetInputValid();
    }

    private static void testSetInputNull() {
        Deflater deflater = new Deflater();
        try {
            deflater.setInput(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // Expected exception
        } finally {
            deflater.end();
        }
    }

    private static void testSetInputValid() {
        Deflater deflater = new Deflater();
        ByteBuffer buffer = ByteBuffer.allocate(10);
        deflater.setInput(buffer);
        // No exception expected, verify no crash and internal state set (indirectly)
        // Since internal state is not accessible, just call deflate to check no error
        byte[] out = new byte[20];
        int len = deflater.deflate(out);
        if (len < 0) {
            throw new RuntimeException("Deflate returned negative length");
        }
        deflater.end();
    }
}
