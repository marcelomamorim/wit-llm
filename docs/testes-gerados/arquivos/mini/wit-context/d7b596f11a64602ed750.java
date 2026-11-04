/*
 * @test
 * @summary Test deterministic behavior of java.io.RandomAccessFile.readInt()
 * @run main RandomAccessFileWitupTest
 */

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest {
    public static void main(String[] args) throws Exception {
        testReadIntNormal();
        testReadIntEOFException();
    }

    private static void testReadIntNormal() throws IOException {
        File tempFile = File.createTempFile("rafTest", ".tmp");
        tempFile.deleteOnExit();

        RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
        try {
            // Write 4 bytes representing int 0x01020304
            raf.write(new byte[] {1, 2, 3, 4});
            raf.seek(0);

            int value = raf.readInt();
            if (value != 0x01020304) {
                throw new AssertionError("Expected 0x01020304 but got " + Integer.toHexString(value));
            }
        } finally {
            raf.close();
        }
    }

    private static void testReadIntEOFException() throws IOException {
        File tempFile = File.createTempFile("rafTestEOF", ".tmp");
        tempFile.deleteOnExit();

        RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
        try {
            // Write only 3 bytes, less than 4 needed for readInt
            raf.write(new byte[] {1, 2, 3});
            raf.seek(0);

            boolean threwEOF = false;
            try {
                raf.readInt();
            } catch (EOFException e) {
                threwEOF = true;
            }
            if (!threwEOF) {
                throw new AssertionError("Expected EOFException when reading int with insufficient bytes");
            }
        } finally {
            raf.close();
        }
    }
}
