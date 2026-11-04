/*
 * @test
 * @summary Test readFully(byte[] b) method of RandomAccessFile
 * @run main RandomAccessFileWitupTest
 */

import java.io.RandomAccessFile;
import java.io.IOException;

public class RandomAccessFileWitupTest {

    public static void main(String[] args) throws IOException {
        testReadFullyNormal();
        testReadFullyEmptyBuffer();
        testReadFullyPartialBuffer();
    }

    private static void testReadFullyNormal() throws IOException {
        byte[] data = {10, 20, 30, 40, 50};
        try (RandomAccessFile raf = new RandomAccessFile("testfile.bin", "rw")) {
            raf.setLength(0);
            raf.write(data);
            raf.seek(0);
            byte[] buffer = new byte[data.length];
            raf.readFully(buffer);
            for (int i = 0; i < data.length; i++) {
                if (buffer[i] != data[i]) {
                    throw new AssertionError("Data mismatch at index " + i);
                }
            }
        }
    }

    private static void testReadFullyEmptyBuffer() throws IOException {
        byte[] buffer = {};
        try (RandomAccessFile raf = new RandomAccessFile("testfile_empty.bin", "rw")) {
            raf.setLength(0);
            raf.write(new byte[]{1, 2, 3});
            raf.seek(0);
            raf.readFully(buffer); // should not throw
        }
    }

    private static void testReadFullyPartialBuffer() throws IOException {
        byte[] data = {5, 6, 7, 8};
        try (RandomAccessFile raf = new RandomAccessFile("testfile_partial.bin", "rw")) {
            raf.setLength(0);
            raf.write(data);
            raf.seek(0);
            byte[] buffer = new byte[2];
            raf.readFully(buffer); // read first 2 bytes
            if (buffer[0] != data[0] || buffer[1] != data[1]) {
                throw new AssertionError("Partial read data mismatch");
            }
        }
    }
}
