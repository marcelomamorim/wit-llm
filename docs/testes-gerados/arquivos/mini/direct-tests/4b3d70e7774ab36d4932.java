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

    // Minimal stub class to provide a FileSystem for JrtFileStore constructor
    static class StubFileSystem extends FileSystem {
        @Override public String toString() { return "StubFileSystem"; }
        @Override public boolean isReadOnly() { return true; }
        // Other abstract methods throw UnsupportedOperationException
        @Override public void close() throws IOException { throw new UnsupportedOperationException(); }
        @Override public boolean isOpen() { throw new UnsupportedOperationException(); }
        @Override public boolean isReadOnly() { return true; }
        @Override public Iterable<java.nio.file.Path> getRootDirectories() { throw new UnsupportedOperationException(); }
        @Override public Iterable<java.nio.file.FileStore> getFileStores() { throw new UnsupportedOperationException(); }
        @Override public Set<String> supportedFileAttributeViews() { throw new UnsupportedOperationException(); }
        @Override public java.nio.file.Path getPath(String first, String... more) { throw new UnsupportedOperationException(); }
        @Override public java.nio.file.WatchService newWatchService() throws IOException { throw new UnsupportedOperationException(); }
    }

    // Minimal stub class to provide a JrtPath for JrtFileStore constructor
    static class StubJrtPath extends JrtPath {
        private final FileSystem fs;
        StubJrtPath(FileSystem fs) { this.fs = fs; }
        @Override public FileSystem getFileSystem() { return fs; }
    }

    public static void main(String[] args) {
        FileSystem fs = new StubFileSystem();
        JrtPath path = new StubJrtPath(fs);
        JrtFileStore store = new JrtFileStore(path);

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
