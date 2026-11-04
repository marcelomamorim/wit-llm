import java.io.File;

/*
 * @test
 * @summary Verifies observable exception behavior of Runtime.load(String) for null, relative and missing absolute library paths
 * @run main RuntimeWitupTest
 */
public class RuntimeWitupTest {
    public static void main(String[] args) {
        testNullFilenameThrowsNullPointerException();
        testRelativeFilenameThrowsUnsatisfiedLinkError();
        testMissingAbsoluteLibraryThrowsUnsatisfiedLinkError();
    }

    static void testNullFilenameThrowsNullPointerException() {
        boolean thrown = false;
        try {
            Runtime.getRuntime().load(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NullPointerException for null filename");
        }
    }

    static void testRelativeFilenameThrowsUnsatisfiedLinkError() {
        boolean thrown = false;
        try {
            Runtime.getRuntime().load("relative_lib_witup.so");
        } catch (UnsatisfiedLinkError e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsatisfiedLinkError for relative filename");
        }
    }

    static void testMissingAbsoluteLibraryThrowsUnsatisfiedLinkError() {
        String path = new File("missing_lib_witup_" + System.nanoTime() + ".so").getAbsolutePath();
        boolean thrown = false;
        try {
            Runtime.getRuntime().load(path);
        } catch (UnsatisfiedLinkError e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsatisfiedLinkError for missing absolute library: " + path);
        }
    }
}
