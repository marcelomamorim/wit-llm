/*
 * @test
 * @summary Test that newWatchService() throws UnsupportedOperationException
 * @run main JrtFileSystemWitupTest
 */
import java.nio.file.WatchService;
import jdk.internal.jrtfs.JrtFileSystem;

public class JrtFileSystemWitupTest {
    public static void main(String[] args) {
        JrtFileSystem fs = null;
        try {
            // Instantiate a JrtFileSystem with minimal setup
            fs = new JrtFileSystem(null, null);
        } catch (Exception e) {
            // If construction fails, the test cannot proceed
            throw new RuntimeException("Failed to create JrtFileSystem instance", e);
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
