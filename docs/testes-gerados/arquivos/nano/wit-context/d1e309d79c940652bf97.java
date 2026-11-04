/*
 * @test
 * @summary Test for JrtFileSystem.newWatchService() method, expecting UnsupportedOperationException.
 * @run main JrtFileSystemWitupTest
 */

import java.nio.file.WatchService;
import java.lang.UnsupportedOperationException;

public class JrtFileSystemWitupTest {

    public static void main(String[] args) {
        testNewWatchServiceThrowsException();
    }

    public static void testNewWatchServiceThrowsException() {
        jdk.internal.jrtfs.JrtFileSystem fs = null;
        try {
            // Instantiate JrtFileSystem with minimal parameters
            fs = new jdk.internal.jrtfs.JrtFileSystem(null, null);
        } catch (Exception e) {
            // If instantiation fails, the test cannot proceed
            throw new AssertionError("Failed to instantiate JrtFileSystem", e);
        }
        try {
            fs.newWatchService();
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e, e);
        }
    }
}
