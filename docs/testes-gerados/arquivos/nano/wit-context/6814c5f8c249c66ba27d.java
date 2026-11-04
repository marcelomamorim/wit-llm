/* @test    @summary Test for AuthorityKeyIdentifierExtension.get(String) method handling known and unknown attribute names    @run main AuthorityKeyIdentifierExtensionWitupTest */

import sun.security.x509.AuthorityKeyIdentifierExtension;
import java.io.IOException;

public class AuthorityKeyIdentifierExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetWithKeyId();
        testGetWithAuthName();
        testGetWithSerialNumber();
        testGetWithUnknownAttribute();
    }

    private static void testGetWithKeyId() throws IOException {
        AuthorityKeyIdentifierExtension ext = createSampleExtension();
        Object result = ext.get("x509.info.extensions.AuthorityKeyIdentifier");
        if (result == null) {
            throw new AssertionError("Expected non-null result for key_id");
        }
    }

    private static void testGetWithAuthName() throws IOException {
        AuthorityKeyIdentifierExtension ext = createSampleExtension();
        Object result = ext.get("auth_name");
        if (result == null) {
            throw new AssertionError("Expected non-null result for auth_name");
        }
    }

    private static void testGetWithSerialNumber() throws IOException {
        AuthorityKeyIdentifierExtension ext = createSampleExtension();
        Object result = ext.get("serial_number");
        if (result == null) {
            throw new AssertionError("Expected non-null result for serial_number");
        }
    }

    private static void testGetWithUnknownAttribute() {
        AuthorityKeyIdentifierExtension ext = createSampleExtension();
        try {
            ext.get("unknown_attribute");
            throw new AssertionError("Expected IOException for unknown attribute");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }

    private static AuthorityKeyIdentifierExtension createSampleExtension() {
        // Creating a minimal sample extension with null fields for testing
        return new AuthorityKeyIdentifierExtension(
            Boolean.FALSE, null
        );
    }
}
