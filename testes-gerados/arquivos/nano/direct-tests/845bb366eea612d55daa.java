/*
 * @test
 * @summary Test getCanonicalPath() method of java.io.File for valid and invalid paths
 * @run main FileWitupTest
 */

import java.io.File;
import java.io.IOException;

public class FileWitupTest {

    public static void main(String[] args) {
        testValidCanonicalPath();
        testInvalidCanonicalPath();
    }

    static void testValidCanonicalPath() {
        try {
            File file = new File(System.getProperty("user.dir"));
            String canonicalPath = file.getCanonicalPath();
            System.out.println("Canonical path: " + canonicalPath);
            assert canonicalPath != null && !canonicalPath.isEmpty() : "Canonical path should not be null or empty";
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testValidCanonicalPath", e);
        }
    }

    static void testInvalidCanonicalPath() {
        try {
            File invalidFile = new File("/invalid/path/that/does/not/exist");
            invalidFile.getCanonicalPath();
            // If no exception, test fails
            System.err.println("Expected IOException was not thrown for invalid path.");
            assert false : "Expected IOException was not thrown";
        } catch (IOException e) {
            System.out.println("Caught expected IOException for invalid path: " + e.getMessage());
        }
    }
}
