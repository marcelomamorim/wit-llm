/*
 * @test
 * @summary Test PushbackInputStream constructor with various buffer sizes
 * @run main PushbackInputStreamWitupTest
 */

import java.io.PushbackInputStream;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

public class PushbackInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidSize();
        testInvalidSizeZero();
        testInvalidSizeNegative();
    }

    private static void testValidSize() throws Exception {
        byte[] data = {1, 2, 3};
        InputStream in = new ByteArrayInputStream(data);
        PushbackInputStream pbis = new PushbackInputStream(in, 5);
        if (pbis.buf.length != 5) {
            throw new RuntimeException("Buffer size not set correctly");
        }
        if (pbis.pos != 5) {
            throw new RuntimeException("Position not initialized correctly");
        }
        pbis.close();
    }

    private static void testInvalidSizeZero() {
        byte[] data = {1};
        InputStream in = new ByteArrayInputStream(data);
        try {
            new PushbackInputStream(in, 0);
            throw new RuntimeException("Expected IllegalArgumentException for size=0");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testInvalidSizeNegative() {
        byte[] data = {1};
        InputStream in = new ByteArrayInputStream(data);
        try {
            new PushbackInputStream(in, -1);
            throw new RuntimeException("Expected IllegalArgumentException for size<0");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
