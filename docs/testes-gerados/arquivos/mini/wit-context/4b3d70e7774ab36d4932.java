/*
 * @test
 * @summary Test that JrtFileStore.getTotalSpace() throws UnsupportedOperationException
 * @run main JrtFileStoreWitupTest
 */

import java.io.IOException;
import java.nio.file.FileSystem;
import jdk.internal.jrtfs.JrtFileStore;
import jdk.internal.jrtfs.JrtPath;

public class JrtFileStoreWitupTest {

    // Minimal stub for JrtPath to allow construction of JrtFileStore
    static class StubJrtPath extends JrtPath {
        private final FileSystem fs;
        StubJrtPath(FileSystem fs) {
            super(null, null, 0, 0); // call super with dummy values
            this.fs = fs;
        }
        @Override
        public FileSystem getFileSystem() {
            return fs;
        }
    }

    // Minimal stub for FileSystem to support isReadOnly and toString
    static class StubFileSystem extends FileSystem {
        private final boolean readOnly;
        StubFileSystem(boolean readOnly) {
            super(null);
            this.readOnly = readOnly;
        }
        @Override
        public boolean isReadOnly() {
            return readOnly;
        }
        @Override
        public String toString() {
            return "StubFileSystem";
        }
        // The following abstract methods must be implemented but are unused in this test
        @Override public void close() throws IOException {}
        @Override public boolean isOpen() { return true; }
        @Override public Iterable<java.nio.file.Path> getRootDirectories() { return null; }
        @Override public Iterable<java.nio.file.FileStore> getFileStores() { return null; }
        @Override public java.util.Set<String> supportedFileAttributeViews() { return null; }
        @Override public java.nio.file.Path getPath(String first, String... more) { return null; }
        @Override public java.nio.file.WatchService newWatchService() throws IOException { return null; }
    }

    public static void main(String[] args) {
        // Create a JrtFileStore instance with stub JrtPath and FileSystem
        StubFileSystem fs = new StubFileSystem(true);
        StubJrtPath jrtPath = new StubJrtPath(fs);
        JrtFileStore store = new JrtFileStore(jrtPath);

        // Test getTotalSpace throws UnsupportedOperationException
        try {
            store.getTotalSpace();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            if (!"getTotalSpace".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException thrown", e);
        }
    }
}
