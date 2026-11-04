/*
 * @test
 * @summary Test that JrtPath.remove() throws ReadOnlyFileSystemException
 * @run main JrtPathWitupTest
 */

import java.nio.file.ReadOnlyFileSystemException;

public class JrtPathWitupTest {

    public static void main(String[] args) {
        testRemoveThrowsException();
    }

    public static void testRemoveThrowsException() {
        jdk.internal.jrtfs.JrtPath path = new jdk.internal.jrtfs.JrtPath(null, "");
        try {
            path.remove();
            throw new AssertionError("Expected ReadOnlyFileSystemException was not thrown");
        } catch (ReadOnlyFileSystemException e) {
            // Test passes if exception is caught
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
