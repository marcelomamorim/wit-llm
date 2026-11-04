/*
 * @test
 * @summary Verify that JrtFileStore.getUnallocatedSpace() throws UnsupportedOperationException with the documented message
 * @modules java.base
 * @run main JrtFileStoreWitupTest
 */

import java.net.URI;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

public class JrtFileStoreWitupTest {

    public static void main(String[] args) throws Exception {
        FileSystem fs = FileSystems.getFileSystem(URI.create("jrt:/"));
        int checked = 0;
        for (FileStore store : fs.getFileStores()) {
            checked++;
            try {
                store.getUnallocatedSpace();
                throw new AssertionError("expected UnsupportedOperationException");
            } catch (UnsupportedOperationException expected) {
                if (!"getUnallocatedSpace".equals(expected.getMessage())) {
                    throw new AssertionError("unexpected message: " + expected.getMessage());
                }
            }
        }
        if (checked == 0) {
            throw new AssertionError("no jrt file stores found");
        }
    }
}
