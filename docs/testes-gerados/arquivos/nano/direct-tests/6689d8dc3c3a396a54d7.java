/* @test    @summary Test getBlockSize method of FileStore class.    @run main FileStoreWitupTest */

import java.nio.file.*;
import java.io.IOException;

public class FileStoreWitupTest {

    public static void main(String[] args) {
        testGetBlockSizeThrowsUnsupported();
    }

    public static void testGetBlockSizeThrowsUnsupported() {
        FileStore fs = new FileStore() {
            @Override
            public String name() { return "test"; }
            @Override
            public String type() { return "testType"; }
            @Override
            public boolean isReadOnly() { return false; }
            @Override
            public long getTotalSpace() throws IOException { return 0; }
            @Override
            public long getBlockSize() throws IOException {
                throw new UnsupportedOperationException();
            }
        };
        try {
            fs.getBlockSize();
            // Fail if no exception is thrown
            System.err.println("Expected UnsupportedOperationException was not thrown.");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Test passes
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
            System.exit(1);
        }
    }
}
