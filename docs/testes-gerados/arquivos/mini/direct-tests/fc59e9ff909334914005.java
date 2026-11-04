/*
 * @test
 * @summary Test SubjectAlternativeNameExtension.delete(String) method
 * @run main SubjectAlternativeNameExtensionWitupTest
 */

import sun.security.x509.SubjectAlternativeNameExtension;
import sun.security.x509.GeneralNames;
import sun.security.x509.PKIXExtensions;
import java.io.IOException;

public class SubjectAlternativeNameExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteWithValidName();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithValidName() throws Exception {
        // Create a SubjectAlternativeNameExtension with a non-empty GeneralNames
        GeneralNames names = new GeneralNames();
        SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension(Boolean.FALSE, names);

        // Delete with SUBJECT_NAME should set names to null and extensionValue to null
        ext.delete(SubjectAlternativeNameExtension.SUBJECT_NAME);

        // After delete, names should be null
        if (ext.names != null) {
            throw new RuntimeException("Failed: names should be null after delete with SUBJECT_NAME");
        }

        // extensionValue should be null after encodeThis sets it
        if (ext.extensionValue != null) {
            throw new RuntimeException("Failed: extensionValue should be null after delete with SUBJECT_NAME");
        }
    }

    private static void testDeleteWithInvalidName() {
        try {
            GeneralNames names = new GeneralNames();
            SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension(Boolean.FALSE, names);
            ext.delete("invalid_name");
            throw new RuntimeException("Failed: IOException expected for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("Failed: IOException message unexpected: " + msg);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed: Unexpected exception type: " + e);
        }
    }
}
