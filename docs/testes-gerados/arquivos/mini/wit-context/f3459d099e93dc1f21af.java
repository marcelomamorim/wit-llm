/*
 * @test
 * @summary Test ProcessBuilder.to(File) method for null argument and normal behavior
 * @run main ProcessBuilderWitupTest
 */

import java.io.File;
import java.lang.ProcessBuilder.Redirect;

public class ProcessBuilderWitupTest {
    public static void main(String[] args) {
        testToWithNullFile();
        testToWithValidFile();
    }

    private static void testToWithNullFile() {
        try {
            Redirect r = ProcessBuilder.to(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // Expected exception
        }
    }

    private static void testToWithValidFile() {
        File f = new File("testfile.txt");
        Redirect r = ProcessBuilder.to(f);
        if (r == null) {
            throw new RuntimeException("Redirect returned is null");
        }
        if (r.type() != Redirect.Type.WRITE) {
            throw new RuntimeException("Redirect type is not WRITE");
        }
        if (!f.equals(r.file())) {
            throw new RuntimeException("Redirect file does not match input file");
        }
        String expectedString = "redirect to write to file \"" + f + "\"";
        if (!expectedString.equals(r.toString())) {
            throw new RuntimeException("Redirect toString() output mismatch");
        }
        // append() is package-private, cannot test directly
    }
}
