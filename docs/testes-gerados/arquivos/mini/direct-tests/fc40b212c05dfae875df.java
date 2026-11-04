/*
 * @test
 * @summary Test java.io.File(File parent, String child) constructor behavior
 * @run main FileWitupTest
 */

import java.io.File;

public class FileWitupTest {
    public static void main(String[] args) {
        testNullChildThrows();
        testParentNullChildNormalized();
        testParentEmptyChildResolvedWithDefaultParent();
        testParentNonEmptyChildResolvedWithParentPath();
    }

    private static void testNullChildThrows() {
        try {
            new File(new File("/tmp"), null);
            throw new RuntimeException("Expected NullPointerException for null child");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testParentNullChildNormalized() {
        File f = new File((File) null, "abc/def");
        String path = f.getPath();
        if (!path.equals("abc/def") && !path.equals("abc\\def")) {
            throw new RuntimeException("Expected normalized path 'abc/def' or 'abc\\def', got: " + path);
        }
    }

    private static void testParentEmptyChildResolvedWithDefaultParent() {
        File emptyParent = new File("");
        File f = new File(emptyParent, "xyz");
        String path = f.getPath();
        if (path.isEmpty()) {
            throw new RuntimeException("Path should not be empty when parent is empty string and child is 'xyz'");
        }
    }

    private static void testParentNonEmptyChildResolvedWithParentPath() {
        File parent = new File("/tmp");
        File f = new File(parent, "file.txt");
        String path = f.getPath();
        if (!path.startsWith(parent.getPath())) {
            throw new RuntimeException("Expected path to start with parent path '/tmp', got: " + path);
        }
    }
}
