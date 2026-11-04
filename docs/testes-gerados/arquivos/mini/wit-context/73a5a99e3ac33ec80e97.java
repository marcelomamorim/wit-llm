/*
 * @test
 * @summary Test ProcessBuilder.appendTo(File) for null argument and normal usage
 * @run main ProcessBuilderWitupTest
 */

import java.io.File;
import java.lang.ProcessBuilder.Redirect;

public class ProcessBuilderWitupTest {
    public static void main(String[] args) {
        testAppendToNullFile();
        testAppendToValidFile();
    }

    private static void testAppendToNullFile() {
        try {
            Redirect r = ProcessBuilder.appendTo(null);
            throw new RuntimeException("Expected NullPointerException for null file argument");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testAppendToValidFile() {
        File f = new File("testfile.txt");
        Redirect r = ProcessBuilder.appendTo(f);
        if (r == null) {
            throw new RuntimeException("Redirect returned is null");
        }
        if (r.type() != Redirect.Type.APPEND) {
            throw new RuntimeException("Redirect type is not APPEND");
        }
        if (!f.equals(r.file())) {
            throw new RuntimeException("Redirect file does not match input file");
        }
        String s = r.toString();
        if (s == null || !s.contains(f.toString())) {
            throw new RuntimeException("Redirect toString does not contain file name");
        }
    }
}
