/*
 * @test
 * @summary Verifies that the jrt FileStore reports getUnallocatedSpace as unsupported
 * @run main JrtFileStoreWitupTest
 */

import java.net.URI;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class JrtFileStoreWitupTest {

    public static void main(String[] args) throws Exception {
        FileSystem jrt = FileSystems.getFileSystem(URI.create("jrt:/"));
        Path p = jrt.getPath("/modules");
        FileStore store = Files.getFileStore(p);

        if (!"jrtfs".equals(store.type())) {
            throw new AssertionError("unexpected type: " + store.type());
        }

        boolean thrown = false;
        try {
            store.getUnallocatedSpace();
        } catch (UnsupportedOperationException e) {
            thrown = true;
            if (!"getUnallocatedSpace".equals(e.getMessage())) {
                throw new AssertionError("unexpected message: " + e.getMessage());
            }
        }
        if (!thrown) {
            throw new AssertionError("expected UnsupportedOperationException");
        }
    }
}
