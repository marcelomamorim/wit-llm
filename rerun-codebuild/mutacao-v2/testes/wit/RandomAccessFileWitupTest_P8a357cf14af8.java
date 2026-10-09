/*
 * @test
 * @summary Test readUnsignedByte method of RandomAccessFile for EOFException and normal read
 * @run main RandomAccessFileWitupTest_P8a357cf14af8
 */

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest_P8a357cf14af8 {
    public static void main(String[] args) throws Exception {
        testReadUnsignedByteNormal();
        testReadUnsignedByteEOFException();
    }

    private static void testReadUnsignedByteNormal() throws IOException {
        File tempFile = File.createTempFile("rafTest", ".tmp");
        tempFile.deleteOnExit();

        RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
        try {
            raf.writeByte(0x7F); // write a positive byte
            raf.seek(0);
            int result = raf.readUnsignedByte();
            if (result != 0x7F) {
                throw new AssertionError("Expected 0x7F but got " + result);
            }
        } finally {
            raf.close();
        }
    }

    private static void testReadUnsignedByteEOFException() throws IOException {
        File tempFile = File.createTempFile("rafTestEmpty", ".tmp");
        tempFile.deleteOnExit();

        RandomAccessFile raf = new RandomAccessFile(tempFile, "r");
        try {
            // File is empty, so readUnsignedByte should throw EOFException
            boolean threw = false;
            try {
                raf.readUnsignedByte();
            } catch (EOFException e) {
                threw = true;
            }
            if (!threw) {
                throw new AssertionError("Expected EOFException was not thrown");
            }
        } finally {
            raf.close();
        }
    }
}
