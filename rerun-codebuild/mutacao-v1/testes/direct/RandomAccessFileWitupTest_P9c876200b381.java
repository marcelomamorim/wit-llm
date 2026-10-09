/* @test    @summary Test readBoolean() method of RandomAccessFile for normal and EOF conditions    @run main RandomAccessFileWitupTest_P9c876200b381 */

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest_P9c876200b381 {

    public static void main(String[] args) throws IOException {
        testReadBooleanNormal();
        testReadBooleanEOF();
    }

    private static void testReadBooleanNormal() throws IOException {
        // Prepare a file with a byte value 1 (true)
        String filename = "testfile_true.bin";
        try (RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
            raf.setLength(1);
            raf.seek(0);
            raf.write(1);
        }
        // Read the boolean value
        try (RandomAccessFile raf = new RandomAccessFile(filename, "r")) {
            boolean result = raf.readBoolean();
            if (result != true) {
                throw new AssertionError("Expected true, got false");
            }
        }
        // Clean up
        new java.io.File("testfile_true.bin").delete();
    }

    private static void testReadBooleanEOF() throws IOException {
        // Prepare an empty file
        String filename = "testfile_eof.bin";
        try (RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
            raf.setLength(0);
        }
        // Attempt to read should throw EOFException
        try (RandomAccessFile raf = new RandomAccessFile(filename, "r")) {
            raf.readBoolean();
            throw new AssertionError("Expected EOFException not thrown");
        } catch (EOFException e) {
            // Expected exception
        }
        // Clean up
        new java.io.File(filename).delete();
    }
}
