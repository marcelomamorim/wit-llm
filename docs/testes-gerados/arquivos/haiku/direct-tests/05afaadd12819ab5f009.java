/*
 * @test
 * @summary Checks getEncodedKeyIdentifier returns null when no key identifier is present and the DER-encoded key identifier when one is present
 * @modules java.base/sun.security.x509
 * @run main AuthorityKeyIdentifierExtensionWitupTest
 */

import java.util.Arrays;

import sun.security.x509.AuthorityKeyIdentifierExtension;

public class AuthorityKeyIdentifierExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testNullFieldsReturnsNull();
        testEmptySequenceReturnsNull();
        testKeyIdentifierReturnsDerOctetString();
        System.out.println("PASS");
    }

    static void testNullFieldsReturnsNull() throws Exception {
        AuthorityKeyIdentifierExtension ext =
            new AuthorityKeyIdentifierExtension(null, null, null);
        byte[] result = ext.getEncodedKeyIdentifier();
        if (result != null) {
            throw new RuntimeException("Expected null when no key identifier is set");
        }
    }

    static void testEmptySequenceReturnsNull() throws Exception {
        byte[] value = new byte[] {0x30, 0x00};
        AuthorityKeyIdentifierExtension ext =
            new AuthorityKeyIdentifierExtension(Boolean.FALSE, value);
        byte[] result = ext.getEncodedKeyIdentifier();
        if (result != null) {
            throw new RuntimeException("Expected null for empty AuthorityKeyIdentifier sequence");
        }
    }

    static void testKeyIdentifierReturnsDerOctetString() throws Exception {
        byte[] keyId = new byte[] {0x0A, 0x0B, 0x0C, 0x0D};
        byte[] value = new byte[] {
            0x30, 0x06,
            (byte) 0x80, 0x04, 0x0A, 0x0B, 0x0C, 0x0D
        };
        AuthorityKeyIdentifierExtension ext =
            new AuthorityKeyIdentifierExtension(Boolean.FALSE, value);
        byte[] result = ext.getEncodedKeyIdentifier();
        if (result == null) {
            throw new RuntimeException("Expected encoded key identifier, got null");
        }
        byte[] expected = new byte[] {0x04, 0x04, 0x0A, 0x0B, 0x0C, 0x0D};
        if (!Arrays.equals(expected, result)) {
            throw new RuntimeException("Unexpected encoding: " + Arrays.toString(result));
        }
    }
}
