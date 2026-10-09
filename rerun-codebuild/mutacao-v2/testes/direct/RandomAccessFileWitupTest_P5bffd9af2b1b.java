/*
 * @test
 * @summary Test RandomAccessFile.readInt() behavior including EOFException
 * @run main RandomAccessFileWitupTest_P5bffd9af2b1b
 */

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest_P5bffd9af2b1b {
    public static void main(String[] args) throws Exception {
        testReadIntNormal();
        testReadIntEOF();
    }

    private static void testReadIntNormal() throws IOException {
        File tempFile = File.createTempFile("rafTest", ".tmp");
        tempFile.deleteOnExit();

        RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
        try {
            // Write a known int value
            int expected = 0x01020304;
            raf.writeInt(expected);
            raf.seek(0);

            int actual = raf.readInt();
            if (actual != expected) {
                throw new AssertionError("readInt() returned " + actual + ", expected " + expected);
            }
        } finally {
            raf.close();
        }
    }

    private static void testReadIntEOF() throws IOException {
        File tempFile = File.createTempFile("rafTestEOF", ".tmp");
        tempFile.deleteOnExit();

        RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
        try {
            // Write fewer than 4 bytes (e.g. 3 bytes) to cause EOFException on readInt
            raf.write(new byte[] {1, 2, 3});
            raf.seek(0);

            boolean threwEOF = false;
            try {
                raf.readInt();
            } catch (EOFException e) {
                threwEOF = true;
            }
            if (!threwEOF) {
                throw new AssertionError("readInt() did not throw EOFException when fewer than 4 bytes available");
            }
        } finally {
            raf.close();
        }
    }
}
