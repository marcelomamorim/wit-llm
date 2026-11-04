/*
 * @test
 * @summary Test sun.security.util.DerInputStream.getIA5String() method
 * @run main sun.security.util.DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import java.io.IOException;

public class DerInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidIA5String();
        testInvalidTagThrowsIOException();
    }

    private static void testValidIA5String() throws IOException {
        // Construct DER encoding for IA5String "Hello"
        // Tag for IA5String is 22 (0x16)
        // Encoding: 0x16 0x05 'H' 'e' 'l' 'l' 'o'
        byte[] derEncoded = new byte[] { 0x16, 0x05, 0x48, 0x65, 0x6c, 0x6c, 0x6f };
        DerInputStream derIn = new DerInputStream(derEncoded);
        String result = derIn.getIA5String();
        if (!"Hello".equals(result)) {
            throw new RuntimeException("Expected 'Hello' but got '" + result + "'");
        }
    }

    private static void testInvalidTagThrowsIOException() {
        // Construct DER encoding with invalid tag (e.g. PrintableString tag 19 (0x13))
        // Encoding: 0x13 0x05 'H' 'e' 'l' 'l' 'o'
        byte[] derEncoded = new byte[] { 0x13, 0x05, 0x48, 0x65, 0x6c, 0x6c, 0x6f };
        try {
            DerInputStream derIn = new DerInputStream(derEncoded);
            derIn.getIA5String();
            throw new RuntimeException("Expected IOException due to invalid tag but none thrown");
        } catch (IOException e) {
            // Expected exception due to tag mismatch
        }
    }
}
