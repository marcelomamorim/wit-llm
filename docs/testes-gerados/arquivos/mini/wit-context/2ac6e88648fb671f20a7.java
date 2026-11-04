/*
 * @test
 * @summary Test SubjectAlternativeNameExtension.set method for valid and invalid inputs
 * @run main SubjectAlternativeNameExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.SubjectAlternativeNameExtension;
import sun.security.x509.GeneralNames;

public class SubjectAlternativeNameExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetWithValidNameAndGeneralNames();
        testSetWithInvalidNameThrowsIOException();
        testSetWithInvalidObjectTypeThrowsIOException();
    }

    private static void testSetWithValidNameAndGeneralNames() throws IOException {
        GeneralNames gn = new GeneralNames();
        SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension();
        ext.set("subject_name", gn);
        // No exception means pass
    }

    private static void testSetWithInvalidNameThrowsIOException() {
        SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension();
        try {
            ext.set("invalid_name", new GeneralNames());
            throw new RuntimeException("Expected IOException not thrown for invalid name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }

    private static void testSetWithInvalidObjectTypeThrowsIOException() {
        SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension();
        try {
            ext.set("subject_name", new Object());
            throw new RuntimeException("Expected IOException not thrown for invalid object type");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute value should be of type GeneralNames")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }
}
