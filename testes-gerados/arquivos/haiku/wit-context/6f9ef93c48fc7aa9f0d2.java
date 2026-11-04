/*
 * @test
 * @summary Witup-derived tests for JrtFileSystemProvider.isHidden(Path)
 * @run main JrtFileSystemProviderWitupTest
 */

import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class JrtFileSystemProviderWitupTest {

    public static void main(String[] args) throws Exception {
        testNullPathThrowsNPE();
        testJrtPathDoesNotThrow();
        System.out.println("PASSED");
    }

    static void testNullPathThrowsNPE() throws Exception {
        FileSystem fs = FileSystems.getFileSystem(URI.create("jrt:/"));
        boolean thrown = false;
        try {
            fs.provider().isHidden(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NullPointerException for null path");
        }
    }

    static void testJrtPathDoesNotThrow() throws Exception {
        FileSystem fs = FileSystems.getFileSystem(URI.create("jrt:/"));
        Path p = fs.getPath("/modules/java.base/java/lang/Object.class");
        boolean hidden = fs.provider().isHidden(p);
        if (hidden) {
            throw new AssertionError("Expected java/lang/Object.class not to be hidden");
        }
    }
}
