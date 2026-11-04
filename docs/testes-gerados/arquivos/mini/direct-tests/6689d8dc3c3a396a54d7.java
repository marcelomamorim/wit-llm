/*
 * @test
 * @summary Test FileStore.getBlockSize() throws UnsupportedOperationException
 * @run main FileStoreWitupTest
 */

import java.nio.file.FileStore;
import java.io.IOException;

public class FileStoreWitupTest {
    public static void main(String[] args) throws IOException {
        FileStore fs = new FileStore() {
            @Override
            public String name() {
                return "dummy";
            }

            @Override
            public String type() {
                return "dummyType";
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
        };

        boolean threw = false;
        try {
            fs.getBlockSize();
        } catch (UnsupportedOperationException e) {
            threw = true;
        }

        if (!threw) {
            throw new AssertionError("Expected UnsupportedOperationException from getBlockSize()");
        }
    }
}
