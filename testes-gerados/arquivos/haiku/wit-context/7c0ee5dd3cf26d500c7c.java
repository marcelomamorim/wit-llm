/*
 * @test
 * @summary Verify DerValue.getBMPString tag guard and BMP decoding
 * @modules java.base/sun.security.util
 * @run main DerValueWitupTest
 */
import java.io.IOException;
import sun.security.util.DerValue;

public class DerValueWitupTest {
    public static void main(String[] args) throws Exception {
        testWrongTagThrowsIOException();
        testBmpDecodingBigEndian();
        testEmptyBmpString();
        testNonAsciiBmpString();
        testBmpFromParsedEncoding();
    }

    static void testWrongTagThrowsIOException() throws Exception {
        DerValue v = new DerValue(DerValue.tag_OctetString, new byte[] {0x00, 0x41});
        boolean thrown = false;
        try {
            v.getBMPString();
        } catch (IOException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException for non-BMP tag");
        }
    }

    static void testBmpDecodingBigEndian() throws Exception {
        DerValue v = new DerValue(DerValue.tag_BMPString,
                new byte[] {0x00, 0x41, 0x00, 0x42});
        String s = v.getBMPString();
        if (!"AB".equals(s)) {
            throw new RuntimeException("Unexpected BMP decoding: " + s);
        }
    }

    static void testEmptyBmpString() throws Exception {
        DerValue v = new DerValue(DerValue.tag_BMPString, new byte[0]);
        String s = v.getBMPString();
        if (s == null || !s.isEmpty()) {
            throw new RuntimeException("Expected empty string but got: " + s);
        }
    }

    static void testNonAsciiBmpString() throws Exception {
        DerValue v = new DerValue(DerValue.tag_BMPString,
                new byte[] {0x20, (byte) 0xAC});
        String s = v.getBMPString();
        if (!"\u20AC".equals(s)) {
            throw new RuntimeException("Unexpected non-ASCII decoding: " + s);
        }
    }

    static void testBmpFromParsedEncoding() throws Exception {
        byte[] encoding = new byte[] {
            (byte) DerValue.tag_BMPString, 0x04, 0x00, 0x41, 0x00, 0x42
        };
        DerValue v = new DerValue(encoding);
        String s = v.getBMPString();
        if (!"AB".equals(s)) {
            throw new RuntimeException("Unexpected parsed BMP decoding: " + s);
        }
    }
}
