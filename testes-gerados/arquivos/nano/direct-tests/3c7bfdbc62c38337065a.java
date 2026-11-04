/*
 * @test
 * @summary Test constructor ZipFile(String name) with valid and invalid file names
 * @run main ZipFileWitupTest
 */

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipFile;

public class ZipFileWitupTest {

    public static void main(String[] args) {
        testValidFile();
        testInvalidFile();
    }

    static void testValidFile() {
        String validFileName = "test.zip";
        try {
            // Create a dummy zip file for testing
            File file = new File(validFileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            try (ZipFile zip = new ZipFile(validFileName)) {
                System.out.println("Successfully opened zip file: " + validFileName);
            }
        } catch (IOException e) {
            System.err.println("Failed to open valid zip file: " + e.getMessage());
        }
    }

    static void testInvalidFile() {
        String invalidFileName = "nonexistent.zip";
        try {
            ZipFile zip = new ZipFile(invalidFileName);
            System.err.println("Unexpected success opening nonexistent file.");
            zip.close();
        } catch (IOException e) {
            System.out.println("Correctly failed to open nonexistent file: " + e.getMessage());
        }
    }
}
