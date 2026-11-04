/*
 * @test
 * @summary Test java.io.RandomAccessFile.readByte() method behavior
 * @run main RandomAccessFileWitupTest
 */

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest {
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

            byte b = raf.readByte();
            if (b != 0x7F) {
                throw new AssertionError("Expected byte 0x7F but got " + b);
            }
        } finally {
            raf.close();
        }
    }

    private static void testReadByteThrowsEOFExceptionAtEOF() throws IOException {
        File tempFile = File.createTempFile("rafTestEOF", ".tmp");
        tempFile.deleteOnExit();

        RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
        try {
            // File is empty, reading should throw EOFException
            try {
                raf.readByte();
                throw new AssertionError("Expected EOFException not thrown");
            } catch (EOFException expected) {
                // Expected exception
            }
        } finally {
            raf.close();
        }
    }
}
