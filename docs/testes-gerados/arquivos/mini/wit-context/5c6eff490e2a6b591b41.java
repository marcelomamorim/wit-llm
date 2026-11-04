/*
 * @test
 * @summary Test deterministic behavior of java.io.RandomAccessFile.readShort()
 * @run main RandomAccessFileWitupTest
 */

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest {
    public static void main(String[] args) throws Exception {
        testReadShortNormal();
        testReadShortEOFException();
    }

    private static void testReadShortNormal() throws IOException {
        File tempFile = File.createTempFile("rafTest", ".tmp");
        tempFile.deleteOnExit();

        RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
        try {
            // Write two bytes to read back as short
            raf.writeByte(0x12);
            raf.writeByte(0x34);
            raf.seek(0);

            short value = raf.readShort();
            // The expected value is (0x12 << 8) + (0x34 << 0) = 0x1234
            if (value != (short)0x1234) {
                throw new AssertionError("readShort returned wrong value: " + Integer.toHexString(value & 0xFFFF));
            }
        } finally {
            raf.close();
        }
    }

    private static void testReadShortEOFException() throws IOException {
        File tempFile = File.createTempFile("rafTestEOF", ".tmp");
        tempFile.deleteOnExit();

        RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
        try {
            // Write only one byte, so second read returns -1
            raf.writeByte(0x12);
            raf.seek(0);

            try {
                raf.readShort();
                throw new AssertionError("Expected EOFException not thrown");
            } catch (EOFException e) {
                // Expected exception
            }
        } finally {
            raf.close();
        }
    }
}
