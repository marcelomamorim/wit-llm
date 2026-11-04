/*
 * @test
 * @summary Verifies that the jrtfs FileStore getUsableSpace() throws UnsupportedOperationException
 * @run main JrtFileStoreWitupTest
 */

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

public class JrtFileStoreWitupTest {

    public static void main(String[] args) throws Exception {
        FileSystem jrt = FileSystems.getFileSystem(URI.create("jrt:/"));
        boolean found = false;
        for (FileStore store : jrt.getFileStores()) {
            if ("jrtfs".equals(store.type())) {
                found = true;
                checkUsableSpaceUnsupported(store);
            }
        }
        if (!found) {
            throw new RuntimeException("No jrtfs FileStore found");
        }
    }

    private static void checkUsableSpaceUnsupported(FileStore store) {
        try {
            long value = store.getUsableSpace();
            throw new RuntimeException("Expected UnsupportedOperationException, got " + value);
        } catch (UnsupportedOperationException expected) {
            if (!"getUsableSpace".equals(expected.getMessage())) {
                throw new RuntimeException("Unexpected message: " + expected.getMessage(), expected);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }
}
