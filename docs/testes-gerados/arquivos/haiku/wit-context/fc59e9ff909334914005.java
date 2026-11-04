/*
 * @test
 * @summary WITUP-generated checks for SubjectAlternativeNameExtension.delete(String)
 * @modules java.base/sun.security.x509
 * @run main SubjectAlternativeNameExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.SubjectAlternativeNameExtension;

public class SubjectAlternativeNameExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteSubjectNameAccepted();
        testDeleteSubjectNameCaseInsensitive();
        testDeleteUnrecognizedNameThrowsIOException();
        testDeleteEmptyNameThrowsIOException();
        testDeleteNullNameThrowsNullPointerException();
    }

    static void testDeleteSubjectNameAccepted() throws Exception {
        SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension();
        ext.delete(SubjectAlternativeNameExtension.SUBJECT_NAME);
    }

    static void testDeleteSubjectNameCaseInsensitive() throws Exception {
        SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension();
        ext.delete("SUBJECT_NAME");
        ext.delete("Subject_Name");
    }

    static void testDeleteUnrecognizedNameThrowsIOException() throws Exception {
        SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension();
        try {
            ext.delete("NotAnAttribute");
            throw new RuntimeException("Expected IOException for unrecognized name");
        } catch (IOException expected) {
            // expected
        }
    }

    static void testDeleteEmptyNameThrowsIOException() throws Exception {
        SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension();
        try {
            ext.delete("");
            throw new RuntimeException("Expected IOException for empty name");
        } catch (IOException expected) {
            // expected
        }
    }

    static void testDeleteNullNameThrowsNullPointerException() throws Exception {
        SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension();
        try {
            ext.delete(null);
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
