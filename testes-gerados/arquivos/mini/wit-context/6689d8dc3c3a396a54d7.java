/*
 * @test
 * @summary Test FileStore.getBlockSize() throws UnsupportedOperationException
 * @run main FileStoreWitupTest
 */

import java.nio.file.FileStore;
import java.io.IOException;

public class FileStoreWitupTest {

    static class TestFileStore extends FileStore {
        @Override
        public String name() {
            return "TestStore";
        }

        @Override
        public String type() {
            return "TestType";
        }

        @Override
        public boolean isReadOnly() {
            return false;
        }

        @Override
        public long getTotalSpace() throws IOException {
            return 1024L;
        }

        @Override
        public long getUsableSpace() throws IOException {
            return 512L;
        }

        @Override
        public long getUnallocatedSpace() throws IOException {
            return 256L;
        }

        @Override
        public boolean supportsFileAttributeView(Class<?> type) {
            return false;
        }

        @Override
        public boolean supportsFileAttributeView(String name) {
            return false;
        }

        @Override
        public <V extends java.nio.file.attribute.FileStoreAttributeView> V getFileStoreAttributeView(Class<V> type) {
            return null;
        }

        @Override
        public Object getAttribute(String attribute) throws IOException {
            throw new UnsupportedOperationException();
        }

        @Override
        public long getBlockSize() throws IOException {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        TestFileStore fs = new TestFileStore();
        try {
            fs.getBlockSize();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }
}
