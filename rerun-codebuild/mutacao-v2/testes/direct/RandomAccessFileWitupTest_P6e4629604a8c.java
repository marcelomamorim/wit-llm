/*
 * @test
 * @summary Test RandomAccessFile.readShort() behavior including EOFException
 * @run main RandomAccessFileWitupTest_P6e4629604a8c
 */

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest_P6e4629604a8c {
    public static void main(String[] args) throws Exception {
        testReadShortNormal();
        testReadShortEOF();
    }

    private static void testReadShortNormal() throws IOException {
        File tempFile = File.createTempFile("rafTest", ".tmp");
        tempFile.deleteOnExit();

        RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
        try {
            // Write two bytes representing a short: 0x1234
            raf.writeByte(0x12);
            raf.writeByte(0x34);
            raf.seek(0);

            short value = raf.readShort();
            if (value != 0x1234) {
                throw new AssertionError("Expected 0x1234 but got " + Integer.toHexString(value & 0xFFFF));
            }
        } finally {
            raf.close();
        }
    }

    private static void testReadShortEOF() throws IOException {
        File tempFile = File.createTempFile("rafTestEOF", ".tmp");
        tempFile.deleteOnExit();

        RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
        try {
            // Write only one byte
            raf.writeByte(0x12);
            raf.seek(0);

            try {
                raf.readShort();
                throw new AssertionError("Expected EOFException not thrown");
            } catch (EOFException e) {
                // Expected
            }
        } finally {
            raf.close();
        }
    }
}
