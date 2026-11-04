/*
 * @test
 * @summary Test java.util.Formatter(File) constructor for normal and exceptional cases
 * @run main FormatterWitupTest
 */

import java.util.Formatter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FormatterWitupTest {
    public static void main(String[] args) throws IOException {
        testValidFileConstructor();
        testNullFileConstructor();
    }

    private static void testValidFileConstructor() throws IOException {
        File tempFile = null;
        Formatter formatter = null;
        try {
            tempFile = File.createTempFile("FormatterWitupTest", ".txt");
            tempFile.deleteOnExit();
            formatter = new Formatter(tempFile);
            if (formatter == null) {
                throw new AssertionError("Formatter constructor returned null");
            }
            // Write something to verify it works
            formatter.format("%s", "test");
            formatter.flush();
        } finally {
            if (formatter != null) {
                formatter.close();
            }
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    private static void testNullFileConstructor() {
        try {
            new Formatter((File) null);
            throw new AssertionError("Expected NullPointerException for null File argument");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (FileNotFoundException e) {
            throw new AssertionError("Unexpected FileNotFoundException for null File argument");
        }
    }
}
