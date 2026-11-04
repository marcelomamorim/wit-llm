/*
 * @test
 * @summary Verify JrtFileStore.getAttribute throws UnsupportedOperationException with the expected message
 * @run main JrtFileStoreWitupTest
 */

import java.net.URI;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JrtFileStoreWitupTest {

    public static void main(String[] args) throws Exception {
        FileStore store = Files.getFileStore(Paths.get(URI.create("jrt:/")));
        check(store, "basic");
        check(store, "jrt");
        check(store, "unknownAttribute");
        System.out.println("PASSED");
    }

    static void check(FileStore store, String attr) throws Exception {
        try {
            Object value = store.getAttribute(attr);
            throw new AssertionError("Expected UnsupportedOperationException for " + attr + " but got " + value);
        } catch (UnsupportedOperationException e) {
            String expected = "does not support " + attr;
            if (!expected.equals(e.getMessage())) {
                throw new AssertionError("Unexpected message: " + e.getMessage());
            }
        }
    }
}
