/*
 * @test
 * @summary Test SubjectAlternativeNameExtension.get(String) method behavior
 * @run main SubjectAlternativeNameExtensionWitupTest
 */

import sun.security.x509.SubjectAlternativeNameExtension;
import sun.security.x509.GeneralNames;
import java.io.IOException;

public class SubjectAlternativeNameExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws Exception {
        GeneralNames gn = new GeneralNames();
        SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension(gn);
        GeneralNames result = ext.get("subject_name");
        if (result == null) {
            throw new RuntimeException("Expected non-null GeneralNames for 'subject_name'");
        }
    }

    private static void testGetWithInvalidName() {
        try {
            GeneralNames gn = new GeneralNames();
            SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension(gn);
            ext.get("invalid_name");
            throw new RuntimeException("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }
}
