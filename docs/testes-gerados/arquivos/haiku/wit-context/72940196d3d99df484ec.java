/*
 * @test
 * @summary Verify that close() on the default jrt file system throws UnsupportedOperationException and leaves it open
 * @run main JrtFileSystemWitupTest
 */

import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

public class JrtFileSystemWitupTest {

    public static void main(String[] args) throws Exception {
        FileSystem fs = FileSystems.getFileSystem(URI.create("jrt:/"));

        if (!fs.isOpen()) {
            throw new RuntimeException("jrt file system should be open before close()");
        }

        boolean thrown = false;
        try {
            fs.close();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new RuntimeException("close() should throw UnsupportedOperationException for non-closable jrt file system");
        }

        if (!fs.isOpen()) {
            throw new RuntimeException("jrt file system should remain open after rejected close()");
        }
    }
}
