/*
 * @test
 * @summary Test sun.security.util.DerInputStream.getPrintableString()
 * @run main sun.security.util.DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import java.io.IOException;

public class DerInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidPrintableString();
        testInvalidTagPrintableString();
    }

    private static void testValidPrintableString() throws IOException {
        // Construct DER encoding for PrintableString "HELLO"
        // DER encoding: tag=0x13 (PrintableString), length=5, value="HELLO"
        byte[] derData = new byte[] { 0x13, 0x05, 'H', 'E', 'L', 'L', 'O' };
        DerInputStream derInput = new DerInputStream(derData);
        String result = derInput.getPrintableString();
        if (!"HELLO".equals(result)) {
            throw new RuntimeException("Expected 'HELLO' but got '" + result + "'");
        }
    }

    private static void testInvalidTagPrintableString() {
        // Construct DER encoding with wrong tag (e.g. IA5String tag 0x16) but call getPrintableString
        byte[] derData = new byte[] { 0x16, 0x05, 'H', 'E', 'L', 'L', 'O' };
        try {
            DerInputStream derInput = new DerInputStream(derData);
            derInput.getPrintableString();
            throw new RuntimeException("Expected IOException due to tag mismatch but none thrown");
        } catch (IOException e) {
            // Expected exception due to tag mismatch
        }
    }
}
