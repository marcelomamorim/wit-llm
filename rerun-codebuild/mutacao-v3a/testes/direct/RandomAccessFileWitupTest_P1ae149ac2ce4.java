/*
 * @test
 * @summary Test readUnsignedByte method for RandomAccessFile.
 * @run main RandomAccessFileWitupTest_P1ae149ac2ce4
 */

import java.io.*;

public class RandomAccessFileWitupTest_P1ae149ac2ce4 {

    public static void main(String[] args) {
        testReadUnsignedBytePositive();
        testReadUnsignedByteEOF();
    }

    static void testReadUnsignedBytePositive() {
        try {
            File tempFile = File.createTempFile("test", null);
            tempFile.deleteOnExit();
            try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
                raf.write(123); // byte value 123
                raf.seek(0);
                int result = raf.readUnsignedByte();
                if (result != 123) {
                    throw new AssertionError("Expected 123, got " + result);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void testReadUnsignedByteEOF() {
        try {
            File tempFile = File.createTempFile("testEOF", null);
            tempFile.deleteOnExit();
            try (RandomAccessFile raf = new RandomAccessFile(tempFile, "r")) {
                // Create empty file
                raf.seek(0);
                int ch = raf.readUnsignedByte();
                throw new AssertionError("Expected EOFException, but read value: " + ch);
            }
        } catch (EOFException eof) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
