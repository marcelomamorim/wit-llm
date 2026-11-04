/*
 * @test
 * @summary Test java.io.File constructor with String pathname
 * @run main FileWitupTest
 */

public class FileWitupTest {
    public static void main(String[] args) {
        testNullPathnameThrows();
        testValidPathname();
    }

    private static void testNullPathnameThrows() {
        try {
            new java.io.File(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testValidPathname() {
        String path = "testpath";
        java.io.File f = new java.io.File(path);
        if (f == null) {
            throw new RuntimeException("File instance is null");
        }
        String p = f.getPath();
        if (!p.equals(path)) {
            throw new RuntimeException("File path mismatch: expected '" + path + "' but got '" + p + "'");
        }
    }
}
