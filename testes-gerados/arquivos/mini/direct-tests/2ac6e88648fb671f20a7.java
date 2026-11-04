/*
 * @test
 * @summary Test sun.security.x509.SubjectAlternativeNameExtension.set method
 * @run main SubjectAlternativeNameExtensionWitupTest
 */

import sun.security.x509.SubjectAlternativeNameExtension;
import sun.security.x509.GeneralNames;
import java.io.IOException;

public class SubjectAlternativeNameExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetWithValidNameAndGeneralNames();
        testSetWithInvalidName();
        testSetWithInvalidObjectType();
    }

    private static void testSetWithValidNameAndGeneralNames() throws Exception {
        GeneralNames gn = new GeneralNames();
        SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension();
        ext.set(SubjectAlternativeNameExtension.SUBJECT_NAME, gn);
        // If no exception, test passed
    }

    private static void testSetWithInvalidName() {
        SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension();
        try {
            ext.set("invalid_name", new GeneralNames());
            throw new RuntimeException("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testSetWithInvalidObjectType() {
        SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension();
        try {
            ext.set(SubjectAlternativeNameExtension.SUBJECT_NAME, "not a GeneralNames instance");
            throw new RuntimeException("Expected IOException for invalid attribute value type");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute value should be of type GeneralNames")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
