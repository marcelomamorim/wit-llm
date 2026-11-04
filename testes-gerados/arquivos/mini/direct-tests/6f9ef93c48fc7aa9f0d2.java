/*
 * @test
 * @summary Test jdk.internal.jrtfs.JrtFileSystemProvider.isHidden(Path) method
 * @run main JrtFileSystemProviderWitupTest
 */

import java.nio.file.Path;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import jdk.internal.jrtfs.JrtFileSystemProvider;

public class JrtFileSystemProviderWitupTest {
    public static void main(String[] args) throws Exception {
        JrtFileSystemProvider provider = new JrtFileSystemProvider();

        // Create a jrt file system to obtain a valid jrt Path
        FileSystem fs = FileSystems.getFileSystem(java.net.URI.create("jrt:/"));
        if (fs == null) {
            fs = FileSystems.newFileSystem(java.net.URI.create("jrt:/"), java.util.Collections.emptyMap());
        }

        // Obtain a Path in the jrt file system
        Path root = fs.getPath("/");

        // Call isHidden on the root path
        boolean hidden = provider.isHidden(root);

        // The root of jrt file system is not hidden
        if (hidden) {
            throw new AssertionError("Root path should not be hidden");
        }

        // Test with a child path (e.g. /modules)
        Path modules = root.resolve("modules");
        boolean hiddenModules = provider.isHidden(modules);

        // The modules directory should not be hidden
        if (hiddenModules) {
            throw new AssertionError("Modules path should not be hidden");
        }

        System.out.println("Test passed");
    }
}
