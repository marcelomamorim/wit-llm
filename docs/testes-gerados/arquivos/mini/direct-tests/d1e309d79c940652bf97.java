/*
 * @test
 * @summary Test that JrtFileSystem.newWatchService() throws UnsupportedOperationException
 * @run main JrtFileSystemWitupTest
 */

import jdk.internal.jrtfs.JrtFileSystem;
import java.nio.file.WatchService;

public class JrtFileSystemWitupTest {
    public static void main(String[] args) throws Exception {
        JrtFileSystem fs = new JrtFileSystem(null, null);
        try {
            WatchService ws = fs.newWatchService();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }
}
