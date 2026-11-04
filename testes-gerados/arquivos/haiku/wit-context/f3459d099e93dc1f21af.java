/*
 * @test
 * @summary Witup-derived checks for ProcessBuilder.Redirect.to(File): null argument rejection and observable write-redirect behavior
 * @run main ProcessBuilderWitupTest
 */

import java.io.File;

public class ProcessBuilderWitupTest {

    public static void main(String[] args) {
        testNullFileThrowsNullPointerException();
        testWriteRedirectType();
        testWriteRedirectFileIsSameReference();
        testWriteRedirectToString();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullFileThrowsNullPointerException() {
        boolean thrown = false;
        try {
            ProcessBuilder.Redirect.to(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "Redirect.to(null) must throw NullPointerException");
    }

    static void testWriteRedirectType() {
        File f = new File("witup_out.txt");
        ProcessBuilder.Redirect r = ProcessBuilder.Redirect.to(f);
        check(r.type() == ProcessBuilder.Redirect.Type.WRITE,
              "type() must be Type.WRITE");
    }

    static void testWriteRedirectFileIsSameReference() {
        File f = new File("witup_out.txt");
        ProcessBuilder.Redirect r = ProcessBuilder.Redirect.to(f);
        check(r.file() == f, "file() must return the File passed to to()");
    }

    static void testWriteRedirectToString() {
        File f = new File("witup_out.txt");
        ProcessBuilder.Redirect r = ProcessBuilder.Redirect.to(f);
        String expected = "redirect to write to file \"" + f + "\"";
        check(expected.equals(r.toString()),
              "toString() mismatch: " + r.toString());
    }
}
