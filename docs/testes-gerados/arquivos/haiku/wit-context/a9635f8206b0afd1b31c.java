/*
 * @test
 * @summary Verify ProcessBuilder.Redirect.from(File) rejects null and returns a READ redirect with the expected observable file and toString
 * @run main ProcessBuilderWitupTest
 */

import java.io.File;

public class ProcessBuilderWitupTest {

    public static void main(String[] args) {
        testNullFileThrowsNPE();
        testFromReturnsReadRedirect();
        testToStringContainsFilePath();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullFileThrowsNPE() {
        try {
            ProcessBuilder.Redirect.from((File) null);
            throw new RuntimeException("expected NullPointerException for null file");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testFromReturnsReadRedirect() {
        File f = new File("witup-input.txt");
        ProcessBuilder.Redirect r = ProcessBuilder.Redirect.from(f);
        check(r != null, "from(file) must not return null");
        check(r.type() == ProcessBuilder.Redirect.Type.READ, "type must be READ");
        check(f.equals(r.file()), "file() must return the same file");
    }

    static void testToStringContainsFilePath() {
        File f = new File("witup-output-check.txt");
        ProcessBuilder.Redirect r = ProcessBuilder.Redirect.from(f);
        String expected = "redirect to read from file \"" + f + "\"";
        check(expected.equals(r.toString()), "unexpected toString: " + r.toString());
    }
}
