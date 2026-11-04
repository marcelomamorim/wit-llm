/*
 * @test
 * @summary Test DerInputStream constructor with offset and length for valid and invalid inputs
 * @run main DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidConstructor();
        testInvalidConstructorThrowsIOException();
    }

    private static void testValidConstructor() throws IOException {
        byte[] data = new byte[] {0x30, 0x03, 0x01, 0x01, 0x00};
        // offset 0, length 5 is valid
        DerInputStream der = new DerInputStream(data, 0, 5);
        if (der == null) {
            throw new AssertionError("DerInputStream constructor returned null");
        }
        // offset 1, length 4 is valid
        der = new DerInputStream(data, 1, 4);
        if (der == null) {
            throw new AssertionError("DerInputStream constructor returned null");
        }
    }

    private static void testInvalidConstructorThrowsIOException() {
        byte[] data = new byte[] {0x30, 0x03};
        // Case where len + offset > data.length
        try {
            new DerInputStream(data, 1, 2);
            throw new AssertionError("Expected IOException not thrown when len + offset > data.length");
        } catch (IOException expected) {
            // expected
        }

        // Case where data.length - offset < 2
        try {
            new DerInputStream(data, 1, 1);
            throw new AssertionError("Expected IOException not thrown when data.length - offset < 2");
        } catch (IOException expected) {
            // expected
        }
    }
}
