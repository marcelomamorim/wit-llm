/* @test    @summary Test for getBlockSize method in FileStore class.
 * @run main FileStoreWitupTest
 */

import java.nio.file.*;
import java.io.IOException;

public class FileStoreWitupTest {

    public static void main(String[] args) {
        testGetBlockSizeThrowsUnsupported();
    }

    public static void testGetBlockSizeThrowsUnsupported() {
        FileStore fs = new DummyFileStore();
        try {
            long result = fs.getBlockSize();
            System.out.println("Unexpected success: result=" + result);
        } catch (UnsupportedOperationException e) {
            System.out.println("Caught expected UnsupportedOperationException");
        } catch (IOException e) {
            System.out.println("Unexpected IOException: " + e);
        } catch (Throwable t) {
            System.out.println("Unexpected exception: " + t);
        }
    }

    static class DummyFileStore extends FileStore {
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
            return 0;
        }

        public long getBlockSize() throws IOException {
            throw new UnsupportedOperationException();
        }
    }
}
