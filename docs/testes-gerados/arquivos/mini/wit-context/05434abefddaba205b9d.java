/*
 * @test
 * @summary Test deterministic behavior of RandomAccessFile.readFully(byte[])
 * @run main RandomAccessFileWitupTest
 */

import java.io.File;
import java.io.RandomAccessFile;
import java.io.EOFException;
import java.io.IOException;

public class RandomAccessFileWitupTest {
    public static void main(String[] args) throws Exception {
        testReadFullyExactLength();
        testReadFullyEOFException();
    }

    private static void testReadFullyExactLength() throws IOException {
        File tempFile = File.createTempFile("rafTest", ".tmp");
        tempFile.deleteOnExit();

        byte[] data = new byte[] {1, 2, 3, 4, 5};

        // Write data to file
        RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
        raf.write(data);
        raf.seek(0);

        byte[] buffer = new byte[data.length];
        raf.readFully(buffer);
        raf.close();

        // Check that data read matches data written
        for (int i = 0; i < data.length; i++) {
            if (buffer[i] != data[i]) {
                throw new AssertionError("Data mismatch at index " + i);
            }
        }
    }

    private static void testReadFullyEOFException() throws IOException {
        File tempFile = File.createTempFile("rafTestEOF", ".tmp");
        tempFile.deleteOnExit();

        byte[] data = new byte[] {10, 20, 30};

        RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
        raf.write(data);
        raf.seek(0);

        byte[] buffer = new byte[data.length + 1]; // buffer larger than file content

        boolean threwEOF = false;
        try {
            raf.readFully(buffer);
        } catch (EOFException e) {
            threwEOF = true;
        } finally {
            raf.close();
        }

        if (!threwEOF) {
            throw new AssertionError("Expected EOFException was not thrown");
        }
    }
}
