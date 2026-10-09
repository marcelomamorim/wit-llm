/* @test    @summary Test readUnsignedByte method for normal and EOF conditions    @run main RandomAccessFileWitupTest_P4693ea457a51 */

import java.io.RandomAccessFile;
import java.io.File;
import java.io.IOException;

public class RandomAccessFileWitupTest_P4693ea457a51 {

    public static void main(String[] args) throws Exception {
        testReadUnsignedByteNormal();
        testReadUnsignedByteEOF();
    }

    private static void testReadUnsignedByteNormal() throws IOException {
        File tempFile = File.createTempFile("testfile", null);
        try {
            try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
                raf.write(42); // write a byte with value 42
                raf.seek(0); // reset pointer
                int result = raf.readUnsignedByte();
                if (result != 42) {
                    throw new AssertionError("Expected 42, got " + result);
                }
            }
        } finally {
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    private static void testReadUnsignedByteEOF() throws IOException {
        File tempFile = File.createTempFile("testfile", null);
        try {
            try (RandomAccessFile raf = new RandomAccessFile(tempFile, "r")) {
                // empty file, read should throw EOFException
                boolean threw = false;
                try {
                    raf.readUnsignedByte();
                } catch (java.io.EOFException e) {
                    threw = true;
                }
                if (!threw) {
                    throw new AssertionError("Expected EOFException not thrown");
                }
            }
        } finally {
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }
}
