/*
 * @test
 * @summary Verify that JrtFileStore.getAttribute throws UnsupportedOperationException with the documented message for any attribute name
 * @run main JrtFileStoreWitupTest
 */
import java.net.URI;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class JrtFileStoreWitupTest {

    public static void main(String[] args) throws Exception {
        Path root = FileSystems.getFileSystem(URI.create("jrt:/")).getPath("/");
        FileStore store = root.getFileStore();
        checkUnsupported(store, "basic");
        checkUnsupported(store, "jrt");
        checkUnsupported(store, "unknownAttribute");
        checkUnsupported(store, "");
        checkUnsupported(store, null);
    }

    private static void checkUnsupported(FileStore store, String attr) throws Exception {
        try {
            Object value = store.getAttribute(attr);
            throw new AssertionError("expected UnsupportedOperationException for attribute " + attr + " but got " + value);
        } catch (UnsupportedOperationException e) {
            String expected = "does not support " + attr;
            if (!expected.equals(e.getMessage())) {
                throw new AssertionError("unexpected message: " + e.getMessage(), e);
            }
        }
    }
}
