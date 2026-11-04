/*
 * @test
 * @summary Test sun.security.util.DerInputStream.getPrintableString() for expected behavior and exception paths
 * @run main DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidPrintableString();
        testEmptyInputThrowsIOException();
        testInvalidTagThrowsIOException();
    }

    private static void testValidPrintableString() throws IOException {
        // Construct a valid DER encoding of a PrintableString: tag 0x13, length 3, value "abc"
        byte[] validPrintableString = new byte[] { 0x13, 0x03, 0x61, 0x62, 0x63 };
        DerInputStream der = new DerInputStream(validPrintableString);
        String result = der.getPrintableString();
        if (!"abc".equals(result)) {
            throw new RuntimeException("Expected 'abc' but got: " + result);
        }
    }

    private static void testEmptyInputThrowsIOException() {
        // Empty input should cause IOException because no data to read
        byte[] empty = new byte[0];
        try {
            DerInputStream der = new DerInputStream(empty);
            der.getPrintableString();
            throw new RuntimeException("Expected IOException for empty input");
        } catch (IOException e) {
            // Expected
        }
    }

    private static void testInvalidTagThrowsIOException() {
        // Input with invalid tag (not PrintableString) should cause IOException
        // For example, tag 0x12 (NumericString) with length 1 and value 'a'
        byte[] invalidTag = new byte[] { 0x12, 0x01, 0x61 };
        try {
            DerInputStream der = new DerInputStream(invalidTag);
            der.getPrintableString();
            throw new RuntimeException("Expected IOException for invalid tag");
        } catch (IOException e) {
            // Expected
        }
    }
}
