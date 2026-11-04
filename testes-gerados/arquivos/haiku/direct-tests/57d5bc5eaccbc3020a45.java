/*
 * @test
 * @summary Verify DerValue.getBitString() returns the bit string payload and rejects non-BIT STRING tags
 * @modules java.base/sun.security.util
 * @run main DerValueWitupTest
 */

import java.io.IOException;
import sun.security.util.DerValue;

public class DerValueWitupTest {

    public static void main(String[] args) throws Exception {
        testBitStringReturnsPayload();
        testNonBitStringTagThrowsIOException();
        testOctetStringTagThrowsIOException();
        testIntegerTagMessageMentionsBitString();
    }

    static void testBitStringReturnsPayload() throws Exception {
        // tag 0x03 (BIT STRING), length 3, unused bits 0x00, data 0x12 0x34
        byte[] enc = {0x03, 0x03, 0x00, 0x12, 0x34};
        DerValue dv = new DerValue(enc);
        byte[] bits = dv.getBitString();
        if (bits == null || bits.length != 2 || bits[0] != 0x12 || bits[1] != 0x34) {
            throw new RuntimeException("Unexpected bit string payload");
        }
    }

    static void testNonBitStringTagThrowsIOException() throws Exception {
        // tag 0x02 (INTEGER), length 1, value 0x05
        byte[] enc = {0x02, 0x01, 0x05};
        DerValue dv = new DerValue(enc);
        try {
            dv.getBitString();
            throw new RuntimeException("Expected IOException for INTEGER tag");
        } catch (IOException expected) {
            // expected
        }
    }

    static void testOctetStringTagThrowsIOException() throws Exception {
        // tag 0x04 (OCTET STRING), length 2
        byte[] enc = {0x04, 0x02, 0x01, 0x02};
        DerValue dv = new DerValue(enc);
        try {
            dv.getBitString();
            throw new RuntimeException("Expected IOException for OCTET STRING tag");
        } catch (IOException expected) {
            if (expected.getMessage() == null
                    || !expected.getMessage().contains("not a bit string")) {
                throw new RuntimeException("Unexpected message: " + expected.getMessage());
            }
        }
    }

    static void testIntegerTagMessageMentionsBitString() throws Exception {
        byte[] enc = {0x02, 0x01, 0x05};
        DerValue dv = new DerValue(enc);
        try {
            dv.getBitString();
            throw new RuntimeException("Expected IOException");
        } catch (IOException expected) {
            String msg = expected.getMessage();
            if (msg == null || !msg.contains("not a bit string")) {
                throw new RuntimeException("Unexpected message: " + msg);
            }
        }
    }
}
