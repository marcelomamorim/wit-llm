/*
 * @test
 * @summary Test JrtFileStore.getUnallocatedSpace throws UnsupportedOperationException
 * @run main JrtFileStoreWitupTest
 */

import java.io.IOException;
import jdk.internal.jrtfs.JrtFileStore;
import jdk.internal.jrtfs.JrtPath;
import java.nio.file.FileSystem;

public class JrtFileStoreWitupTest {

    public static void main(String[] args) throws Exception {
        // Create a minimal JrtPath stub to construct JrtFileStore
        JrtPath jrtPath = new JrtPathStub();
        JrtFileStore store = new JrtFileStore(jrtPath);

        // Test getUnallocatedSpace throws UnsupportedOperationException
        try {
            store.getUnallocatedSpace();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            if (!"getUnallocatedSpace".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    // Minimal stub for JrtPath to provide a FileSystem
    static class JrtPathStub extends JrtPath {
        JrtPathStub() {
            super(null);
        }

        @Override
        public FileSystem getFileSystem() {
            return new FileSystemStub();
        }
    }

    // Minimal stub for FileSystem to satisfy JrtFileStore constructor
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
        public void close() throws IOException {}

        @Override
        public boolean isOpen() { return true; }

        @Override
        public Iterable<java.nio.file.Path> getRootDirectories() { return null; }

        @Override
        public Iterable<java.nio.file.FileStore> getFileStores() { return null; }

        @Override
        public java.util.Set<String> supportedFileAttributeViews() { return null; }

        @Override
        public java.nio.file.Path getPath(String first, String... more) { return null; }

        @Override
        public java.nio.file.WatchService newWatchService() throws IOException { return null; }
    }
}
