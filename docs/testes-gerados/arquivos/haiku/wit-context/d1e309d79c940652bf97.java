/*
 * @test
 * @summary Verify that newWatchService() on the jrt file system throws UnsupportedOperationException
 * @run main JrtFileSystemWitupTest
 */
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.WatchService;

public class JrtFileSystemWitupTest {
    public static void main(String[] args) throws Exception {
        FileSystem fs = FileSystems.getFileSystem(URI.create("jrt:/"));
        for (int i = 0; i < 2; i++) {
            boolean thrown = false;
            try {
                WatchService ws = fs.newWatchService();
                if (ws != null) {
                    ws.close();
                }
            } catch (UnsupportedOperationException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("Expected UnsupportedOperationException from newWatchService() on attempt " + i);
            }
        }
    }
}
