/*
 * @test
 * @summary Test RandomAccessFile.readChar() method behavior
 * @run main RandomAccessFileWitupTest
 */

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest {
    public static void main(String[] args) throws Exception {
        testReadCharNormal();
        testReadCharEOF();
    }

    private static void testReadCharNormal() throws IOException {
        File tempFile = File.createTempFile("rafTest", ".tmp");
        tempFile.deleteOnExit();

        RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
        try {
            // Write two bytes representing a char: 0x41 0x42 ('A' 'B')
            raf.writeByte(0x41);
            raf.writeByte(0x42);
            raf.seek(0);

            char c = raf.readChar();
            // Expected char is (0x41 << 8) + 0x42 = 0x4142
            if (c != 0x4142) {
                throw new AssertionError("Expected char 0x4142 but got: 0x" + Integer.toHexString(c));
            }
        } finally {
            raf.close();
        }
    }

    private static void testReadCharEOF() throws IOException {
        File tempFile = File.createTempFile("rafTestEOF", ".tmp");
        tempFile.deleteOnExit();

        RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
        try {
            // Write only one byte
            raf.writeByte(0x41);
            raf.seek(0);

            boolean threwEOF = false;
            try {
                raf.readChar();
            } catch (EOFException e) {
                threwEOF = true;
            }

            if (!threwEOF) {
                throw new AssertionError("Expected EOFException when reading char with insufficient bytes");
            }
        } finally {
            raf.close();
        }
    }
}
