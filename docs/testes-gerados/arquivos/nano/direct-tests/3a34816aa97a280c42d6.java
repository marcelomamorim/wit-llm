/* @test    @summary Test get method of SubjectInfoAccessExtension for valid attribute name    @run main SubjectInfoAccessExtensionWitupTest */

import sun.security.x509.SubjectInfoAccessExtension;
import java.io.IOException;
import java.util.List;

public class SubjectInfoAccessExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws IOException {
        // Create a dummy accessDescriptions list
        List<sun.security.x509.AccessDescription> descriptions = java.util.Collections.emptyList();
        // Instantiate SubjectInfoAccessExtension with empty list
        SubjectInfoAccessExtension extension = new SubjectInfoAccessExtension(descriptions);
        // Call get with valid attribute name
        List<sun.security.x509.AccessDescription> result = extension.get("descriptions");
        // Verify that the returned list is the same as the one set
        if (result != descriptions) {
            throw new AssertionError("Expected the same list to be returned");
        }
    }

    private static void testGetWithInvalidName() {
        // Create a dummy accessDescriptions list
        List<sun.security.x509.AccessDescription> descriptions = java.util.Collections.emptyList();
        try {
            // Instantiate SubjectInfoAccessExtension with empty list
            SubjectInfoAccessExtension extension = new SubjectInfoAccessExtension(descriptions);
            // Call get with invalid attribute name
            extension.get("invalidName");
            throw new AssertionError("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            // Expected exception
            String message = e.getMessage();
            if (message == null || !message.contains("not recognized")) {
                throw new AssertionError("Unexpected exception message: " + message, e);
            }
        }
    }
}
