/*
 * @test
 * @summary Test that JrtFileStore.getUsableSpace() throws UnsupportedOperationException
 * @run main jdk.internal.jrtfs.JrtFileStoreWitupTest
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
            super(null, null, 0, null);
            this.fs = fs;
        }
        @Override
        public FileSystem getFileSystem() {
            return fs;
        }
    }

    // Minimal stub for FileSystem to allow isReadOnly() and toString()
    static class StubFileSystem extends FileSystem {
        @Override
        public String toString() {
            return "StubFileSystem";
        }
        @Override
        public boolean isReadOnly() {
            return true;
        }
        // The following abstract methods must be implemented but are unused in this test
        @Override public void close() throws IOException {}
        @Override public boolean isOpen() { return true; }
        @Override public boolean isReadOnly() { return true; }
        @Override public String getSeparator() { return "/"; }
        @Override public Iterable<java.nio.file.Path> getRootDirectories() { return null; }
        @Override public Iterable<java.nio.file.FileStore> getFileStores() { return null; }
        @Override public Set<String> supportedFileAttributeViews() { return null; }
        @Override public java.nio.file.Path getPath(String first, String... more) { return null; }
        @Override public java.nio.file.WatchService newWatchService() throws IOException { return null; }
    }

    public static void main(String[] args) {
        FileSystem fs = new StubFileSystem();
        JrtPath jrtPath = new StubJrtPath(fs);
        JrtFileStore store = new JrtFileStore(jrtPath);

        try {
            store.getUsableSpace();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            if (!"getUsableSpace".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException thrown", e);
        }
    }
}
