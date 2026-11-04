/*
 * @test
 * @summary Test the isHidden method of JrtFileSystemProvider.
 * @run main JrtFileSystemProviderWitupTest
 */
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jrtfs.JrtFileSystemProvider;

public class JrtFileSystemProviderWitupTest {

    public static void main(String[] args) {
        testIsHiddenWithRegularPath();
        testIsHiddenWithHiddenPath();
    }

    private static void testIsHiddenWithRegularPath() {
        Path path = Paths.get("/some/regular/file");
        JrtFileSystemProvider provider = new JrtFileSystemProvider();
        boolean result = provider.isHidden(path);
        // Expect false for a regular path
        if (result != false) {
            throw new AssertionError("Expected isHidden to return false for regular path");
        }
    }

    private static void testIsHiddenWithHiddenPath() {
        Path path = Paths.get("/.hidden/file");
        JrtFileSystemProvider provider = new JrtFileSystemProvider();
        boolean result = provider.isHidden(path);
        // Expect true for a hidden path
        if (result != true) {
            throw new AssertionError("Expected isHidden to return true for hidden path");
        }
    }
}
