/*
 * @test
 * @summary Test readUnsignedShort method of RandomAccessFile
 * @run main RandomAccessFileWitupTest
 */

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest {
    public static void main(String[] args) throws Exception {
        testReadUnsignedShortNormal();
        testReadUnsignedShortEOF();
    }

    private static void testReadUnsignedShortNormal() throws IOException {
        File tempFile = File.createTempFile("rafTest", ".tmp");
        tempFile.deleteOnExit();

        RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
        try {
            // Write two bytes: 0x12 0x34
            raf.writeByte(0x12);
            raf.writeByte(0x34);
            raf.seek(0);

            int result = raf.readUnsignedShort();
            // Expected: (0x12 << 8) + (0x34 << 0) = 0x1234 = 4660
            if (result != 0x1234) {
                throw new AssertionError("Expected 0x1234 but got " + Integer.toHexString(result));
            }
        } finally {
            raf.close();
        }
    }

    private static void testReadUnsignedShortEOF() throws IOException {
        File tempFile = File.createTempFile("rafTestEOF", ".tmp");
        tempFile.deleteOnExit();

        RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
        try {
            // Write only one byte
            raf.writeByte(0x12);
            raf.seek(0);

            boolean threwEOF = false;
            try {
                raf.readUnsignedShort();
            } catch (EOFException e) {
                threwEOF = true;
            }
            if (!threwEOF) {
                throw new AssertionError("Expected EOFException when reading unsigned short with insufficient bytes");
            }
        } finally {
            raf.close();
        }
    }
}
