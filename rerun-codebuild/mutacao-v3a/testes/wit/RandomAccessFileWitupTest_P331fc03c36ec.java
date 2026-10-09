/* @test    @summary Test for readBoolean method in RandomAccessFile    @run main RandomAccessFileWitupTest_P331fc03c36ec */

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest_P331fc03c36ec {

    public static void main(String[] args) throws IOException {
        testReadBooleanNormal();
        testReadBooleanEOF();
    }

    private static void testReadBooleanNormal() throws IOException {
        File tempFile = File.createTempFile("temp", null);
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            // Write a byte with value 1 (true)
            raf.write(1);
            raf.seek(0);
            boolean result = raf.readBoolean();
            if (result != true) {
                throw new AssertionError("Expected true, got " + result);
            }
        } finally {
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    private static void testReadBooleanEOF() throws IOException {
        File tempFile = File.createTempFile("temp", null);
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "r")) {
            // No bytes written, just read
            raf.seek(0);
            try {
                raf.readBoolean();
                throw new AssertionError("Expected EOFException not thrown");
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
