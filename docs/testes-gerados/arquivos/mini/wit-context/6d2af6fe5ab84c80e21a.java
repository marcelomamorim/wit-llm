/*
 * @test
 * @summary Test that JrtFileStore.getUsableSpace() throws UnsupportedOperationException
 * @run main JrtFileStoreWitupTest
 */

import java.io.IOException;
import java.nio.file.FileSystem;
import jdk.internal.jrtfs.JrtFileStore;
import jdk.internal.jrtfs.JrtPath;

public class JrtFileStoreWitupTest {

    public static void main(String[] args) throws Exception {
        // Create a minimal JrtPath stub to construct JrtFileStore
        JrtPath stubPath = new JrtPathStub();
        JrtFileStore store = new JrtFileStore(stubPath);

        // Test getUsableSpace throws UnsupportedOperationException
        try {
            store.getUsableSpace();
            throw new AssertionError("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            if (!"getUsableSpace".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    // Minimal stub class to provide getFileSystem() for JrtFileStore constructor
    static class JrtPathStub extends JrtPath {
        JrtPathStub() {
            super(null, null, 0, 0);
        }

        @Override
        public FileSystem getFileSystem() {
            return new FileSystemStub();
        }
    }

    // Minimal stub FileSystem to support isReadOnly() and toString() used by JrtFileStore
    static class FileSystemStub extends FileSystem {
        @Override
        public String toString() {
            return "stubfs";
        }

        @Override
        public boolean isReadOnly() {
            return true;
        }

        // The following abstract methods must be implemented but are not used in this test
        @Override
        public void close() throws IOException {
        }

        @Override
        public boolean isOpen() {
            return true;
        }

        @Override
        public boolean isReadOnly() {
            return true;
        }

        @Override
        public Iterable<java.nio.file.Path> getRootDirectories() {
            return null;
        }

        @Override
        public Iterable<java.nio.file.FileStore> getFileStores() {
            return null;
        }

        @Override
        public Set<String> supportedFileAttributeViews() {
            return null;
        }

        @Override
        public java.nio.file.Path getPath(String first, String... more) {
            return null;
        }

        @Override
        public java.nio.file.WatchService newWatchService() throws IOException {
            return null;
        }
    }
}
