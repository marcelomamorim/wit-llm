/*
 * @test
 * @summary Test sun.security.util.DerInputStream.getIA5String() for expected IOException and normal behavior
 * @run main DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import java.io.IOException;

public class DerInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidIA5String();
        testInvalidTagThrowsIOException();
        testEOFThrowsIOException();
    }

    static void testValidIA5String() throws IOException {
        // Construct a valid IA5String DER encoding: tag 0x16 (DerValue.tag_IA5String), length 3, value "abc"
        byte[] validIA5 = new byte[] { DerValue.tag_IA5String, 3, 'a', 'b', 'c' };
        DerInputStream dis = new DerInputStream(validIA5);
        String s = dis.getIA5String();
        if (!"abc".equals(s)) {
            throw new RuntimeException("Expected 'abc' but got '" + s + "'");
        }
    }

    static void testInvalidTagThrowsIOException() {
        // Construct DER encoding with invalid tag (not DerValue.tag_IA5String)
        // Use tag 0x17 (UTCTime) to trigger IOException in readString
        byte[] invalidTag = new byte[] { 0x17, 3, 'a', 'b', 'c' };
        try {
            DerInputStream dis = new DerInputStream(invalidTag);
            dis.getIA5String();
            throw new RuntimeException("Expected IOException due to invalid tag but none thrown");
        } catch (IOException e) {
            // Expected
        }
    }

    static void testEOFThrowsIOException() {
        // Construct DER encoding with correct tag but truncated length (length 5 but only 3 bytes provided)
        byte[] truncated = new byte[] { DerValue.tag_IA5String, 5, 'a', 'b', 'c' };
        try {
            DerInputStream dis = new DerInputStream(truncated);
            dis.getIA5String();
            throw new RuntimeException("Expected IOException due to EOF but none thrown");
        } catch (IOException e) {
            // Expected
        }
    }
}
