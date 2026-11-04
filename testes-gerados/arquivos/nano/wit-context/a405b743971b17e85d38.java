/* @test    @summary Test for delete method in SubjectInfoAccessExtension to verify exception handling and behavior.    @run main SubjectInfoAccessExtensionWitupTest */

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import sun.security.x509.SubjectInfoAccessExtension;

public class SubjectInfoAccessExtensionWitupTest {

    public static void main(String[] args) {
        testDeleteWithDescriptions();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithDescriptions() {
        try {
            // Create an instance with a dummy list
            List<sun.security.x509.AccessDescription> descriptions = Collections.emptyList();
            SubjectInfoAccessExtension ext = new SubjectInfoAccessExtension(descriptions);
            // Call delete with "descriptions" (should not throw)
            ext.delete("descriptions");
            System.out.println("testDeleteWithDescriptions passed");
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testDeleteWithDescriptions", e);
        }
    }

    private static void testDeleteWithInvalidName() {
        try {
            List<sun.security.x509.AccessDescription> descriptions = Collections.emptyList();
            SubjectInfoAccessExtension ext = new SubjectInfoAccessExtension(descriptions);
            // Call delete with an invalid name (should throw IOException)
            ext.delete("invalidName");
            throw new RuntimeException("Expected IOException was not thrown");
        } catch (IOException e) {
            String message = e.getMessage();
            if (message != null && message.contains("Attribute name [")) {
                System.out.println("testDeleteWithInvalidName passed");
            } else {
                throw new RuntimeException("IOException message did not match expected", e);
            }
        }
    }
}
