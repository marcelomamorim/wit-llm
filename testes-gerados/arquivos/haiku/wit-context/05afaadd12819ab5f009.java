/*
 * @test
 * @summary Verify getEncodedKeyIdentifier returns null when no key identifier is set and the DER-encoded key identifier otherwise
 * @modules java.base/sun.security.x509
 * @run main AuthorityKeyIdentifierExtensionWitupTest
 */

import java.util.Arrays;

import sun.security.x509.AuthorityKeyIdentifierExtension;

public class AuthorityKeyIdentifierExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        AuthorityKeyIdentifierExtension empty =
                new AuthorityKeyIdentifierExtension(null, null, null);
        if (empty.getEncodedKeyIdentifier() != null) {
            throw new RuntimeException("Expected null when no key identifier is present");
        }

        byte[] value = {0x30, 0x04, (byte) 0x80, 0x02, (byte) 0xAB, (byte) 0xCD};
        AuthorityKeyIdentifierExtension withId =
                new AuthorityKeyIdentifierExtension(Boolean.FALSE, value);
        byte[] encoded = withId.getEncodedKeyIdentifier();
        byte[] expected = {0x04, 0x02, (byte) 0xAB, (byte) 0xCD};
        if (encoded == null) {
            throw new RuntimeException("Expected encoded key identifier, got null");
        }
        if (!Arrays.equals(expected, encoded)) {
            throw new RuntimeException("Unexpected encoding: " + Arrays.toString(encoded));
        }
    }
}
