/* @test    @summary Test for RandomAccessFile.readInt() method    @run main RandomAccessFileWitupTest_P0f1a1afa5581 */

import java.io.*;

public class RandomAccessFileWitupTest_P0f1a1afa5581 {

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
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            // Write only 3 bytes to simulate EOF during readInt
            raf.write(new byte[] {0x01, 0x02, 0x03});
            raf.seek(0);
            try {
                raf.readInt();
                throw new AssertionError("Expected EOFException was not thrown");
            } catch (EOFException e) {
                // Expected exception
            }
        }
    }
}
