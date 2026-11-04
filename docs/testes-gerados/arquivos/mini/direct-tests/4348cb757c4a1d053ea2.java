/*
 * @test
 * @summary Test that JrtPath.remove() always throws ReadOnlyFileSystemException
 * @run main JrtPathWitupTest
 */

import jdk.internal.jrtfs.JrtPath;
import java.nio.file.ReadOnlyFileSystemException;

public class JrtPathWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a minimal JrtPath instance with dummy JrtFileSystem and path
        // Since JrtFileSystem is package-private and no public constructor,
        // we create a minimal subclass to pass to JrtPath constructor.
        JrtPath path = new JrtPath(new DummyJrtFileSystem(), "/dummy");

        try {
            path.remove();
            throw new AssertionError("Expected ReadOnlyFileSystemException not thrown");
        } catch (ReadOnlyFileSystemException e) {
            // Expected exception
        }
    }

    // Minimal dummy implementation of JrtFileSystem to satisfy constructor
    static class DummyJrtFileSystem extends jdk.internal.jrtfs.JrtFileSystem {
        DummyJrtFileSystem() {
            super(null);
        }
    }
}
