/*
 * @test
 * @summary Test java.io.RandomAccessFile.getFD() method behavior
 * @run main RandomAccessFileWitupTest
 */

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest {
    public static void main(String[] args) throws Exception {
        testGetFDReturnsNonNull();
        testGetFDThrowsIOExceptionOnClosedFile();
    }

    private static void testGetFDReturnsNonNull() throws IOException {
        File tempFile = File.createTempFile("rafTest", ".tmp");
        tempFile.deleteOnExit();
        RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
        try {
            FileDescriptor fd = raf.getFD();
            if (fd == null) {
                throw new AssertionError("getFD() returned null on open file");
            }
        } finally {
            raf.close();
        }
    }

    private static void testGetFDThrowsIOExceptionOnClosedFile() throws IOException {
        File tempFile = File.createTempFile("rafTest", ".tmp");
        tempFile.deleteOnExit();
        RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
        raf.close();
        try {
            raf.getFD();
            throw new AssertionError("getFD() did not throw IOException after close");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
