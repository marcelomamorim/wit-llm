/* @test    @summary Test for readBoolean method in RandomAccessFile    @run main RandomAccessFileWitupTest_P9c876200b381 */

import java.io.RandomAccessFile;
import java.io.IOException;

public class RandomAccessFileWitupTest_P9c876200b381 {

    public static void main(String[] args) throws IOException {
        testReadBooleanNormal();
        testReadBooleanEOF();
    }

    private static void testReadBooleanNormal() throws IOException {
        // Prepare a file with a byte value 1 (true)
        String filename = "testfile_true.bin";
        try (java.io.RandomAccessFile raf = new java.io.RandomAccessFile(filename, "rw")) {
            raf.setLength(1);
            raf.seek(0);
            raf.write(1); // write byte 1
            raf.seek(0);
            boolean result = raf.readBoolean();
            if (result != true) {
                throw new AssertionError("Expected true, got false");
            }
        }
        // Cleanup
        java.io.File f = new java.io.File(filename);
        if (f.exists() && !f.delete()) {
            throw new RuntimeException("Failed to delete test file: " + filename);
        }
    }

    private static void testReadBooleanEOF() throws IOException {
        String filename = "testfile_eof.bin";
        try (java.io.RandomAccessFile raf = new java.io.RandomAccessFile(filename, "rw")) {
            raf.setLength(0); // empty file
            raf.seek(0);
            boolean exceptionThrown = false;
            try {
                raf.readBoolean();
            } catch (java.io.EOFException e) {
                exceptionThrown = true;
            }
            if (!exceptionThrown) {
                throw new AssertionError("Expected EOFException when reading from empty file");
            }
        }
        // Cleanup
        java.io.File f = new java.io.File(filename);
        if (f.exists() && !f.delete()) {
            throw new RuntimeException("Failed to delete test file: " + filename);
        }
    }
}
