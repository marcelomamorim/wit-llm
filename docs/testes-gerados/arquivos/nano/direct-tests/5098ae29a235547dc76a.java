/* @test    @summary Test constructor of JarFile with a valid filename string    @run main JarFileWitupTest */

import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;

public class JarFileWitupTest {

    public static void main(String[] args) throws IOException {
        testValidFileName();
        testInvalidFileName();
    }

    static void testValidFileName() {
        String tempFileName = "test.jar";
        File tempFile = new File(tempFileName);
        try {
            if (!tempFile.exists()) {
                boolean created = tempFile.createNewFile();
                if (!created) {
                    System.err.println("Failed to create temporary test file.");
                    return;
                }
            }
            try (JarFile jar = new JarFile(tempFileName)) {
                System.out.println("Successfully opened JarFile with filename: " + tempFileName);
            }
        } catch (IOException e) {
            System.err.println("IOException during testValidFileName: " + e.getMessage());
        } finally {
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    static void testInvalidFileName() {
        String invalidFileName = "nonexistent.jar";
        try {
            try (JarFile jar = new JarFile(invalidFileName)) {
                System.err.println("Unexpected success opening nonexistent file.");
            }
        } catch (IOException e) {
            System.out.println("Correctly caught IOException for invalid filename: " + e.getMessage());
        }
    }
}
