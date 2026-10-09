/*
 * @test
 * @summary Test deterministic behavior of java.io.RandomAccessFile.readByte()
 * @run main RandomAccessFileWitupTest_P8b8058fe199b
 */

import java.io.RandomAccessFile;
import java.io.File;
import java.io.EOFException;
import java.io.IOException;

public class RandomAccessFileWitupTest_P8b8058fe199b {
    public static void main(String[] args) throws Exception {
        testReadByteReturnsCorrectValue();
        testReadByteThrowsEOFExceptionAtEOF();
    }

    private static void testReadByteReturnsCorrectValue() throws IOException {
        File tempFile = File.createTempFile("rafTest", ".tmp");
        tempFile.deleteOnExit();

        RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
        try {
            // Write a single byte
            raf.writeByte(0x7F); // 127 decimal
            raf.seek(0);

            // readByte should return the same byte
            byte b = raf.readByte();
            if (b != 0x7F) {
                throw new AssertionError("Expected 0x7F but got " + b);
            }

            // After reading the only byte, next readByte should throw EOFException
            try {
                raf.readByte();
                throw new AssertionError("Expected EOFException not thrown");
            } catch (EOFException expected) {
                // expected
            }
        } finally {
            raf.close();
            tempFile.delete();
        }
    }

    private static void testReadByteThrowsEOFExceptionAtEOF() throws IOException {
        File tempFile = File.createTempFile("rafTestEOF", ".tmp");
        tempFile.deleteOnExit();

        RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
        try {
            // File is empty, so readByte should throw EOFException immediately
            try {
                raf.readByte();
                throw new AssertionError("Expected EOFException on empty file");
            } catch (EOFException expected) {
                // expected
            }
        } finally {
            raf.close();
            tempFile.delete();
        }
    }
}
