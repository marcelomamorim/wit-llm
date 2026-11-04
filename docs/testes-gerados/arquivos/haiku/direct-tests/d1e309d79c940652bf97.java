/*
 * @test
 * @summary JrtFileSystem.newWatchService must throw UnsupportedOperationException
 * @run main JrtFileSystemWitupTest
 */
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

public class JrtFileSystemWitupTest {
    public static void main(String[] args) throws Exception {
        FileSystem fs = FileSystems.getFileSystem(URI.create("jrt:/"));
        if (!"jrt".equals(fs.provider().getScheme())) {
            throw new RuntimeException("unexpected scheme: " + fs.provider().getScheme());
        }
        for (int i = 0; i < 2; i++) {
            boolean thrown = false;
            try {
                fs.newWatchService();
            } catch (UnsupportedOperationException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("newWatchService() did not throw UnsupportedOperationException");
            }
        }
        if (!fs.isOpen()) {
            throw new RuntimeException("file system unexpectedly closed");
        }
    }
}
