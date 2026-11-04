/*
 * @test
 * @summary FileStore.getBlockSize() default implementation throws UnsupportedOperationException and can be overridden
 * @run main FileStoreWitupTest
 */

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileStoreAttributeView;

public class FileStoreWitupTest {

    static class StubFileStore extends FileStore {
        @Override
        public String name() { return "stub"; }

        @Override
        public String type() { return "stubtype"; }

        @Override
        public boolean isReadOnly() { return false; }

        @Override
        public long getTotalSpace() throws IOException { return 0L; }

        @Override
        public long getUsableSpace() throws IOException { return 0L; }

        @Override
        public long getUnallocatedSpace() throws IOException { return 0L; }

        @Override
        public boolean supportsFileAttributeView(Class<? extends FileAttributeView> type) { return false; }

        @Override
        public boolean supportsFileAttributeView(String name) { return false; }

        @Override
        public <V extends FileStoreAttributeView> V getFileStoreAttributeView(Class<V> type) { return null; }

        @Override
        public Object getAttribute(String attribute) throws IOException { return null; }
    }

    static class SizedStub extends StubFileStore {
        @Override
        public long getBlockSize() { return 4096L; }
    }

    public static void main(String[] args) throws Exception {
        testDefaultBlockSizeThrows();
        testOverriddenBlockSize();
    }

    static void testDefaultBlockSizeThrows() throws Exception {
        FileStore fs = new StubFileStore();
        try {
            fs.getBlockSize();
            throw new RuntimeException("expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected: base implementation throws
        }
    }

    static void testOverriddenBlockSize() throws Exception {
        FileStore fs = new SizedStub();
        long size = fs.getBlockSize();
        if (size != 4096L) {
            throw new RuntimeException("unexpected block size: " + size);
        }
    }
}
