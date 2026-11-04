/*
 * @test
 * @summary Test JrtFileStore.getUnallocatedSpace throws UnsupportedOperationException
 * @run main JrtFileStoreWitupTest
 */

import java.io.IOException;
import java.nio.file.FileSystem;
import jdk.internal.jrtfs.JrtFileStore;
import jdk.internal.jrtfs.JrtPath;

public class JrtFileStoreWitupTest {

    // Minimal stub class to provide a FileSystem for JrtFileStore constructor
    static class DummyFileSystem extends FileSystem {
        @Override public void close() throws IOException { }
        @Override public boolean isOpen() { return true; }
        @Override public boolean isReadOnly() { return true; }
        @Override public String getSeparator() { return "/"; }
        @Override public Iterable<java.nio.file.Path> getRootDirectories() { return null; }
        @Override public Iterable<java.nio.file.FileStore> getFileStores() { return null; }
        @Override public Set<String> supportedFileAttributeViews() { return null; }
        @Override public java.nio.file.Path getPath(String first, String... more) { return null; }
        @Override public java.nio.file.WatchService newWatchService() throws IOException { return null; }
    }

    // Minimal stub class to provide JrtPath for JrtFileStore constructor
    static class DummyJrtPath extends JrtPath {
        private final FileSystem fs;
        DummyJrtPath(FileSystem fs) {
            super(null, null, 0, 0);
            this.fs = fs;
        }
        @Override
        public FileSystem getFileSystem() {
            return fs;
        }
    }

    public static void main(String[] args) throws Exception {
        DummyFileSystem dummyFs = new DummyFileSystem();
        DummyJrtPath dummyPath = new DummyJrtPath(dummyFs);
        JrtFileStore store = new JrtFileStore(dummyPath);

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
}
