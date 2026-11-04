/*
 * @test
 * @summary Tests DerValue.getBMPString() decoding and tag validation
 * @modules java.base/sun.security.util
 * @run main DerValueWitupTest
 */

import java.io.IOException;

import sun.security.util.DerValue;

public class DerValueWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testParsedBMPString() throws IOException {
        // TLV: tag 0x1E (BMPString), length 4, "AB" in UTF-16BE
        byte[] enc = {0x1E, 0x04, 0x00, 0x41, 0x00, 0x42};
        DerValue v = new DerValue(enc);
        check("AB".equals(v.getBMPString()), "parsed BMPString should be AB");
    }

    static void testConstructedBMPString() throws IOException {
        byte[] data = {0x00, 0x41, 0x00, 0x42, 0x00, 0x43};
        DerValue v = new DerValue((byte) 0x1E, data);
        check("ABC".equals(v.getBMPString()), "constructed BMPString should be ABC");
    }

    static void testEmptyBMPString() throws IOException {
        DerValue v = new DerValue((byte) 0x1E, new byte[0]);
        check("".equals(v.getBMPString()), "empty BMPString should decode to empty string");
    }

    static void testNonAsciiBMPString() throws IOException {
        // U+00E9 in big endian
        byte[] data = {0x00, (byte) 0xE9};
        DerValue v = new DerValue((byte) 0x1E, data);
        check("\u00E9".equals(v.getBMPString()), "non-ASCII BMPString should decode U+00E9");
    }

    static void testWrongTagThrows() {
        byte[] data = {0x00, 0x41};
        DerValue v = new DerValue((byte) 0x04, data); // OCTET STRING
        boolean thrown = false;
        try {
            v.getBMPString();
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "getBMPString on non-BMP tag should throw IOException");
    }

    public static void main(String[] args) throws Exception {
        testParsedBMPString();
        testConstructedBMPString();
        testEmptyBMPString();
        testNonAsciiBMPString();
        testWrongTagThrows();
    }
}
