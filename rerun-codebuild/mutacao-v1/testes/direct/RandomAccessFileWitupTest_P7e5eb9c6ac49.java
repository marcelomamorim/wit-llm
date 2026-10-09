/* @test    @summary Test readByte() method of RandomAccessFile for normal and EOF conditions    @run main RandomAccessFileWitupTest_P7e5eb9c6ac49 */

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest_P7e5eb9c6ac49 {

    public static void main(String[] args) throws IOException {
        testReadByteNormal();
        testReadByteEOF();
    }

    private static void testReadByteNormal() throws IOException {
        // Create a temporary file with known content
        String filename = "testfile.bin";
        try (RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
            // Write a byte value
            raf.writeByte(0x5A); // 90 in decimal
            // Seek to beginning
            raf.seek(0);
            // Read byte
            byte result = raf.readByte();
            if (result != (byte)0x5A) {
                throw new AssertionError("Expected 0x5A, got " + result);
            }
        }
        // Delete the file after test
        java.io.File f = new java.io.File("testfile.bin");
        if (f.exists() && !f.delete()) {
            throw new RuntimeException("Failed to delete test file");
        }
    }

    private static void testReadByteEOF() throws IOException {
        String filename = "testfile_eof.bin";
        try (RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
            // Write a single byte
            raf.writeByte(0x7F); // 127 in decimal
            // Seek to beginning
            raf.seek(0);
            // Read the byte
            byte b = raf.readByte();
            if (b != (byte)0x7F) {
                throw new AssertionError("Expected 0x7F, got " + b);
            }
            // Seek beyond end to trigger EOF
            raf.seek(1);
            try {
                raf.readByte();
                throw new AssertionError("Expected EOFException not thrown");
            } catch (EOFException e) {
                // Expected exception
            }
        }
        // Delete the file after test
        java.io.File f = new java.io.File("testfile_eof.bin");
        if (f.exists() && !f.delete()) {
            throw new RuntimeException("Failed to delete test file");
        }
    }
}
