/*
 * @test
 * @summary Test sun.security.x509.SubjectKeyIdentifierExtension.get(String) method
 * @run main SubjectKeyIdentifierExtensionWitupTest
 */

import sun.security.x509.SubjectKeyIdentifierExtension;
import sun.security.x509.KeyIdentifier;
import java.io.IOException;

public class SubjectKeyIdentifierExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testGetWithValidKeyId();
        testGetWithInvalidNameThrowsIOException();
    }

    private static void testGetWithValidKeyId() throws Exception {
        byte[] keyId = new byte[] {1, 2, 3, 4, 5};
        SubjectKeyIdentifierExtension ext = new SubjectKeyIdentifierExtension(keyId);
        KeyIdentifier result = ext.get("key_id");
        if (result == null) {
            throw new RuntimeException("get returned null for valid key_id");
        }
        byte[] resultBytes = result.getIdentifier();
        if (resultBytes == null || resultBytes.length != keyId.length) {
            throw new RuntimeException("Returned KeyIdentifier has invalid identifier bytes");
        }
        for (int i = 0; i < keyId.length; i++) {
            if (resultBytes[i] != keyId[i]) {
                throw new RuntimeException("Returned KeyIdentifier bytes differ from input");
            }
        }
    }

    private static void testGetWithInvalidNameThrowsIOException() {
        byte[] keyId = new byte[] {10, 20, 30};
        try {
            SubjectKeyIdentifierExtension ext = new SubjectKeyIdentifierExtension(keyId);
            ext.get("invalid_name");
            throw new RuntimeException("Expected IOException not thrown for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type thrown: " + e);
        }
    }
}
