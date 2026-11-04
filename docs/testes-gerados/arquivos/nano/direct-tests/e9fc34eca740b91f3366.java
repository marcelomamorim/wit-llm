/* @test    @summary Test get method of SubjectKeyIdentifierExtension for valid and invalid attribute names    @run main SubjectKeyIdentifierExtensionWitupTest */

import sun.security.x509.SubjectKeyIdentifierExtension;
import sun.security.util.KeyIdentifier;
import java.io.IOException;

public class SubjectKeyIdentifierExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    static void testGetWithValidName() throws IOException {
        byte[] keyBytes = {1, 2, 3, 4, 5};
        SubjectKeyIdentifierExtension ext = new SubjectKeyIdentifierExtension(keyBytes);
        KeyIdentifier result = ext.get("key_id");
        assert result != null : "Expected non-null KeyIdentifier";
        byte[] resultBytes = result.getIdentifier();
        assert java.util.Arrays.equals(resultBytes, keyBytes) : "KeyIdentifier bytes do not match";
    }

    static void testGetWithInvalidName() {
        byte[] keyBytes = {1, 2, 3, 4, 5};
        SubjectKeyIdentifierExtension ext;
        try {
            ext = new SubjectKeyIdentifierExtension(keyBytes);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create extension", e);
        }
        try {
            ext.get("invalid_name");
            assert false : "Expected IOException for unrecognized attribute name";
        } catch (IOException e) {
            assert e.getMessage().contains("Attribute name not recognized") : "Unexpected exception message: " + e.getMessage();
        }
    }
}
