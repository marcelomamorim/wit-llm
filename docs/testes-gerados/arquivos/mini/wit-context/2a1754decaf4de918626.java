/*
 * @test
 * @summary Test java.io.File constructor with String pathname for NPE and valid input
 * @run main FileWitupTest
 */

import java.io.File;

public class FileWitupTest {
    public static void main(String[] args) {
        testNullPathnameThrowsNPE();
        testValidPathnameCreatesFile();
    }

    private static void testNullPathnameThrowsNPE() {
        try {
            new File((String) null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testValidPathnameCreatesFile() {
        String validPath = "testfile";
        File f = new File(validPath);
        if (f == null) {
            throw new RuntimeException("File constructor returned null");
        }
        String path = f.getPath();
        if (!validPath.equals(path)) {
            throw new RuntimeException("File path mismatch: expected '" + validPath + "' but got '" + path + "'");
        }
    }
}
