/*
 * @test
 * @summary Test ProcessBuilder.from(File) method behavior
 * @run main ProcessBuilderWitupTest
 */

import java.io.File;
import java.lang.ProcessBuilder.Redirect;

public class ProcessBuilderWitupTest {
    public static void main(String[] args) {
        testFromWithValidFile();
        testFromWithNullFile();
    }

    private static void testFromWithValidFile() {
        File file = new File("testfile.txt");
        Redirect redirect = ProcessBuilder.from(file);
        if (redirect == null) {
            throw new AssertionError("Redirect should not be null");
        }
        if (redirect.type() != Redirect.Type.READ) {
            throw new AssertionError("Redirect type should be READ");
        }
        if (!file.equals(redirect.file())) {
            throw new AssertionError("Redirect file should be the same as input file");
        }
        String expectedString = "redirect to read from file \"" + file + "\"";
        if (!expectedString.equals(redirect.toString())) {
            throw new AssertionError("Redirect toString() mismatch. Expected: " + expectedString + ", got: " + redirect.toString());
        }
    }

    private static void testFromWithNullFile() {
        try {
            ProcessBuilder.from(null);
            throw new AssertionError("Expected NullPointerException for null file argument");
        } catch (NullPointerException npe) {
            // Expected exception
        }
    }
}
