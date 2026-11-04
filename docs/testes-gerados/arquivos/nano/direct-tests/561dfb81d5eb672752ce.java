/* @test    @summary Test the set method of SubjectKeyIdentifierExtension for valid and invalid inputs    @run main SubjectKeyIdentifierExtensionWitupTest */

import java.io.IOException;
import sun.security.x509.SubjectKeyIdentifierExtension;
import sun.security.util.KeyIdentifier;

public class SubjectKeyIdentifierExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetWithValidKeyIdentifier();
        testSetWithInvalidObject();
        testSetWithUnrecognizedName();
    }

    static void testSetWithValidKeyIdentifier() throws IOException {
        SubjectKeyIdentifierExtension ext = new SubjectKeyIdentifierExtension((byte[]) null);
        KeyIdentifier keyId = new KeyIdentifier(new byte[] {1, 2, 3, 4});
        ext.set("key_id", keyId);
        System.out.println("testSetWithValidKeyIdentifier passed");
    }

    static void testSetWithInvalidObject() {
        try {
            SubjectKeyIdentifierExtension ext = new SubjectKeyIdentifierExtension((byte[]) null);
            ext.set("key_id", "invalidObject");
            System.err.println("testSetWithInvalidObject failed: exception not thrown");
        } catch (IOException e) {
            System.out.println("testSetWithInvalidObject passed: " + e.getMessage());
        }
    }

    static void testSetWithUnrecognizedName() {
        try {
            SubjectKeyIdentifierExtension ext = new SubjectKeyIdentifierExtension((byte[]) null);
            ext.set("unknown_name", new KeyIdentifier(new byte[] {1}));
            System.err.println("testSetWithUnrecognizedName failed: exception not thrown");
        } catch (IOException e) {
            System.out.println("testSetWithUnrecognizedName passed: " + e.getMessage());
        }
    }
}
