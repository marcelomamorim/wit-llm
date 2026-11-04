/*
 * @test
 * @summary JrtFileStore.getTotalSpace must throw UnsupportedOperationException
 * @run main JrtFileStoreWitupTest
 */

import java.net.URI;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class JrtFileStoreWitupTest {
    public static void main(String[] args) throws Exception {
        Path root = FileSystems.getFileSystem(URI.create("jrt:/")).getPath("/");
        FileStore store = Files.getFileStore(root);
        if (!"jrtfs".equals(store.type())) {
            throw new RuntimeException("unexpected store type: " + store.type());
        }
        try {
            long total = store.getTotalSpace();
            throw new RuntimeException("expected UnsupportedOperationException, got " + total);
        } catch (UnsupportedOperationException expected) {
        }
    }
}
