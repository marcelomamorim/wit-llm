/*
 * @test
 * @summary Test sun.security.util.DerInputStream.getUTF8String()
 * @run main sun.security.util.DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidUTF8String();
        testInvalidTag();
    }

    private static void testValidUTF8String() throws IOException {
        // DER encoding of UTF8String "test"
        // Tag for UTF8String is 0x0C
        // Encoding: 0C 04 74 65 73 74
        byte[] validUTF8 = new byte[] { 0x0C, 0x04, 0x74, 0x65, 0x73, 0x74 };
        DerInputStream der = new DerInputStream(validUTF8);
        String s = der.getUTF8String();
        if (!"test".equals(s)) {
            throw new RuntimeException("Expected 'test' but got '" + s + "'");
        }
    }

    private static void testInvalidTag() {
        // Construct DER encoding with wrong tag (e.g. PrintableString tag 0x13)
        // Encoding: 13 04 74 65 73 74
        byte[] invalidTag = new byte[] { 0x13, 0x04, 0x74, 0x65, 0x73, 0x74 };
        try {
            DerInputStream der = new DerInputStream(invalidTag);
            der.getUTF8String();
            throw new RuntimeException("Expected IOException due to tag mismatch");
        } catch (IOException e) {
            // Expected exception due to tag mismatch
        }
    }
}
