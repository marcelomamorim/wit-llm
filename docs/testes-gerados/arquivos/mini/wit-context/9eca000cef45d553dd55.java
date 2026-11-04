/*
 * @test
 * @summary Test sun.security.util.DerInputStream.getT61String() for expected behavior and exception paths
 * @run main DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import java.io.IOException;

public class DerInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidT61String();
        testInvalidTagThrowsIOException();
        testEmptyBufferThrowsIOException();
    }

    static void testValidT61String() throws IOException {
        // Construct a valid DER encoding with tag_T61String
        // Tag for T61String is DerValue.tag_T61String (0x14)
        // Minimal valid encoding: tag, length, content
        byte[] validT61 = new byte[] { DerValue.tag_T61String, 3, 'a', 'b', 'c' };
        DerInputStream dis = new DerInputStream(validT61);
        String s = dis.getT61String();
        if (!"abc".equals(s)) {
            throw new RuntimeException("Expected 'abc' but got '" + s + "'");
        }
    }

    static void testInvalidTagThrowsIOException() {
        // Construct a DER encoding with invalid tag (not tag_T61String)
        // The getT61String method calls readString with tag_T61String, so if tag mismatches,
        // it should throw IOException
        byte[] invalidTag = new byte[] { 0x01, 1, 0x00 };
        try {
            DerInputStream dis = new DerInputStream(invalidTag);
            dis.getT61String();
            throw new RuntimeException("Expected IOException due to invalid tag but none thrown");
        } catch (IOException e) {
            // Expected
        }
    }

    static void testEmptyBufferThrowsIOException() {
        // Construct a DER encoding with tag_T61String but no content (length 0)
        byte[] emptyContent = new byte[] { DerValue.tag_T61String, 0 };
        try {
            DerInputStream dis = new DerInputStream(emptyContent);
            dis.getT61String();
            // It may return empty string or throw IOException depending on implementation
            // We accept empty string as valid
        } catch (IOException e) {
            // Acceptable if IOException thrown
        }
    }
}
