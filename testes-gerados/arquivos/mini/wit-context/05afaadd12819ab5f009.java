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
        testGetEncodedKeyIdentifierReturnsNullWhenIdIsNull();
        testGetEncodedKeyIdentifierReturnsEncodedBytesWhenIdIsNotNull();
    }

    private static void testGetEncodedKeyIdentifierReturnsNullWhenIdIsNull() throws IOException {
        // Construct with critical and value, id will be null internally
        AuthorityKeyIdentifierExtension ext = new AuthorityKeyIdentifierExtension(Boolean.FALSE, new byte[0]);
        byte[] result = ext.getEncodedKeyIdentifier();
        if (result != null) {
            throw new AssertionError("Expected null when id is null, got non-null");
        }
    }

    private static void testGetEncodedKeyIdentifierReturnsEncodedBytesWhenIdIsNotNull() throws IOException {
        // Create a KeyIdentifier with a known byte array
        byte[] keyIdBytes = new byte[] {1, 2, 3, 4, 5};
        KeyIdentifier kid = new KeyIdentifier(keyIdBytes);

        AuthorityKeyIdentifierExtension ext = new AuthorityKeyIdentifierExtension(kid, null, null);
        byte[] encoded = ext.getEncodedKeyIdentifier();

        if (encoded == null) {
            throw new AssertionError("Expected non-null encoded bytes when id is set");
        }

        // The encoded bytes should contain the original keyIdBytes as a subsequence
        boolean found = false;
        for (int i = 0; i <= encoded.length - keyIdBytes.length; i++) {
            boolean match = true;
            for (int j = 0; j < keyIdBytes.length; j++) {
                if (encoded[i + j] != keyIdBytes[j]) {
                    match = false;
                    break;
                }
            }
            if (match) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new AssertionError("Encoded bytes do not contain the original key identifier bytes");
        }
    }
}
