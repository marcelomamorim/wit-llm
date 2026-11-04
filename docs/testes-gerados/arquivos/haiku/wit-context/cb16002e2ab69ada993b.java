/*
 * @test
 * @summary Verify SubjectAlternativeNameExtension.get(String) returns names for subject_name and throws IOException for unrecognized attribute names
 * @modules java.base/sun.security.x509
 * @run main SubjectAlternativeNameExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.GeneralNames;
import sun.security.x509.SubjectAlternativeNameExtension;

public class SubjectAlternativeNameExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        SubjectAlternativeNameExtension ext =
                new SubjectAlternativeNameExtension(Boolean.FALSE, (GeneralNames) null);

        if (ext.get(SubjectAlternativeNameExtension.SUBJECT_NAME) != null) {
            throw new RuntimeException("expected null names for subject_name");
        }

        if (ext.get("SUBJECT_NAME") != null) {
            throw new RuntimeException("expected null names for SUBJECT_NAME (case-insensitive)");
        }

        expectIOException(ext, "unknown");
        expectIOException(ext, "");
        expectIOException(ext, "subject_names");
        expectIOException(ext, SubjectAlternativeNameExtension.NAME);
    }

    private static void expectIOException(SubjectAlternativeNameExtension ext, String name) {
        try {
            ext.get(name);
            throw new RuntimeException("expected IOException for name: " + name);
        } catch (IOException expected) {
            // expected
        }
    }
}
