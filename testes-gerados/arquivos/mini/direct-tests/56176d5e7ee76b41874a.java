/*
 * @test
 * @summary Test sun.security.util.DerInputStream.getGeneralString()
 * @run main sun.security.util.DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidGeneralString();
        testInvalidTagThrowsIOException();
    }

    private static void testValidGeneralString() throws IOException {
        // DER encoding for GeneralString tag (0x1B) with length 5 and ASCII content "Hello"
        byte[] validGeneralString = new byte[] { 0x1B, 0x05, 'H', 'e', 'l', 'l', 'o' };
        DerInputStream der = new DerInputStream(validGeneralString);
        String result = der.getGeneralString();
        if (!"Hello".equals(result)) {
            throw new AssertionError("Expected 'Hello' but got '" + result + "'");
        }
    }

    private static void testInvalidTagThrowsIOException() {
        // DER encoding with wrong tag (0x1C) instead of GeneralString (0x1B)
        byte[] invalidTag = new byte[] { 0x1C, 0x03, 'a', 'b', 'c' };
        try {
            DerInputStream der = new DerInputStream(invalidTag);
            der.getGeneralString();
            throw new AssertionError("Expected IOException due to invalid tag but none thrown");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
