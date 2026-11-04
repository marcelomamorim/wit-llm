/*
 * @test
 * @summary Verify DerValue.getBitString() rejects non-BIT STRING tags and returns the bit string payload for BIT STRING tags
 * @modules java.base/sun.security.util
 * @run main DerValueWitupTest
 */

import java.io.IOException;
import sun.security.util.DerValue;

public class DerValueWitupTest {

    public static void main(String[] args) throws Exception {
        testNonBitStringTagThrowsIOException();
        testBitStringTagReturnsPayload();
        testBitStringFromEncodedBytes();
    }

    private static void testNonBitStringTagThrowsIOException() {
        DerValue integerValue = new DerValue(DerValue.tag_Integer, new byte[] {0x00, 0x01});
        try {
            integerValue.getBitString();
            throw new AssertionError("Expected IOException for INTEGER tag");
        } catch (IOException expected) {
            // expected
        }

        DerValue octetValue = new DerValue(DerValue.tag_OctetString, new byte[] {0x01});
        try {
            octetValue.getBitString();
            throw new AssertionError("Expected IOException for OCTET STRING tag");
        } catch (IOException expected) {
            // expected
        }
    }

    private static void testBitStringTagReturnsPayload() throws IOException {
        DerValue value = new DerValue(DerValue.tag_BitString,
                new byte[] {0x00, 0x01, 0x02});
        byte[] result = value.getBitString();
        if (result == null) {
            throw new AssertionError("getBitString returned null");
        }
        if (result.length != 2 || result[0] != 0x01 || result[1] != 0x02) {
            throw new AssertionError("Unexpected bit string payload length=" + result.length);
        }
    }

    private static void testBitStringFromEncodedBytes() throws IOException {
        byte[] encoded = new byte[] {0x03, 0x02, 0x00, (byte) 0xAB};
        DerValue value = new DerValue(encoded);
        byte[] result = value.getBitString();
        if (result.length != 1 || result[0] != (byte) 0xAB) {
            throw new AssertionError("Unexpected decoded bit string");
        }
    }
}
