/* @test    @summary Test delete method of AuthorityInfoAccessExtension for exception path with invalid attribute name    @run main AuthorityInfoAccessExtensionWitupTest */

import java.io.IOException;
import java.util.*;
import sun.security.x509.AuthorityInfoAccessExtension;

public class AuthorityInfoAccessExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteWithInvalidName();
    }

    public static void testDeleteWithInvalidName() {
        AuthorityInfoAccessExtension ext = createSampleExtension();
        String invalidName = "invalidName";
        try {
            ext.delete(invalidName);
            // Expecting IOException due to unrecognized attribute name
            System.err.println("Failed: Expected IOException was not thrown for name: " + invalidName);
        } catch (IOException e) {
            String message = e.getMessage();
            if (message != null && message.contains("not recognized")) {
                System.out.println("Passed: IOException thrown as expected for name: " + invalidName);
            } else {
                System.err.println("Failed: IOException thrown but message was unexpected: " + message);
            }
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception type: " + e);
        }
    }

    private static AuthorityInfoAccessExtension createSampleExtension() {
        // Create a dummy extension with empty accessDescriptions for testing
        List<AuthorityInfoAccessExtension.AccessDescription> list = new ArrayList<>();
        try {
            return new AuthorityInfoAccessExtension(list);
        } catch (IOException e) {
            // Should not happen with empty list
            throw new RuntimeException(e);
        }
    }
}
