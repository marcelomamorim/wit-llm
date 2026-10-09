/* @test    @summary Test for readShort method in RandomAccessFile to verify behavior at EOF    @run main RandomAccessFileWitupTest_P89ae0e1d95ec */

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest_P89ae0e1d95ec {

    public static void main(String[] args) throws Exception {
        testReadShortNormal();
        testReadShortEOF();
    }

    private static void testReadShortNormal() throws IOException {
        File tempFile = File.createTempFile("test", null);
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            raf.write(new byte[] {0x01, 0x02, 0x03, 0x04});
            raf.seek(0);
            short result = raf.readShort();
            if (result != (short)((0x01 << 8) + 0x02)) {
                throw new AssertionError("Expected 258, but got " + result);
            }
        } finally {
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    private static void testReadShortEOF() throws IOException {
        File tempFile = File.createTempFile("test", null);
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            raf.write(new byte[] {0x01}); // only 1 byte
            raf.seek(0);
            // Read first byte successfully
            raf.read();
            // Next read should trigger EOFException
            try {
                raf.readShort();
                throw new AssertionError("Expected EOFException to be thrown");
            } catch (IOException e) {
                if (!(e instanceof java.io.EOFException)) {
                    throw e;
                }
            }
        } finally {
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }
}
