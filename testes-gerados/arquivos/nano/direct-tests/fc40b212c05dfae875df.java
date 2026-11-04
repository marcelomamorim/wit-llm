/* @test    @summary Test constructor File(File parent, String child) with valid parent and child paths    @run main FileWitupTest */

import java.io.File;

public class FileWitupTest {

    public static void main(String[] args) {
        testNullChildThrows();
        testValidParentAndChild();
        testNullParent();
    }

    static void testNullChildThrows() {
        try {
            File parent = new File("/tmp");
            new File(parent, null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testValidParentAndChild() {
        File parent = new File("/tmp");
        String child = "subdir/file.txt";
        File result = new File(parent, child);
        String expectedPath = parent.getPath().endsWith("/") ? parent.getPath() + child : parent.getPath() + "/" + child;
        if (!result.getPath().endsWith(child)) {
            throw new AssertionError("Result path does not end with child: " + result.getPath());
        }
    }

    static void testNullParent() {
        String child = "file.txt";
        File result = new File(null, child);
        if (!result.getPath().endsWith(child)) {
            throw new AssertionError("Result path does not end with child: " + result.getPath());
        }
    }
}
