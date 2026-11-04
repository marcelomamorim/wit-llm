/*
 * @test
 * @summary Test java.io.File(File parent, String child) constructor for null child and basic path resolution
 * @run main FileWitupTest
 */

import java.io.File;

public class FileWitupTest {
    public static void main(String[] args) {
        testNullChildThrows();
        testNonNullParentEmptyPath();
        testNonNullParentNonEmptyPath();
        testNullParent();
    }

    private static void testNullChildThrows() {
        File parent = new File(".");
        try {
            new File(parent, null);
            throw new RuntimeException("Expected NullPointerException for null child");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testNonNullParentEmptyPath() {
        File parent = new File(""); // empty path
        File f = new File(parent, "child");
        String p = f.getPath();
        if (p == null || p.isEmpty()) {
            throw new RuntimeException("Path should not be null or empty when parent path is empty");
        }
    }

    private static void testNonNullParentNonEmptyPath() {
        File parent = new File("parentDir");
        File f = new File(parent, "child");
        String p = f.getPath();
        if (!p.contains("parentDir") || !p.contains("child")) {
            throw new RuntimeException("Path should contain both parent and child parts");
        }
    }

    private static void testNullParent() {
        File f = new File((File) null, "child");
        String p = f.getPath();
        if (p == null || !p.equals("child")) {
            throw new RuntimeException("Path should equal child string when parent is null");
        }
    }
}
