/*
 * @test
 * @summary Test sun.security.x509.SubjectKeyIdentifierExtension.get(String) method
 * @run main SubjectKeyIdentifierExtensionWitupTest
 */

import sun.security.x509.SubjectKeyIdentifierExtension;
import sun.security.x509.KeyIdentifier;
import java.io.IOException;
import java.util.Arrays;

public class SubjectKeyIdentifierExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testGetWithValidKeyId();
        testGetWithInvalidNameThrowsIOException();
        System.out.println("All tests passed.");
    }

    private static void testGetWithValidKeyId() throws IOException {
        byte[] keyIdBytes = new byte[] {1, 2, 3, 4, 5};
        SubjectKeyIdentifierExtension ext = new SubjectKeyIdentifierExtension(keyIdBytes);
        KeyIdentifier keyId = ext.get("key_id");
        if (keyId == null) {
            throw new AssertionError("Expected non-null KeyIdentifier for 'key_id'");
        }
        byte[] returnedId = keyId.getIdentifier();
        if (!Arrays.equals(returnedId, keyIdBytes)) {
            throw new AssertionError("Returned KeyIdentifier bytes do not match input bytes");
        }
    }

    private static void testGetWithInvalidNameThrowsIOException() {
        byte[] keyIdBytes = new byte[] {10, 20, 30};
        try {
            SubjectKeyIdentifierExtension ext = new SubjectKeyIdentifierExtension(keyIdBytes);
            ext.get("invalid_name");
            throw new AssertionError("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new AssertionError("IOException message unexpected: " + msg);
            }
        }
    }
}
