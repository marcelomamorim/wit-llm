/*
 * @test
 * @summary Verifies that JrtFileStore.getTotalSpace() throws UnsupportedOperationException with message "getTotalSpace" when reached through the public jrt file system API
 * @run main JrtFileStoreWitupTest
 */

import java.net.URI;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;

public class JrtFileStoreWitupTest {

    public static void main(String[] args) throws Exception {
        FileSystem jrtfs = FileSystems.getFileSystem(URI.create("jrt:/"));
        FileStore store = Files.getFileStore(jrtfs.getPath("/"));

        if (!"jrtfs".equals(store.type())) {
            throw new RuntimeException("Unexpected file store type: " + store.type());
        }

        boolean thrown = false;
        try {
            store.getTotalSpace();
        } catch (UnsupportedOperationException e) {
            thrown = true;
            if (!"getTotalSpace".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
        }
        if (!thrown) {
            throw new RuntimeException("getTotalSpace() did not throw UnsupportedOperationException");
        }
    }
}
