/* @test    @summary Test for SubjectAlternativeNameExtension.get(String) method    @run main SubjectAlternativeNameExtensionWitupTest */

import sun.security.x509.SubjectAlternativeNameExtension;
import sun.security.util.GeneralNames;
import java.io.IOException;

public class SubjectAlternativeNameExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws IOException {
        GeneralNames genNames = new GeneralNames();
        SubjectAlternativeNameExtension extension = new SubjectAlternativeNameExtension(genNames);
        GeneralNames result = extension.get("subject_name");
        if (result != genNames) {
            throw new AssertionError("Expected to retrieve the same GeneralNames instance.");
        }
    }

    private static void testGetWithInvalidName() {
        GeneralNames genNames = new GeneralNames();
        SubjectAlternativeNameExtension extension = new SubjectAlternativeNameExtension(genNames);
        try {
            extension.get("invalid_name");
            throw new AssertionError("Expected IOException for unrecognized attribute name.");
        } catch (IOException e) {
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
