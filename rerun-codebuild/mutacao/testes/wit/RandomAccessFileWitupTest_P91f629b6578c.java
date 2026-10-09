/*
 * @test
 * @summary Tests for java.io.RandomAccessFile.readFully(byte[], int, int)
 * @run main RandomAccessFileWitupTest_P91f629b6578c
 */

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.File;

public class RandomAccessFileWitupTest_P91f629b6578c {

    public static void main(String[] args) throws Exception {
        testReadFullyNormal();
        testReadFullyEOF();
    }

    private static void testReadFullyNormal() throws IOException {
        File tempFile = File.createTempFile("test", null);
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            byte[] data = {1, 2, 3, 4, 5};
            raf.write(data);
            raf.seek(0);
            byte[] buffer = new byte[5];
            raf.readFully(buffer, 0, buffer.length);
            boolean success = true;
            for (int i = 0; i < buffer.length; i++) {
                if (buffer[i] != data[i]) {
                    success = false;
                    break;
                }
            }
            if (!success) {
                throw new AssertionError("readFully did not read expected data");
            }
        }
    }

    private static void testReadFullyEOF() throws IOException {
        File tempFile = File.createTempFile("testEOF", null);
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "r")) {
            byte[] buffer = new byte[10];
            try {
                raf.readFully(buffer, 0, buffer.length);
                throw new AssertionError("Expected EOFException was not thrown");
            } catch (EOFException e) {
                // Expected exception
            }
        }
    }
}
