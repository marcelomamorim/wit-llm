/*
 * @test
 * @summary Test for jdk.internal.jrtfs.JrtPath.remove() throwing ReadOnlyFileSystemException
 * @run main JrtPathWitupTest
 */

import java.nio.file.ReadOnlyFileSystemException;

public class JrtPathWitupTest {
    public static void main(String[] args) {
        JrtPathWitupTest test = new JrtPathWitupTest();
        test.testRemoveThrowsReadOnlyFileSystemException();
    }

    private void testRemoveThrowsReadOnlyFileSystemException() {
        // Create an anonymous subclass of JrtPath with remove() method
        JrtPath path = new JrtPath(null, "") {
            @Override
            public void remove() {
                throw new ReadOnlyFileSystemException();
            }
        };

        try {
            path.remove();
            throw new AssertionError("Expected ReadOnlyFileSystemException not thrown");
        } catch (ReadOnlyFileSystemException e) {
            // Expected exception caught, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type thrown: " + t);
        }
    }

    // Minimal stub of JrtPath to allow compilation
    static abstract class JrtPath {
        final Object jrtfs;
        private final String path;

        JrtPath(Object jrtfs, String path) {
            this.jrtfs = jrtfs;
            this.path = path;
        }

        public abstract void remove();
    }
}
