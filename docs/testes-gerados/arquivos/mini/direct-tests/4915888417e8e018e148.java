/*
 * @test
 * @summary Test java.io.File.createNewFile() behavior
 * @run main FileWitupTest
 */

import java.io.File;
import java.io.IOException;

public class FileWitupTest {
    public static void main(String[] args) throws Exception {
        testCreateNewFileSuccess();
        testCreateNewFileInvalidPath();
    }

    private static void testCreateNewFileSuccess() throws IOException {
        // Create a temporary file and delete it to test createNewFile
        File tempFile = File.createTempFile("FileWitupTest", ".tmp");
        try {
            // Delete the file so createNewFile can create it
            if (!tempFile.delete()) {
                throw new IOException("Failed to delete temp file for test");
            }
            // Now createNewFile should create the file
            boolean created = tempFile.createNewFile();
            if (!created) {
                throw new AssertionError("createNewFile() returned false when file did not exist");
            }
            // createNewFile again should return false because file exists
            boolean createdAgain = tempFile.createNewFile();
            if (createdAgain) {
                throw new AssertionError("createNewFile() returned true when file already exists");
            }
        } finally {
            tempFile.delete();
        }
    }

    private static void testCreateNewFileInvalidPath() {
        // Construct a File with an invalid path to trigger IOException
        // The invalid path is platform dependent; use a path with illegal characters
        // For Windows: characters like \0 or * are invalid
        // For Unix: a null character is invalid but cannot be in a String
        // Use a path with a null character to simulate invalid path
        // Since Java String cannot contain \0, use a path that is very unlikely to be valid

        // Use a path with a character that is invalid on Windows and Unix: "\0"
        // But since Java String cannot contain \0, use a path with a character that is invalid on Windows
        // For example, on Windows, a file name with "<>:""/\|?*" are invalid
        // Use a name with asterisk *

        String invalidPath = "invalid*path";
        File invalidFile = new File(invalidPath);
        try {
            invalidFile.createNewFile();
            throw new AssertionError("Expected IOException for invalid file path");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Invalid file path")) {
                throw new AssertionError("IOException thrown but message unexpected: " + msg);
            }
        } catch (SecurityException se) {
            // SecurityException may be thrown if security manager denies write
            // Acceptable for this test
        }
    }
}
