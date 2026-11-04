/* @test    @summary Test delete method of SubjectKeyIdentifierExtension for exception path and normal behavior    @run main SubjectKeyIdentifierExtensionWitupTest */

import java.io.IOException;
import sun.security.x509.SubjectKeyIdentifierExtension;

public class SubjectKeyIdentifierExtensionWitupTest {

    public static void main(String[] args) {
        testDeleteWithValidKey();
        testDeleteWithInvalidKey();
    }

    private static void testDeleteWithValidKey() {
        try {
            byte[] keyBytes = {1, 2, 3, 4};
            SubjectKeyIdentifierExtension ext = new SubjectKeyIdentifierExtension(keyBytes);
            ext.delete("key_id");
            System.out.println("testDeleteWithValidKey passed");
        } catch (Exception e) {
            System.err.println("testDeleteWithValidKey failed: " + e);
        }
    }

    private static void testDeleteWithInvalidKey() {
        try {
            byte[] keyBytes = {5, 6, 7, 8};
            SubjectKeyIdentifierExtension ext = new SubjectKeyIdentifierExtension(keyBytes);
            ext.delete("unknown_attribute");
            System.err.println("testDeleteWithInvalidKey failed: no exception thrown");
        } catch (IOException e) {
            if (e.getMessage().contains("Attribute name not recognized")) {
                System.out.println("testDeleteWithInvalidKey passed");
            } else {
                System.err.println("testDeleteWithInvalidKey failed: " + e);
            }
        } catch (Exception e) {
            System.err.println("testDeleteWithInvalidKey failed: " + e);
        }
    }
}
