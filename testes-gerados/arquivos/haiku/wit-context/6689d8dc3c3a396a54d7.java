/*
 * @test
 * @summary Verifies that FileStore.getBlockSize() throws UnsupportedOperationException by default
 * @run main FileStoreWitupTest
 */

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileStoreAttributeView;

public class FileStoreWitupTest {

    static final class TestStore extends FileStore {
        @Override
        public String name() {
            return "test";
        }

        @Override
        public String type() {
            return "test";
        }

        @Override
        public boolean isReadOnly() {
            return false;
        }

        @Override
        public long getTotalSpace() throws IOException {
            return 0L;
        }

        @Override
        public long getUsableSpace() throws IOException {
            return 0L;
        }

        @Override
        public long getUnallocatedSpace() throws IOException {
            return 0L;
        }

        @Override
        public boolean supportsFileAttributeView(Class<? extends FileAttributeView> type) {
            return false;
        }

        @Override
        public boolean supportsFileAttributeView(String name) {
            return false;
        }

        @Override
        public <V extends FileStoreAttributeView> V getFileStoreAttributeView(Class<V> type) {
            return null;
        }

        @Override
        public Object getAttribute(String attribute) throws IOException {
            return null;
        }
    }

    public static void main(String[] args) throws IOException {
        FileStore store = new TestStore();
        boolean thrown = false;
        try {
            store.getBlockSize();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException from getBlockSize()");
        }
    }
}
