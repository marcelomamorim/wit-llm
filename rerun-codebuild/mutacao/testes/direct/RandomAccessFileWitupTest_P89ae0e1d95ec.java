/* @test    @summary Test readShort() method of RandomAccessFile for correct reading of two bytes as a short.    @run main RandomAccessFileWitupTest_P89ae0e1d95ec */

import java.io.*;

public class RandomAccessFileWitupTest_P89ae0e1d95ec {

    public static void main(String[] args) throws IOException {
        testReadShortPositive();
        testReadShortEOF();
        testReadShortPartial();
    }

    private static void testReadShortPositive() throws IOException {
        File tempFile = File.createTempFile("test", ".bin");
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            raf.writeByte(0x12);
            raf.writeByte(0x34);
            raf.seek(0);
            short result = raf.readShort();
            if (result != (short) ((0x12 << 8) + 0x34)) {
                throw new AssertionError("Expected 0x1234, got: " + Integer.toHexString(result & 0xFFFF));
            }
        }
    }

    private static void testReadShortEOF() throws IOException {
        File tempFile = File.createTempFile("testEOF", ".bin");
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            raf.writeByte(0x12);
            raf.seek(0);
            raf.readByte(); // read one byte
            try {
                raf.readShort();
                throw new AssertionError("Expected EOFException not thrown");
            } catch (EOFException e) {
                // expected
            }
        }
    }

    private static void testReadShortPartial() throws IOException {
        File tempFile = File.createTempFile("testPartial", ".bin");
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            raf.writeByte(0x12);
            raf.seek(0);
            try {
                raf.readShort();
                throw new AssertionError("Expected EOFException not thrown");
            } catch (EOFException e) {
                // expected
            }
        }
    }
}
