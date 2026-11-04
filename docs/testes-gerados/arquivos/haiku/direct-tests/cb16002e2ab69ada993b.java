/*
 * @test
 * @summary Tests SubjectAlternativeNameExtension.get(String) name matching and return values
 * @modules java.base/sun.security.x509
 * @run main SubjectAlternativeNameExtensionWitupTest
 */

import java.io.IOException;

public class SubjectAlternativeNameExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testReturnsStoredNamesForSubjectName();
        testCaseInsensitiveMatch();
        testNullNamesReturnsNull();
        testUnrecognizedNameThrowsIOException();
        testNullNameThrowsNullPointerException();
    }

    static void testReturnsStoredNamesForSubjectName() throws IOException {
        GeneralNames names = new GeneralNames();
        SubjectAlternativeNameExtension ext =
                new SubjectAlternativeNameExtension(Boolean.FALSE, names);
        GeneralNames result = ext.get(SubjectAlternativeNameExtension.SUBJECT_NAME);
        if (result != names) {
            throw new RuntimeException("get(SUBJECT_NAME) must return the stored GeneralNames instance");
        }
    }

    static void testCaseInsensitiveMatch() throws IOException {
        GeneralNames names = new GeneralNames();
        SubjectAlternativeNameExtension ext =
                new SubjectAlternativeNameExtension(Boolean.FALSE, names);
        GeneralNames upper = ext.get("SUBJECT_NAME");
        GeneralNames mixed = ext.get("Subject_Name");
        if (upper != names || mixed != names) {
            throw new RuntimeException("get must match subject_name case-insensitively");
        }
    }

    static void testNullNamesReturnsNull() throws IOException {
        SubjectAlternativeNameExtension ext =
                new SubjectAlternativeNameExtension(Boolean.FALSE, (GeneralNames) null);
        if (ext.get(SubjectAlternativeNameExtension.SUBJECT_NAME) != null) {
            throw new RuntimeException("get must return null when no names were set");
        }
    }

    static void testUnrecognizedNameThrowsIOException() throws IOException {
        SubjectAlternativeNameExtension ext =
                new SubjectAlternativeNameExtension(Boolean.FALSE, new GeneralNames());
        try {
            ext.get("unknown_attribute");
            throw new RuntimeException("Expected IOException for unrecognized attribute name");
        } catch (IOException expected) {
            // expected
        }
    }

    static void testNullNameThrowsNullPointerException() throws IOException {
        SubjectAlternativeNameExtension ext =
                new SubjectAlternativeNameExtension(Boolean.FALSE, new GeneralNames());
        try {
            ext.get(null);
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
