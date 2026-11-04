/*
 * @test
 * @summary Verifies DerInputStream.getBMPString() decodes BMP strings and rejects a mismatched DER tag
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        check(new DerInputStream(new byte[]{0x1E, 0x04, 0x00, 0x41, 0x00, 0x42}).getBMPString(), "AB");
        check(new DerInputStream(new byte[]{0x1E, 0x00}).getBMPString(), "");
        check(new DerInputStream(new byte[]{0x1E, 0x02, 0x00, (byte) 0xE9}).getBMPString(), "\u00E9");
        byte[] framed = new byte[]{0x7F, 0x7F, 0x1E, 0x04, 0x00, 0x41, 0x00, 0x42, 0x7F};
        check(new DerInputStream(framed, 2, 6, true).getBMPString(), "AB");
        expectIOException(new byte[]{0x0C, 0x02, 0x41, 0x42});
    }

    private static void check(String actual, String expected) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected \"" + expected + "\" but got \"" + actual + "\"");
        }
    }

    private static void expectIOException(byte[] data) throws Exception {
        try {
            new DerInputStream(data).getBMPString();
        } catch (java.io.IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for non-BMP tag");
    }
}
