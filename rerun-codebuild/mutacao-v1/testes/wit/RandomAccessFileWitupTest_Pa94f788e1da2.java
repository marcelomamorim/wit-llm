/* @test    @summary Test for RandomAccessFile.readFully(byte[], int, int) handling EOFException    @run main RandomAccessFileWitupTest_Pa94f788e1da2 */

import java.io.RandomAccessFile;
import java.io.EOFException;
import java.io.IOException;

public class RandomAccessFileWitupTest_Pa94f788e1da2 {

    public static void main(String[] args) throws Exception {
        testReadFullyNormal();
        testReadFullyEOF();
    }

    private static void testReadFullyNormal() throws IOException {
        byte[] buffer = new byte[10];
        try (RandomAccessFile raf = new RandomAccessFile("testfile", "rw")) {
            raf.setLength(10);
            raf.seek(0);
            // Write some data to the file
            for (int i = 0; i < 10; i++) {
                raf.writeByte(i);
            }
            raf.seek(0);
            // Read fully
            raf.readFully(buffer, 0, 10);
            for (int i = 0; i < 10; i++) {
                if (buffer[i] != i) {
                    throw new AssertionError("Data mismatch at index " + i);
                }
            }
        }
    }

    private static void testReadFullyEOF() throws IOException {
        byte[] buffer = new byte[10];
        try (RandomAccessFile raf = new RandomAccessFile("testfile", "r")) {
            raf.setLength(5); // set file length less than buffer size
            raf.seek(0);
            // Fill file with 5 bytes
            for (int i = 0; i < 5; i++) {
                raf.writeByte(i);
            }
            raf.seek(0);
            // Attempt to read 10 bytes, expect EOFException
            try {
                raf.readFully(buffer, 0, 10);
                throw new AssertionError("Expected EOFException was not thrown");
            } catch (EOFException e) {
                // Expected exception
            }
        }
    }
}
