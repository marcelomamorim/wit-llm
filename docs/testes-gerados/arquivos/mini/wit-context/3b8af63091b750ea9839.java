/*
 * @test
 * @summary Test deterministic behavior of java.io.RandomAccessFile.readChar()
 * @run main RandomAccessFileWitupTest
 */

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest {
    public static void main(String[] args) throws Exception {
        testReadCharNormal();
        testReadCharEOFException();
    }

    private static void testReadCharNormal() throws IOException {
        File tempFile = File.createTempFile("rafTest", ".tmp");
        tempFile.deleteOnExit();

        RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
        try {
            // Write two bytes representing a char
            raf.writeByte(0x12);
            raf.writeByte(0x34);
            raf.seek(0);

            char c = raf.readChar();
            // The char should be (0x12 << 8) + 0x34 = 0x1234
            if (c != 0x1234) {
                throw new AssertionError("Expected char 0x1234 but got: " + Integer.toHexString(c));
            }
        } finally {
            raf.close();
        }
    }

    private static void testReadCharEOFException() throws IOException {
        File tempFile = File.createTempFile("rafTestEOF", ".tmp");
        tempFile.deleteOnExit();

        RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
        try {
            // Write only one byte
            raf.writeByte(0x12);
            raf.seek(0);

            try {
                raf.readChar();
                throw new AssertionError("Expected EOFException not thrown");
            } catch (EOFException e) {
                // Expected exception
            }
        } finally {
            raf.close();
        }
    }
}
