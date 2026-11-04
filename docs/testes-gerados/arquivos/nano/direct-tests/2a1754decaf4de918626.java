/* @test    @summary Test constructor of java.io.File with valid and null input    @run main FileWitupTest */

import java.io.File;

public class FileWitupTest {

    public static void main(String[] args) {
        testValidPath();
        testNullPath();
    }

    static void testValidPath() {
        String path = "test/directory/file.txt";
        File file = new File(path);
        if (file == null) {
            throw new AssertionError("File object should not be null for valid path");
        }
        String expectedPath = path;
        String actualPath = file.getPath();
        if (!expectedPath.equals(actualPath)) {
            throw new AssertionError("Expected path: " + expectedPath + ", but got: " + actualPath);
        }
    }

    static void testNullPath() {
        try {
            new File((String) null);
            throw new AssertionError("NullPointerException expected when passing null path");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
