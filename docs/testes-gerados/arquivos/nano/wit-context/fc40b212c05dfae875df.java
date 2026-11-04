/*
 * @test
 * @summary Test for java.io.File constructor with parent File and child String
 * @run main FileWitupTest
 */

import java.io.File;

public class FileWitupTest {

    public static void main(String[] args) {
        testConstructorWithParentAndChild();
        testConstructorWithNullChild();
    }

    private static void testConstructorWithParentAndChild() {
        try {
            File parent = new File("/tmp");
            String child = "subdir/file.txt";
            File file = new File(parent, child);
            if (file == null) {
                throw new AssertionError("File object should not be null");
            }
            String path = file.getPath();
            if (!path.contains("subdir/file.txt")) {
                throw new AssertionError("Path does not contain expected child path: " + path);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testConstructorWithParentAndChild", e);
        }
    }

    private static void testConstructorWithNullChild() {
        try {
            File parent = new File("/tmp");
            String child = null;
            boolean exceptionThrown = false;
            try {
                new File(parent, child);
            } catch (NullPointerException npe) {
                exceptionThrown = true;
            }
            if (!exceptionThrown) {
                throw new AssertionError("Expected NullPointerException when child is null");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testConstructorWithNullChild", e);
        }
    }
}
