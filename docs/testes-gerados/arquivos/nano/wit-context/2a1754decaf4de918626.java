/*
 * @test
 * @summary Test for java.io.File constructor with null pathname
 * @run main FileWitupTest
 */

import java.io.File;

public class FileWitupTest {

    public static void main(String[] args) {
        testNullPathnameThrowsException();
        testValidPathnameDoesNotThrow();
    }

    private static void testNullPathnameThrowsException() {
        boolean threwNullPointer = false;
        try {
            new File((String) null);
        } catch (NullPointerException e) {
            threwNullPointer = true;
        }
        if (!threwNullPointer) {
            throw new AssertionError("Expected NullPointerException for null pathname");
        }
    }

    private static void testValidPathnameDoesNotThrow() {
        String validPath = "testfile.txt";
        try {
            File f = new File(validPath);
            if (f == null) {
                throw new AssertionError("File constructor returned null for valid pathname");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid pathname: " + e);
        }
    }
}
