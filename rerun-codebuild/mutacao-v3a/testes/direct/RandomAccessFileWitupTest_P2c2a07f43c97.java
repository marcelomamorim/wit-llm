/* @test    @summary Test readShort() method of RandomAccessFile for correctness and EOF behavior    @run main RandomAccessFileWitupTest_P2c2a07f43c97 */

import java.io.*;

public class RandomAccessFileWitupTest_P2c2a07f43c97 {

    public static void main(String[] args) throws IOException {
        testReadShortNormal();
        testReadShortEOF();
    }

    private static void testReadShortNormal() throws IOException {
        File tempFile = File.createTempFile("testReadShort", ".bin");
        try {
            try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
                raf.writeShort(0x1234);
                raf.seek(0);
                short value = raf.readShort();
                if (value != (short) 0x1234) {
                    throw new AssertionError("Expected 0x1234, but got: " + Integer.toHexString(value & 0xFFFF));
                }
            }
        } finally {
            tempFile.delete();
        }
    }

    private static void testReadShortEOF() throws IOException {
        File tempFile = File.createTempFile("testReadShortEOF", ".bin");
        try {
            try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
                raf.writeInt(0x12345678); // Write more than 2 bytes
                raf.seek(0);
                raf.readShort(); // Read first short
                raf.readShort(); // Read second short
                // Now reading beyond EOF should throw EOFException
                boolean threw = false;
                try {
                    raf.readShort();
                } catch (EOFException e) {
                    threw = true;
                }
                if (!threw) {
                    throw new AssertionError("Expected EOFException when reading beyond EOF");
                }
            }
        } finally {
            tempFile.delete();
        }
    }
}
