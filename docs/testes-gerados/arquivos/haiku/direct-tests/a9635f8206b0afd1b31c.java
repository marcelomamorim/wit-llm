/*
 * @test
 * @summary Tests ProcessBuilder.Redirect.from(File) observable behavior
 * @run main ProcessBuilderWitupTest
 */

import java.io.File;
import java.lang.ProcessBuilder.Redirect;

public class ProcessBuilderWitupTest {

    public static void main(String[] args) {
        testNullFileThrowsNullPointerException();
        testRedirectTypeIsRead();
        testFileAccessorReturnsSameFile();
        testToStringDescribesReadRedirect();
        testDistinctFilesProduceDistinctDescriptions();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullFileThrowsNullPointerException() {
        boolean thrown = false;
        try {
            Redirect.from(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "from(null) should throw NullPointerException");
    }

    static void testRedirectTypeIsRead() {
        Redirect r = Redirect.from(new File("sample.txt"));
        check(r.type() == Redirect.Type.READ, "type() should be Type.READ");
    }

    static void testFileAccessorReturnsSameFile() {
        File f = new File("sample.txt");
        Redirect r = Redirect.from(f);
        check(r.file() == f, "file() should return the same File instance");
    }

    static void testToStringDescribesReadRedirect() {
        File f = new File("sample.txt");
        Redirect r = Redirect.from(f);
        String expected = "redirect to read from file \"" + f + "\"";
        check(expected.equals(r.toString()),
              "toString() mismatch: " + r.toString());
    }

    static void testDistinctFilesProduceDistinctDescriptions() {
        Redirect a = Redirect.from(new File("a.txt"));
        Redirect b = Redirect.from(new File("b.txt"));
        check(!a.toString().equals(b.toString()),
              "different files should yield different toString() values");
    }
}
