/*
 * @test
 * @summary Test ProcessBuilder.from(File) method for null argument and normal usage
 * @run main ProcessBuilderWitupTest
 */

import java.io.File;
import java.lang.ProcessBuilder.Redirect;

public class ProcessBuilderWitupTest {
    public static void main(String[] args) {
        testFromWithNullFile();
        testFromWithValidFile();
    }

    private static void testFromWithNullFile() {
        try {
            Redirect r = ProcessBuilder.from(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // Expected exception
        }
    }

    private static void testFromWithValidFile() {
        File f = new File("testfile");
        Redirect r = ProcessBuilder.from(f);
        if (r == null) {
            throw new RuntimeException("Redirect returned is null");
        }
        if (r.type() != Redirect.Type.READ) {
            throw new RuntimeException("Redirect type is not READ");
        }
        if (!f.equals(r.file())) {
            throw new RuntimeException("Redirect file does not match input file");
        }
        String expectedString = "redirect to read from file \"" + f + "\"";
        if (!expectedString.equals(r.toString())) {
            throw new RuntimeException("Redirect toString() does not match expected string");
        }
    }
}
