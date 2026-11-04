/*
 * @test
 * @summary Witup-derived checks for PKCS12Attribute(byte[]) constructor exception behavior
 * @run main PKCS12AttributeWitupTest
 */

import java.security.PKCS12Attribute;

public class PKCS12AttributeWitupTest {

    public static void main(String[] args) {
        testNullEncodedThrowsNPE();
        testTruncatedEncodingThrowsIAE();
    }

    static void testNullEncodedThrowsNPE() {
        boolean thrown = false;
        try {
            new PKCS12Attribute((byte[]) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null encoded array");
        }
    }

    static void testTruncatedEncodingThrowsIAE() {
        byte[] truncated = new byte[] { 0x30, 0x05, 0x02 };
        boolean thrown = false;
        try {
            new PKCS12Attribute(truncated);
        } catch (IllegalArgumentException e) {
            thrown = true;
            if (e.getCause() == null) {
                throw new RuntimeException("Expected cause for malformed encoding");
            }
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for truncated encoding");
        }
    }
}
