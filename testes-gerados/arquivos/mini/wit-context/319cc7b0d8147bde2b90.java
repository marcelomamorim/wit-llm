/*
 * @test
 * @summary Test sun.security.util.DerInputStream constructor with byte[] input
 * @run main DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidInput();
        testInputTooShort();
    }

    private static void testValidInput() throws IOException {
        // Minimal valid DER encoding: tag + length (e.g. 0x30 0x00 for empty SEQUENCE)
        byte[] validData = new byte[] {0x30, 0x00};
        DerInputStream der = new DerInputStream(validData);
        if (der == null) {
            throw new AssertionError("DerInputStream constructor returned null");
        }
    }

    private static void testInputTooShort() {
        // According to WIT, input length < 2 should cause IOException
        byte[] shortData = new byte[] {0x30};
        try {
            new DerInputStream(shortData);
            throw new AssertionError("Expected IOException for input length < 2");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
