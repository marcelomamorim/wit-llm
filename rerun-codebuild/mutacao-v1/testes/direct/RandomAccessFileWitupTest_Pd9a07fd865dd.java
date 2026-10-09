/* @test    @summary Test readShort() method of RandomAccessFile for correct behavior and EOF handling    @run main RandomAccessFileWitupTest_Pd9a07fd865dd */

import java.io.*;

public class RandomAccessFileWitupTest_Pd9a07fd865dd {

    public static void main(String[] args) throws Exception {
        testReadShortNormal();
        testReadShortEOF();
    }

    private static void testReadShortNormal() throws Exception {
        File tempFile = File.createTempFile("test", ".bin");
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            // Write known short value: 0x1234
            raf.writeByte(0x12);
            raf.writeByte(0x34);
            raf.seek(0);
            short result = raf.readShort();
            if (result != (short) ((0x12 << 8) + 0x34)) {
                throw new AssertionError("Expected 0x1234, got: " + Integer.toHexString(result & 0xFFFF));
            }
        }
    }

    private static void testReadShortEOF() throws Exception {
        File tempFile = File.createTempFile("testEOF", ".bin");
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            // Write only one byte to cause EOF on readShort
            raf.writeByte(0x12);
            raf.seek(0);
            try {
                raf.readShort();
                throw new AssertionError("Expected EOFException not thrown");
            } catch (EOFException e) {
                // Expected exception
            }
        }
    }
}
