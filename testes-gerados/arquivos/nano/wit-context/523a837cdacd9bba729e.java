/*
 * @test
 * @summary Test for RandomAccessFile.readUnsignedByte() method.
 * @run main RandomAccessFileWitupTest
 */

import java.io.RandomAccessFile;
import java.io.IOException;

public class RandomAccessFileWitupTest {

    public static void main(String[] args) {
        testReadUnsignedByteNormal();
        testReadUnsignedByteEOF();
    }

    private static void testReadUnsignedByteNormal() {
        try {
            // Prepare a temporary file with known bytes
            java.io.File tempFile = java.io.File.createTempFile("test", null);
            tempFile.deleteOnExit();
            try (java.io.RandomAccessFile raf = new java.io.RandomAccessFile(tempFile, "rw")) {
                raf.write(new byte[] {0x01, 0x7F, (byte)0xFF});
                raf.seek(0);
                int byte1 = raf.readUnsignedByte();
                int byte2 = raf.readUnsignedByte();
                int byte3 = raf.readUnsignedByte();
                if (byte1 != 1 || byte2 != 127 || byte3 != 255) {
                    throw new AssertionError("Unexpected byte values: " + byte1 + ", " + byte2 + ", " + byte3);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("IOException during normal readUnsignedByte test", e);
        }
    }

    private static void testReadUnsignedByteEOF() {
        try {
            java.io.File tempFile = java.io.File.createTempFile("testEOF", null);
            tempFile.deleteOnExit();
            try (java.io.RandomAccessFile raf = new java.io.RandomAccessFile(tempFile, "r")) {
                // Read entire file to reach EOF
                while (raf.read() != -1) {
                    // consume bytes
                }
                // Now attempt to read beyond EOF
                int result = raf.readUnsignedByte();
                throw new AssertionError("Expected EOFException, but got result: " + result);
            } catch (java.io.EOFException eof) {
                // Expected exception
            }
        } catch (IOException e) {
            throw new RuntimeException("IOException during EOF readUnsignedByte test", e);
        }
    }
}
