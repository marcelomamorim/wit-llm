/*
 * @test
 * @summary Test sun.security.util.DerInputStream.getUTF8String() for expected behavior and exception paths
 * @run main DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import java.io.IOException;

public class DerInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidUTF8String();
        testInvalidTagThrowsIOException();
        testEmptyBufferThrowsIOException();
    }

    static void testValidUTF8String() throws IOException {
        // Construct a DER encoding of a UTF8String with tag 0x0C (tag_UTF8String)
        // Encoding: tag (0x0C), length (1), value (0x41 = 'A')
        byte[] validUtf8 = new byte[] { DerValue.tag_UTF8String, 0x01, 0x41 };
        DerInputStream dis = new DerInputStream(validUtf8);
        String s = dis.getUTF8String();
        if (!"A".equals(s)) {
            throw new RuntimeException("Expected string 'A', got: " + s);
        }
    }

    static void testInvalidTagThrowsIOException() {
        // Construct a DER encoding with invalid tag (not tag_UTF8String)
        // tag = 0x0A (arbitrary invalid tag for UTF8String), length = 1, value = 0x41
        byte[] invalidTag = new byte[] { 0x0A, 0x01, 0x41 };
        try {
            DerInputStream dis = new DerInputStream(invalidTag);
            dis.getUTF8String();
            throw new RuntimeException("Expected IOException due to invalid tag, but none thrown");
        } catch (IOException e) {
            // Expected
        }
    }

    static void testEmptyBufferThrowsIOException() {
        // Empty buffer should cause IOException when reading UTF8String
        byte[] empty = new byte[0];
        try {
            DerInputStream dis = new DerInputStream(empty);
            dis.getUTF8String();
            throw new RuntimeException("Expected IOException due to empty buffer, but none thrown");
        } catch (IOException e) {
            // Expected
        }
    }
}
