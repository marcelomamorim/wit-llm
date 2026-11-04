/*
 * @test
 * @summary Test for readUnsignedShort method in RandomAccessFile
 * @run main RandomAccessFileWitupTest
 */

import java.io.RandomAccessFile;
import java.io.IOException;

public class RandomAccessFileWitupTest {

    public static void main(String[] args) {
        testReadUnsignedShortNormal();
        testReadUnsignedShortEOF();
    }

    private static void testReadUnsignedShortNormal() {
        try {
            // Prepare a file with known bytes
            java.io.File tempFile = java.io.File.createTempFile("test", null);
            tempFile.deleteOnExit();
            try (java.io.RandomAccessFile raf = new java.io.RandomAccessFile(tempFile, "rw")) {
                // Write two bytes: 0x12 0x34
                raf.write(0x12);
                raf.write(0x34);
                raf.seek(0);
                int result = raf.readUnsignedShort();
                if (result != ((0x12 << 8) + 0x34)) {
                    throw new AssertionError("Expected 0x1234, got: " + Integer.toHexString(result));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void testReadUnsignedShortEOF() {
        try {
            java.io.File tempFile = java.io.File.createTempFile("testEOF", null);
            tempFile.deleteOnExit();
            try (java.io.RandomAccessFile raf = new java.io.RandomAccessFile(tempFile, "rw")) {
                // Write only one byte
                raf.write(0x56);
                raf.seek(0);
                // Read first byte normally
                int firstByte = raf.readUnsignedShort();
                throw new AssertionError("Expected EOFException, but read: " + firstByte);
            }
        } catch (IOException e) {
            if (e instanceof java.io.EOFException) {
                // Expected exception
            } else {
                throw new RuntimeException(e);
            }
        }
    }
}
