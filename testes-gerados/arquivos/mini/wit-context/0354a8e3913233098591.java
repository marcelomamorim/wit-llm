/*
 * @test
 * @summary Test java.util.Formatter(String fileName) constructor for normal and exceptional cases
 * @run main FormatterWitupTest
 */

import java.util.Formatter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.io.BufferedReader;
import java.io.FileReader;

public class FormatterWitupTest {
    public static void main(String[] args) throws Exception {
        testValidFileNameConstructor();
        testNullFileNameThrows();
    }

    private static void testValidFileNameConstructor() throws Exception {
        // Create a temporary file
        File tempFile = File.createTempFile("FormatterWitupTest", ".txt");
        tempFile.deleteOnExit();

        // Use Formatter(String fileName) constructor
        Formatter formatter = null;
        try {
            formatter = new Formatter(tempFile.getAbsolutePath());
            // Write formatted output
            formatter.format(Locale.US, "%d %s", 42, "test");
            formatter.flush();
        } finally {
            if (formatter != null) {
                formatter.close();
            }
        }

        // Verify file content
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(tempFile));
            String line = reader.readLine();
            if (line == null || !line.contains("42 test")) {
                throw new AssertionError("Formatted output not found in file");
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    private static void testNullFileNameThrows() {
        try {
            new Formatter((String) null);
            throw new AssertionError("Expected NullPointerException for null fileName");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (FileNotFoundException e) {
            // Not expected for null fileName, fail
            throw new AssertionError("Unexpected FileNotFoundException for null fileName", e);
        }
    }
}
