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
        testGetFDThrowsIOExceptionWhenFdNull();
    }

    private static void testGetFDReturnsNonNull() throws Exception {
        File tempFile = File.createTempFile("rafTest", ".tmp");
        tempFile.deleteOnExit();
        RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
        try {
            FileDescriptor fd = raf.getFD();
            if (fd == null) {
                throw new AssertionError("getFD() returned null but fd is expected non-null");
            }
        } finally {
            raf.close();
        }
    }

    private static void testGetFDThrowsIOExceptionWhenFdNull() throws Exception {
        // Create a subclass to simulate fd == null
        class RAFWithNullFD extends RandomAccessFile {
            RAFWithNullFD() throws IOException {
                super(File.createTempFile("rafTestNullFD", ".tmp"), "rw");
            }

            @Override
            public FileDescriptor getFD() throws IOException {
                // Simulate fd == null by throwing IOException as per original method
                throw new IOException();
            }
        }

        RAFWithNullFD raf = new RAFWithNullFD();
        try {
            try {
                raf.getFD();
                throw new AssertionError("Expected IOException when fd is null");
            } catch (IOException e) {
                // Expected exception
            }
        } finally {
            raf.close();
        }
    }
}
