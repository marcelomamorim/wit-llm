/* @test    @summary Test for RandomAccessFile.readInt() method    @run main RandomAccessFileWitupTest */

import java.io.*;

public class RandomAccessFileWitupTest {

    public static void main(String[] args) throws Exception {
        testReadIntPositive();
        testReadIntNegative();
        testReadIntEOF();
    }

    private static void testReadIntPositive() throws Exception {
        File tempFile = File.createTempFile("test", ".bin");
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            int value = 0x01020304;
            raf.writeInt(value);
            raf.seek(0);
            int result = raf.readInt();
            if (result != value) {
                throw new AssertionError("Expected: " + value + ", but got: " + result);
            }
        }
    }

    private static void testReadIntNegative() throws Exception {
        File tempFile = File.createTempFile("test", ".bin");
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            int value = 0xFFEECCDD;
            raf.writeInt(value);
            raf.seek(0);
            int result = raf.readInt();
            if (result != value) {
                throw new AssertionError("Expected: " + value + ", but got: " + result);
            }
        }
    }

    private static void testReadIntEOF() throws Exception {
        File tempFile = File.createTempFile("test", ".bin");
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "r")) {
            // Do not write anything, just seek to end
            raf.seek(0);
            // Read beyond EOF to trigger EOFException
            try {
                raf.readInt();
                throw new AssertionError("Expected EOFException was not thrown");
            } catch (EOFException e) {
                // Expected exception
            }
        }
    }
}
