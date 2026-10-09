/* @test    @summary Test for readUnsignedByte method in RandomAccessFile    @run main RandomAccessFileWitupTest_P1ae149ac2ce4 */

import java.io.RandomAccessFile;
import java.io.IOException;

public class RandomAccessFileWitupTest_P1ae149ac2ce4 {

    public static void main(String[] args) {
        testReadUnsignedByteNormal();
        testReadUnsignedByteEOF();
    }

    private static void testReadUnsignedByteNormal() {
        try {
            // Setup: create a temporary file with known bytes
            java.io.File tempFile = java.io.File.createTempFile("test", null);
            tempFile.deleteOnExit();
            try (java.io.RandomAccessFile raf = new java.io.RandomAccessFile(tempFile, "rw")) {
                raf.write(new byte[]{0x01, 0x7F, (byte)0xFF});
                raf.seek(0);
                int b1 = raf.readUnsignedByte();
                int b2 = raf.readUnsignedByte();
                int b3 = raf.readUnsignedByte();
                if (b1 != 1 || b2 != 127 || b3 != 255) {
                    throw new RuntimeException("Unexpected byte values: " + b1 + ", " + b2 + ", " + b3);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("IOException during testReadUnsignedByteNormal", e);
        }
    }

    private static void testReadUnsignedByteEOF() {
        try {
            // Setup: create a file with no content
            java.io.File emptyFile = java.io.File.createTempFile("empty", null);
            emptyFile.deleteOnExit();
            try (java.io.RandomAccessFile raf = new java.io.RandomAccessFile(emptyFile, "r")) {
                // Move to end of file
                raf.seek(raf.length());
                // Read beyond EOF, expect EOFException
                int result = -1;
                boolean exceptionThrown = false;
                try {
                    result = raf.readUnsignedByte();
                } catch (IOException e) {
                    if (e instanceof java.io.EOFException) {
                        exceptionThrown = true;
                    } else {
                        throw e;
                    }
                }
                if (!exceptionThrown) {
                    throw new RuntimeException("Expected EOFException was not thrown");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("IOException during testReadUnsignedByteEOF", e);
        }
    }
}
