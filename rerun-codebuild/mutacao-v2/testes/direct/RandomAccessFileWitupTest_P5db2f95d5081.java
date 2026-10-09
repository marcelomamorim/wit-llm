/*
 * @test
 * @summary Test java.io.RandomAccessFile.readBoolean() method behavior
 * @run main RandomAccessFileWitupTest_P5db2f95d5081
 */

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest_P5db2f95d5081 {
    public static void main(String[] args) throws Exception {
        testReadBooleanTrue();
        testReadBooleanFalse();
        testReadBooleanEOF();
    }

    private static void testReadBooleanTrue() throws IOException {
        File tempFile = File.createTempFile("rafTestTrue", ".tmp");
        tempFile.deleteOnExit();
        RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
        try {
            raf.write(1); // write non-zero byte
            raf.seek(0);
            boolean result = raf.readBoolean();
            if (!result) {
                throw new AssertionError("Expected true but got false");
            }
        } finally {
            raf.close();
        }
    }

    private static void testReadBooleanFalse() throws IOException {
        File tempFile = File.createTempFile("rafTestFalse", ".tmp");
        tempFile.deleteOnExit();
        RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
        try {
            raf.write(0); // write zero byte
            raf.seek(0);
            boolean result = raf.readBoolean();
            if (result) {
                throw new AssertionError("Expected false but got true");
            }
        } finally {
            raf.close();
        }
    }

    private static void testReadBooleanEOF() throws IOException {
        File tempFile = File.createTempFile("rafTestEOF", ".tmp");
        tempFile.deleteOnExit();
        RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
        try {
            // Do not write anything, file is empty
            raf.seek(0);
            try {
                raf.readBoolean();
                throw new AssertionError("Expected EOFException but none thrown");
            } catch (EOFException e) {
                // Expected
            }
        } finally {
            raf.close();
        }
    }
}
