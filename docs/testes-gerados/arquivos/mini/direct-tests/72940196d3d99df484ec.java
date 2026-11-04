/*
 * @test
 * @summary Test jdk.internal.jrtfs.JrtFileSystem.close() behavior
 * @run main JrtFileSystemWitupTest
 */

import java.io.IOException;
import java.util.Collections;
import jdk.internal.jrtfs.JrtFileSystem;
import jdk.internal.jrtfs.JrtFileSystemProvider;

public class JrtFileSystemWitupTest {

    public static void main(String[] args) throws Exception {
        testCloseThrowsIfNotClosable();
        testCloseCallsCleanupIfClosable();
    }

    private static void testCloseThrowsIfNotClosable() {
        JrtFileSystemProvider provider = new JrtFileSystemProvider();
        JrtFileSystem fs = null;
        try {
            // env null means isClosable == false
            fs = new JrtFileSystem(provider, null);
            try {
                fs.close();
                throw new AssertionError("Expected UnsupportedOperationException");
            } catch (UnsupportedOperationException expected) {
                // expected
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException during setup", e);
        } finally {
            if (fs != null && fs.isOpen()) {
                try {
                    // forcibly close for cleanup
                    fs.isClosable = true; // hack to allow close
                    fs.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    private static void testCloseCallsCleanupIfClosable() throws IOException {
        JrtFileSystemProvider provider = new JrtFileSystemProvider();
        JrtFileSystem fs = new JrtFileSystem(provider, Collections.emptyMap());
        if (!fs.isOpen()) {
            throw new AssertionError("FileSystem should be open after construction");
        }
        fs.close();
        if (fs.isOpen()) {
            throw new AssertionError("FileSystem should be closed after close()");
        }
    }
}
