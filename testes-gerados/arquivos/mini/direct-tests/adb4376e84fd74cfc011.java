/*
 * @test
 * @summary Test java.util.Formatter(File) constructor behavior
 * @run main FormatterWitupTest
 */

import java.util.Formatter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FormatterWitupTest {
    public static void main(String[] args) throws IOException {
        testFormatterWithValidFile();
        testFormatterWithNonExistentFile();
    }

    private static void testFormatterWithValidFile() throws IOException {
        File tempFile = File.createTempFile("FormatterWitupTest", ".txt");
        tempFile.deleteOnExit();
        Formatter formatter = null;
        try {
            formatter = new Formatter(tempFile);
            // Write something to verify it works
            formatter.format("%s", "test");
            formatter.flush();
        } finally {
            if (formatter != null) {
                formatter.close();
            }
        }
    }

    private static void testFormatterWithNonExistentFile() {
        File nonExistentDir = new File("nonexistent_dir_for_formatter_test");
        File fileInNonExistentDir = new File(nonExistentDir, "file.txt");
        try {
            new Formatter(fileInNonExistentDir);
            throw new RuntimeException("Expected FileNotFoundException was not thrown");
        } catch (FileNotFoundException e) {
            // Expected exception
        }
    }
}
