/*
 * @test
 * @summary Verify SubjectAlternativeNameExtension.delete accepts subject_name case-insensitively and rejects other names
 * @modules java.base/sun.security.x509
 * @run main SubjectAlternativeNameExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.SubjectAlternativeNameExtension;

public class SubjectAlternativeNameExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testSubjectNameAccepted();
        testSubjectNameCaseInsensitive();
        testUnrecognizedNameThrows();
        testSimilarNamesThrow();
        testRepeatedDeleteIsStable();
    }

    static void testSubjectNameAccepted() throws Exception {
        SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension();
        ext.delete(SubjectAlternativeNameExtension.SUBJECT_NAME);
    }

    static void testSubjectNameCaseInsensitive() throws Exception {
        SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension();
        ext.delete("SUBJECT_NAME");
        ext.delete("Subject_Name");
        ext.delete("subject_NAME");
    }

    static void testUnrecognizedNameThrows() throws Exception {
        SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension();
        expectUnrecognized(ext, "bogus");
    }

    static void testSimilarNamesThrow() throws Exception {
        SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension();
        expectUnrecognized(ext, "subject");
        expectUnrecognized(ext, "subject-name");
        expectUnrecognized(ext, "SubjectAlternativeName");
        expectUnrecognized(ext, "");
    }

    static void testRepeatedDeleteIsStable() throws Exception {
        SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension();
        ext.delete(SubjectAlternativeNameExtension.SUBJECT_NAME);
        ext.delete(SubjectAlternativeNameExtension.SUBJECT_NAME);
        expectUnrecognized(ext, "other");
        ext.delete(SubjectAlternativeNameExtension.SUBJECT_NAME);
    }

    static void expectUnrecognized(SubjectAlternativeNameExtension ext, String name) {
        try {
            ext.delete(name);
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("not recognized")) {
                throw new RuntimeException("Unexpected message for " + name + ": " + msg);
            }
            return;
        }
        throw new RuntimeException("Expected IOException for name: " + name);
    }
}
