/*
 * @test
 * @summary Test sun.security.x509.AuthorityKeyIdentifierExtension.getEncodedKeyIdentifier()
 * @run main AuthorityKeyIdentifierExtensionWitupTest
 */

import sun.security.x509.AuthorityKeyIdentifierExtension;
import sun.security.x509.KeyIdentifier;
import sun.security.util.DerOutputStream;
import java.io.IOException;

public class AuthorityKeyIdentifierExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testGetEncodedKeyIdentifierWithId();
        testGetEncodedKeyIdentifierWithoutId();
    }

    private static void testGetEncodedKeyIdentifierWithId() throws IOException {
        // Create a KeyIdentifier with a known byte array
        byte[] keyIdBytes = new byte[] {1, 2, 3, 4, 5};
        KeyIdentifier kid = new KeyIdentifier(keyIdBytes);

        // Construct AuthorityKeyIdentifierExtension with KeyIdentifier
        AuthorityKeyIdentifierExtension ext = new AuthorityKeyIdentifierExtension(kid, null, null);

        // Call getEncodedKeyIdentifier and verify it returns the DER encoding of the KeyIdentifier
        byte[] encoded = ext.getEncodedKeyIdentifier();
        if (encoded == null) {
            throw new RuntimeException("getEncodedKeyIdentifier() returned null when id is set");
        }

        // The encoded bytes should contain the original keyIdBytes as the octet string content
        // Decode to verify
        sun.security.util.DerValue val = new sun.security.util.DerValue(encoded);
        if (val.tag != sun.security.util.DerValue.tag_OctetString) {
            throw new RuntimeException("Encoded KeyIdentifier is not an OCTET STRING");
        }
        byte[] decodedBytes = val.getOctetString();
        if (decodedBytes.length != keyIdBytes.length) {
            throw new RuntimeException("Decoded bytes length mismatch");
        }
        for (int i = 0; i < decodedBytes.length; i++) {
            if (decodedBytes[i] != keyIdBytes[i]) {
                throw new RuntimeException("Decoded bytes content mismatch at index " + i);
            }
        }
    }

    private static void testGetEncodedKeyIdentifierWithoutId() throws IOException {
        // Construct AuthorityKeyIdentifierExtension with null KeyIdentifier
        AuthorityKeyIdentifierExtension ext = new AuthorityKeyIdentifierExtension(null, null, null);

        // Call getEncodedKeyIdentifier and verify it returns null
        byte[] encoded = ext.getEncodedKeyIdentifier();
        if (encoded != null) {
            throw new RuntimeException("getEncodedKeyIdentifier() should return null when id is null");
        }
    }
}
