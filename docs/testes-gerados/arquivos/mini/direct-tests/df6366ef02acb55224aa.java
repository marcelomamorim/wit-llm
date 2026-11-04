/*
 * @test
 * @summary Test JrtFileStore.getAttribute throws UnsupportedOperationException with correct message
 * @run main jdk.internal.jrtfs.JrtFileStoreWitupTest
 */

import java.io.IOException;
import jdk.internal.jrtfs.JrtFileStore;
import jdk.internal.jrtfs.JrtPath;

public class JrtFileStoreWitupTest {

    public static void main(String[] args) throws Exception {
        // Create a minimal JrtPath stub to construct JrtFileStore
        JrtPath jrtPath = new JrtPathStub();
        JrtFileStore store = new JrtFileStore(jrtPath);

        // Test getAttribute throws UnsupportedOperationException with expected message
        String attr = "testAttr";
        try {
            store.getAttribute(attr);
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            String msg = e.getMessage();
            if (!msg.equals("does not support " + attr)) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }

    // Minimal stub for JrtPath to allow JrtFileStore construction
    static class JrtPathStub extends JrtPath {
        JrtPathStub() {
            super(null, null, 0, 0);
        }

        @Override
        public java.nio.file.FileSystem getFileSystem() {
            return new FileSystemStub();
        }
    }

    // Minimal stub for FileSystem to satisfy JrtFileStore constructor
    static class FileSystemStub extends java.nio.file.FileSystem {
        @Override
        public String toString() {
            return "stubfs";
        }

        @Override
        public boolean isReadOnly() {
            return true;
        }

        // The rest of abstract methods can throw UnsupportedOperationException as they are not used
        @Override public void close() { }
        @Override public boolean isOpen() { return true; }
        @Override public java.nio.file.Path getPath(String first, String... more) { throw new UnsupportedOperationException(); }
        @Override public java.nio.file.WatchService newWatchService() { throw new UnsupportedOperationException(); }
        @Override public java.util.Set<String> supportedFileAttributeViews() { throw new UnsupportedOperationException(); }
        @Override public java.nio.file.FileSystemProvider provider() { throw new UnsupportedOperationException(); }
    }
}
