/*
 * @test
 * @summary Tests PKCS12Attribute(byte[] encoded) null handling, DER parsing and defensive copy
 * @run main PKCS12AttributeWitupTest
 */

import java.security.PKCS12Attribute;

public class PKCS12AttributeWitupTest {

    public static void main(String[] args) throws Exception {
        testNullEncodedThrowsNPE();
        testMalformedEncodedThrowsIAE();
        testValidEncodedParsesName();
        testDefensiveCopyIgnoresLaterMutation();
    }

    static void testNullEncodedThrowsNPE() {
        try {
            new PKCS12Attribute((byte[]) null);
            throw new RuntimeException("Expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testMalformedEncodedThrowsIAE() {
        try {
            new PKCS12Attribute(new byte[]{0x01, 0x02});
            throw new RuntimeException("Expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testValidEncodedParsesName() {
        // SEQUENCE { OID 1.2.3, SET { UTF8String "abc" } }
        byte[] der = new byte[] {
            0x30, 0x0B,
            0x06, 0x02, 0x2A, 0x03,
            0x31, 0x05,
            0x0C, 0x03, 0x61, 0x62, 0x63
        };
        PKCS12Attribute attr = new PKCS12Attribute(der);
        if (!"1.2.3".equals(attr.getName())) {
            throw new RuntimeException("Unexpected name: " + attr.getName());
        }
    }

    static void testDefensiveCopyIgnoresLaterMutation() {
        byte[] der = new byte[] {
            0x30, 0x0B,
            0x06, 0x02, 0x2A, 0x03,
            0x31, 0x05,
            0x0C, 0x03, 0x61, 0x62, 0x63
        };
        PKCS12Attribute attr = new PKCS12Attribute(der);
        der[0] = 0x00;
        der[1] = 0x00;
        if (!"1.2.3".equals(attr.getName())) {
            throw new RuntimeException("Name changed after input mutation: " + attr.getName());
        }
    }
}
