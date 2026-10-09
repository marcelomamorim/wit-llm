/* @test    @summary Test readShort method for normal and EOF conditions    @run main RandomAccessFileWitupTest_Pd9a07fd865dd */

import java.io.File;
import java.io.RandomAccessFile;
import java.io.IOException;

public class RandomAccessFileWitupTest_Pd9a07fd865dd {

    public static void main(String[] args) throws Exception {
        testReadShortNormal();
        testReadShortEOF();
    }

    private static void testReadShortNormal() throws IOException {
        File tempFile = File.createTempFile("test", null);
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            // Write two bytes representing a short value
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
        File tempFile = File.createTempFile("testEOF", null);
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            // Write only one byte to cause EOFException on readShort
            raf.writeByte(0x12);
            raf.seek(0);
            try {
                raf.readShort();
                throw new AssertionError("Expected EOFException not thrown");
            } catch (IOException e) {
                if (!(e instanceof java.io.EOFException)) {
                    throw new AssertionError("Expected EOFException, got: " + e);
                }
            }
        }
    }
}
