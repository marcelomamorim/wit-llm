/* @test    @summary Test delete method of SubjectAlternativeNameExtension for exception path    @run main SubjectAlternativeNameExtensionWitupTest */

import java.io.IOException;
import sun.security.x509.SubjectAlternativeNameExtension;

public class SubjectAlternativeNameExtensionWitupTest {

    public static void main(String[] args) {
        testDeleteWithUnrecognizedName();
    }

    public static void testDeleteWithUnrecognizedName() {
        SubjectAlternativeNameExtension extension = null;
        try {
            extension = new SubjectAlternativeNameExtension();
        } catch (IOException e) {
            // Fail the test if construction fails
            throw new RuntimeException("Failed to create extension", e);
        }
        String unrecognizedName = "unknown_name";
        try {
            extension.delete(unrecognizedName);
            // If no exception thrown, test fails
            throw new AssertionError("Expected IOException was not thrown for unrecognized name");
        } catch (IOException e) {
            String message = e.getMessage();
            if (message == null || !message.contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + message, e);
            }
            // Exception as expected, test passes
        }
    }
}
