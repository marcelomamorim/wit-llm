/*
 * @test
 * @summary Test RandomAccessFile.readFully(byte[]) method behavior
 * @run main RandomAccessFileWitupTest
 */

import java.io.RandomAccessFile;
import java.io.File;
import java.io.IOException;
import java.io.EOFException;

public class RandomAccessFileWitupTest {
    public static void main(String[] args) throws Exception {
        testReadFullyWithValidData();
        testReadFullyWithEmptyArray();
        testReadFullyWithClosedFile();
    }

    private static void testReadFullyWithValidData() throws IOException {
        File tempFile = File.createTempFile("rafTest", ".tmp");
        tempFile.deleteOnExit();

        byte[] data = new byte[] {10, 20, 30, 40, 50};

        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            raf.write(data);
            raf.seek(0);

            byte[] buffer = new byte[data.length];
            raf.readFully(buffer);

            for (int i = 0; i < data.length; i++) {
                if (buffer[i] != data[i]) {
                    throw new RuntimeException("Data mismatch at index " + i);
                }
            }
        }
    }

    private static void testReadFullyWithEmptyArray() throws IOException {
        File tempFile = File.createTempFile("rafTestEmpty", ".tmp");
        tempFile.deleteOnExit();

        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            // Write some data
            raf.write(new byte[] {1, 2, 3});
            raf.seek(0);

            byte[] emptyBuffer = new byte[0];
            // Should not throw and do nothing
            raf.readFully(emptyBuffer);
        }
    }

    private static void testReadFullyWithClosedFile() throws IOException {
        File tempFile = File.createTempFile("rafTestClosed", ".tmp");
        tempFile.deleteOnExit();

        RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
        raf.close();

        byte[] buffer = new byte[1];
        try {
            raf.readFully(buffer);
            throw new RuntimeException("Expected IOException when reading from closed file");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
