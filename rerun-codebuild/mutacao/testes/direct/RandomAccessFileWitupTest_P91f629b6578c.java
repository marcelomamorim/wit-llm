/* @test    @summary Test readFully method of RandomAccessFile for correct behavior and EOFException handling    @run main RandomAccessFileWitupTest_P91f629b6578c */

import java.io.*;

public class RandomAccessFileWitupTest_P91f629b6578c {

    public static void main(String[] args) throws Exception {
        testReadFullyNormal();
        testReadFullyEOF();
    }

    private static void testReadFullyNormal() throws Exception {
        byte[] buffer = new byte[10];
        File tempFile = File.createTempFile("testfile", ".txt");
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            raf.write(new byte[] {1,2,3,4,5,6,7,8,9,10});
            raf.seek(0);
            raf.readFully(buffer, 0, buffer.length);
            for (int i = 0; i < buffer.length; i++) {
                if (buffer[i] != i + 1) {
                    throw new AssertionError("Data mismatch at index " + i);
                }
            }
        }
    }

    private static void testReadFullyEOF() throws Exception {
        byte[] buffer = new byte[5];
        File tempFile = File.createTempFile("testfileEOF", ".txt");
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            raf.write(new byte[] {1,2,3});
            raf.seek(0);
            try {
                raf.readFully(buffer, 0, buffer.length);
                throw new AssertionError("Expected EOFException was not thrown");
            } catch (EOFException e) {
                // Expected exception
            }
        }
    }
}
