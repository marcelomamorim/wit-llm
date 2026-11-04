/* @test    @summary Test readBoolean method of RandomAccessFile for normal and EOF conditions    @run main RandomAccessFileWitupTest */

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.File;

public class RandomAccessFileWitupTest {

    public static void main(String[] args) throws IOException {
        testReadBooleanNormal();
        testReadBooleanEOF();
    }

    private static void testReadBooleanNormal() throws IOException {
        File tempFile = File.createTempFile("test", null);
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            raf.write(1); // write a non-zero byte
            raf.seek(0);
            boolean result = raf.readBoolean();
            if (result != true) {
                throw new AssertionError("Expected true, got " + result);
            }
        }
    }

    private static void testReadBooleanEOF() throws IOException {
        File tempFile = File.createTempFile("testEOF", null);
        tempFile.deleteOnExit();
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "r")) {
            // do not write anything, just read
            try {
                raf.readBoolean();
                throw new AssertionError("Expected EOFException to be thrown");
            } catch (EOFException e) {
                // expected
            }
        }
    }
}
