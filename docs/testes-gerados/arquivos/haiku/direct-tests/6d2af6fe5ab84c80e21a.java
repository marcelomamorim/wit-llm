/*
 * @test
 * @summary JrtFileStore.getUsableSpace must throw UnsupportedOperationException with message "getUsableSpace"
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
        try {
            long value = store.getUsableSpace();
            throw new AssertionError("expected UnsupportedOperationException, got " + value);
        } catch (UnsupportedOperationException e) {
            if (!"getUsableSpace".equals(e.getMessage())) {
                throw new AssertionError("unexpected message: " + e.getMessage());
            }
        }
        try {
            store.getUsableSpace();
            throw new AssertionError("second call did not throw");
        } catch (UnsupportedOperationException e) {
            if (!"getUsableSpace".equals(e.getMessage())) {
                throw new AssertionError("unexpected message on second call: " + e.getMessage());
            }
        }
    }
}
