/*
 * @test
 * @summary Test java.util.Formatter(String fileName) constructor
 * @run main FormatterWitupTest
 */

import java.util.Formatter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;

public class FormatterWitupTest {
    public static void main(String[] args) throws Exception {
        testValidFileName();
        testFileNotFound();
    }

    private static void testValidFileName() throws Exception {
        File tempFile = File.createTempFile("FormatterWitupTest", ".txt");
        tempFile.deleteOnExit();
        Formatter formatter = null;
        try {
            formatter = new Formatter(tempFile.getAbsolutePath());
            // Check that locale is default FORMAT locale
            Locale loc = formatter.locale();
            if (!loc.equals(Locale.getDefault(Locale.Category.FORMAT))) {
                throw new RuntimeException("Locale mismatch: expected " + Locale.getDefault(Locale.Category.FORMAT) + ", got " + loc);
            }
            // Check that formatter is not closed immediately
            formatter.format("%s", "test");
        } finally {
            if (formatter != null) {
                formatter.close();
            }
        }
    }

    private static void testFileNotFound() {
        String invalidPath = "/path/to/nonexistent/directory/file.txt";
        try {
            new Formatter(invalidPath);
            throw new RuntimeException("Expected FileNotFoundException for invalid path");
        } catch (FileNotFoundException e) {
            // Expected exception
        }
    }
}
