/*
 * @test
 * @summary Verifies error handling of SubjectAlternativeNameExtension.set for invalid names and values
 * @modules java.base/sun.security.x509
 * @run main SubjectAlternativeNameExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.SubjectAlternativeNameExtension;

public class SubjectAlternativeNameExtensionWitupTest {

    private static void expectIOException(SubjectAlternativeNameExtension ext,
                                          String name, Object value,
                                          String label) {
        try {
            ext.set(name, value);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException: " + label);
    }

    public static void main(String[] args) throws Exception {
        SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension();

        // Unrecognized attribute name must be rejected.
        expectIOException(ext, "unknownName", null, "unknown name with null value");

        // Recognized name with a value that is not GeneralNames must be rejected.
        expectIOException(ext, SubjectAlternativeNameExtension.SUBJECT_NAME,
                          Integer.valueOf(1), "subject_name with Integer");

        // Recognized name with null value must be rejected (null is not instanceof GeneralNames).
        expectIOException(ext, SubjectAlternativeNameExtension.SUBJECT_NAME,
                          null, "subject_name with null");

        // Name matching is case-insensitive; a wrong type under an uppercase name is still rejected.
        expectIOException(ext, "SUBJECT_NAME", "not-general-names",
                          "SUBJECT_NAME uppercase with String");

        // A name that only partially matches must be rejected.
        expectIOException(ext, "subject_name_extra", null, "partial name match");
    }
}
