/*
 * @test
 * @summary close() on the default (non-closable) jrt file system throws UnsupportedOperationException and leaves it open
 * @run main JrtFileSystemWitupTest
 */

import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

public class JrtFileSystemWitupTest {

    public static void main(String[] args) throws Exception {
        FileSystem fs = FileSystems.getFileSystem(URI.create("jrt:/"));

        if (!fs.isOpen()) {
            throw new RuntimeException("default jrt file system should be open");
        }

        boolean thrown = false;
        try {
            fs.close();
        } catch (UnsupportedOperationException expected) {
            thrown = true;
        }

        if (!thrown) {
            throw new RuntimeException("close() should throw UnsupportedOperationException when env is null");
        }

        if (!fs.isOpen()) {
            throw new RuntimeException("file system must remain open after rejected close()");
        }
    }
}
