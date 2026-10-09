/* @test    @summary Test RandomAccessFile.readFully(byte[], int, int) for correct behavior and EOFException handling    @run main RandomAccessFileWitupTest_Pa94f788e1da2 */

import java.io.*;

public class RandomAccessFileWitupTest_Pa94f788e1da2 {

    public static void main(String[] args) throws Exception {
        testReadFullyNormalCase();
        testReadFullyEOFException();
    }

    private static void testReadFullyNormalCase() throws Exception {
        byte[] data = {10, 20, 30, 40, 50};
        File tempFile = File.createTempFile("testfile", null);
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            raf.write(data);
            raf.seek(0);
            byte[] buffer = new byte[5];
            raf.readFully(buffer, 0, buffer.length);
            for (int i = 0; i < buffer.length; i++) {
                if (buffer[i] != data[i]) {
                    throw new AssertionError("Data mismatch at index " + i);
                }
            }
        }
    }

    private static void testReadFullyEOFException() throws Exception {
        byte[] data = {1, 2, 3};
        File tempFile = File.createTempFile("testfileEOF", null);
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            raf.write(data);
            raf.seek(0);
            byte[] buffer = new byte[10];
            boolean exceptionThrown = false;
            try {
                raf.readFully(buffer, 0, buffer.length);
            } catch (EOFException e) {
                exceptionThrown = true;
            }
            if (!exceptionThrown) {
                throw new AssertionError("Expected EOFException was not thrown");
            }
        }
    }
}
