/*
 * @test
 * @summary Test JrtFileStore.getAttribute throws UnsupportedOperationException with correct message
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
            super(null, null, 0, null); // call super with dummy values
            this.fs = fs;
        }
        @Override
        public FileSystem getFileSystem() {
            return fs;
        }
    }

    // Minimal stub for FileSystem to allow isReadOnly and toString
    static class StubFileSystem extends FileSystem {
        private final boolean readOnly;
        private final String name;

        StubFileSystem(String name, boolean readOnly) {
            this.name = name;
            this.readOnly = readOnly;
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public boolean isReadOnly() {
            return readOnly;
        }

        // The following abstract methods must be implemented but can throw UnsupportedOperationException
        @Override
        public void close() throws IOException {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isOpen() {
            return true;
        }

        @Override
        public Iterable<java.nio.file.Path> getRootDirectories() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Iterable<java.nio.file.FileStore> getFileStores() {
            throw new UnsupportedOperationException();
        }

        @Override
        public java.util.Set<String> supportedFileAttributeViews() {
            throw new UnsupportedOperationException();
        }

        @Override
        public java.nio.file.Path getPath(String first, String... more) {
            throw new UnsupportedOperationException();
        }

        @Override
        public java.nio.file.WatchService newWatchService() throws IOException {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        // Create stub FileSystem
        StubFileSystem fs = new StubFileSystem("stubfs", true);
        // Create JrtPath stub
        StubJrtPath jrtPath = new StubJrtPath(fs);
        // Create JrtFileStore instance
        JrtFileStore store = new JrtFileStore(jrtPath);

        // Test getAttribute throws UnsupportedOperationException with expected message
        String testAttr = "anyAttribute";
        try {
            store.getAttribute(testAttr);
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains(testAttr)) {
                throw new RuntimeException("Exception message does not contain attribute name: " + msg);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException thrown", e);
        }
    }
}
