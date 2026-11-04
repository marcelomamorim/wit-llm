/*
 * @test
 * @summary Test SubjectAlternativeNameExtension.delete(String) behavior
 * @run main SubjectAlternativeNameExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.SubjectAlternativeNameExtension;
import sun.security.x509.GeneralNames;

public class SubjectAlternativeNameExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteWithSubjectName();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithSubjectName() throws IOException {
        GeneralNames gn = new GeneralNames();
        SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension(gn);
        ext.delete("subject_name");
        // After delete with "subject_name", names should be null and extensionValue null
        // We check that no exception is thrown and extensionValue is null
        if (ext.extensionValue != null) {
            throw new RuntimeException("extensionValue should be null after delete with subject_name");
        }
    }

    private static void testDeleteWithInvalidName() {
        try {
            GeneralNames gn = new GeneralNames();
            SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension(gn);
            ext.delete("invalid_name");
            throw new RuntimeException("Expected IOException not thrown for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }
}
