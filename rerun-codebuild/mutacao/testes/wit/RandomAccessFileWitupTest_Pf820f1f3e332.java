/*
 * @test
 * @summary Test for RandomAccessFile.readBoolean() method.
 * @run main RandomAccessFileWitupTest_Pf820f1f3e332
 */

import java.io.RandomAccessFile;
import java.io.IOException;

public class RandomAccessFileWitupTest_Pf820f1f3e332 {

    public static void main(String[] args) {
        testReadBooleanNormal();
        testReadBooleanEOF();
    }

    private static void testReadBooleanNormal() {
        try {
            // Prepare a file with a boolean value (true -> 1)
            java.io.File tempFile = java.io.File.createTempFile("test", null);
            tempFile.deleteOnExit();
            try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
                raf.write(1); // write byte 1 (true)
                raf.seek(0);
                boolean result = raf.readBoolean();
                if (result != true) {
                    throw new AssertionError("Expected true, got " + result);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void testReadBooleanEOF() {
        try {
            // Prepare an empty file to trigger EOFException
            java.io.File tempFile = java.io.File.createTempFile("testEOF", null);
            tempFile.deleteOnExit();
            try (RandomAccessFile raf = new RandomAccessFile(tempFile, "r")) {
                // Read from empty file should throw EOFException
                boolean exceptionThrown = false;
                try {
                    raf.readBoolean();
                } catch (java.io.EOFException eof) {
                    exceptionThrown = true;
                }
                if (!exceptionThrown) {
                    throw new AssertionError("Expected EOFException to be thrown");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
