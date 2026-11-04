/*
 * @test
 * @summary ReasonFlags(DerValue) accepts a DER BIT STRING and rejects a non-BIT STRING tag
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main ReasonFlagsWitupTest
 */

import java.io.IOException;

import sun.security.util.DerValue;
import sun.security.x509.ReasonFlags;

public class ReasonFlagsWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        testValidBitStringAccepted();
        testNonBitStringTagRejected();
    }

    static void testValidBitStringAccepted() throws Exception {
        // BIT STRING, length 2, 1 unused bit, data 0x80
        byte[] der = new byte[] {0x03, 0x02, 0x01, (byte) 0x80};
        DerValue dv = new DerValue(der);
        ReasonFlags flags = new ReasonFlags(dv);
        check(flags != null, "ReasonFlags should be created from a valid BIT STRING");
    }

    static void testNonBitStringTagRejected() throws Exception {
        // INTEGER, length 1, value 5 -- not a BIT STRING
        byte[] der = new byte[] {0x02, 0x01, 0x05};
        DerValue dv = new DerValue(der);
        boolean thrown = false;
        try {
            new ReasonFlags(dv);
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "ReasonFlags(DerValue) must throw IOException for a non-BIT STRING tag");
    }
}
