/* @test    @summary Test for SubjectInfoAccessExtension.get(String) method behavior.    @run main SubjectInfoAccessExtensionWitupTest */

import sun.security.x509.SubjectInfoAccessExtension;
import sun.security.x509.AccessDescription;
import java.io.IOException;
import java.util.List;

public class SubjectInfoAccessExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetWithDescriptions();
        testGetWithInvalidName();
    }

    private static void testGetWithDescriptions() throws IOException {
        // Setup: create an instance with a list of AccessDescription
        List<AccessDescription> descriptions = java.util.Collections.emptyList();
        SubjectInfoAccessExtension ext = new SubjectInfoAccessExtension(descriptions);
        // Invoke get with "descriptions" (case-insensitive)
        List<AccessDescription> result = ext.get("descriptions");
        // Verify: result should be the same list
        if (result != descriptions) {
            throw new AssertionError("Expected descriptions list to be returned.");
        }
    }

    private static void testGetWithInvalidName() {
        try {
            List<AccessDescription> descriptions = java.util.Collections.emptyList();
            SubjectInfoAccessExtension ext = new SubjectInfoAccessExtension(descriptions);
            // Call get with a name that is not "descriptions"
            ext.get("invalidName");
            throw new AssertionError("Expected IOException for invalid name.");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("not recognized")) {
                throw new AssertionError("Unexpected exception message: " + msg, e);
            }
        }
    }
}
