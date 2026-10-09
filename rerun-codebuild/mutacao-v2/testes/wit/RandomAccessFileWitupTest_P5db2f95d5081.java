/*
 * @test
 * @summary Test deterministic behavior of RandomAccessFile.readBoolean()
 * @run main RandomAccessFileWitupTest_P5db2f95d5081
 */

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest_P5db2f95d5081 {
    public static void main(String[] args) throws Exception {
        testReadBooleanReturnsTrue();
        testReadBooleanReturnsFalse();
        testReadBooleanThrowsEOFException();
    }

    private static void testReadBooleanReturnsTrue() throws IOException {
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

    private static void testReadBooleanReturnsFalse() throws IOException {
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

    private static void testReadBooleanThrowsEOFException() throws IOException {
        File tempFile = File.createTempFile("rafTestEOF", ".tmp");
        tempFile.deleteOnExit();
        RandomAccessFile raf = new RandomAccessFile(tempFile, "r");
        try {
            // File is empty, so readBoolean should throw EOFException
            try {
                raf.readBoolean();
                throw new AssertionError("Expected EOFException was not thrown");
            } catch (EOFException expected) {
                // Expected exception
            }
        } finally {
            raf.close();
        }
    }
}
