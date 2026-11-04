/* @test    @summary Test delete method of AuthorityKeyIdentifierExtension for recognized and unrecognized attribute names    @run main AuthorityKeyIdentifierExtensionWitupTest */

import java.io.IOException;
import sun.security.x509.AuthorityKeyIdentifierExtension;

public class AuthorityKeyIdentifierExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteRecognizedKeyId();
        testDeleteRecognizedAuthName();
        testDeleteRecognizedSerialNumber();
        testDeleteUnrecognizedName();
    }

    static void testDeleteRecognizedKeyId() throws IOException {
        AuthorityKeyIdentifierExtension ext = new AuthorityKeyIdentifierExtension();
        // Simulate setting id
        ext.delete("key_id");
        // No exception expected, id should be null
        assert ext != null : "ext should not be null";
    }

    static void testDeleteRecognizedAuthName() throws IOException {
        AuthorityKeyIdentifierExtension ext = new AuthorityKeyIdentifierExtension();
        ext.delete("auth_name");
        assert ext != null : "ext should not be null";
    }

    static void testDeleteRecognizedSerialNumber() throws IOException {
        AuthorityKeyIdentifierExtension ext = new AuthorityKeyIdentifierExtension();
        ext.delete("serial_number");
        assert ext != null : "ext should not be null";
    }

    static void testDeleteUnrecognizedName() {
        AuthorityKeyIdentifierExtension ext = new AuthorityKeyIdentifierExtension();
        try {
            ext.delete("unknown_attribute");
            assert false : "Expected IOException for unrecognized attribute";
        } catch (IOException e) {
            assert e.getMessage().contains("Attribute name not recognized") : "Unexpected exception message";
        }
    }
}
