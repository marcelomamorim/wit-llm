/* @test    @summary Test Formatter constructor with filename    @run main FormatterWitupTest */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.OutputStreamWriter;
import java.util.Locale;

public class FormatterWitupTest {

    public static void main(String[] args) {
        testFormatterConstructorWithFileName();
    }

    public static void testFormatterConstructorWithFileName() {
        String tempFileName = "test_output.txt";
        File tempFile = new File(tempFileName);
        try {
            // Ensure the file does not exist before test
            if (tempFile.exists()) {
                tempFile.delete();
            }
            // Instantiate Formatter with filename
            java.util.Formatter formatter = new java.util.Formatter(tempFileName);
            // Write a test string
            formatter.format("Test message: %d", 123);
            formatter.close();
            // Verify file creation and content
            if (!tempFile.exists()) {
                throw new AssertionError("File was not created.");
            }
            String content = new String(java.nio.file.Files.readAllBytes(tempFile.toPath()), "UTF-8");
            if (!content.contains("Test message: 123")) {
                throw new AssertionError("Content mismatch: " + content);
            }
        } catch (FileNotFoundException e) {
            throw new AssertionError("FileNotFoundException thrown unexpectedly", e);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e, e);
        } finally {
            // Cleanup
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }
}
