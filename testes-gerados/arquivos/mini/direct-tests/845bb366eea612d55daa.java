/*
 * @test
 * @summary Test java.io.File.getCanonicalPath() behavior including invalid path
 * @run main FileWitupTest
 */

import java.io.File;
import java.io.IOException;

public class FileWitupTest {
    public static void main(String[] args) throws Exception {
        testValidCanonicalPath();
        testInvalidCanonicalPathThrows();
    }

    private static void testValidCanonicalPath() throws IOException {
        // Create a File with a simple valid path
        File f = new File(".");
        String canonical = f.getCanonicalPath();
        if (canonical == null || canonical.isEmpty()) {
            throw new AssertionError("getCanonicalPath() returned null or empty string");
        }
        // The canonical path should be absolute
        if (!new File(canonical).isAbsolute()) {
            throw new AssertionError("getCanonicalPath() did not return an absolute path");
        }
    }

    private static void testInvalidCanonicalPathThrows() {
        // Create a subclass of File to override isInvalid() to return true
        class InvalidFile extends File {
            InvalidFile(String pathname) {
                super(pathname);
            }
            @Override
            boolean isInvalid() {
                return true;
            }
        }

        File invalidFile = new InvalidFile("invalid");
        try {
            invalidFile.getCanonicalPath();
            throw new AssertionError("Expected IOException for invalid file path");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Invalid file path")) {
                throw new AssertionError("IOException message unexpected: " + msg);
            }
        }
    }
}
