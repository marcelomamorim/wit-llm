/* @test    @summary Test readUnsignedByte() method of RandomAccessFile for normal and EOF conditions    @run main RandomAccessFileWitupTest_P4693ea457a51 */

import java.io.RandomAccessFile;
import java.io.EOFException;
import java.io.IOException;

public class RandomAccessFileWitupTest_P4693ea457a51 {

    public static void main(String[] args) throws IOException {
        testReadUnsignedByteNormal();
        testReadUnsignedByteEOF();
    }

    private static void testReadUnsignedByteNormal() throws IOException {
        // Prepare a file with known bytes
        String filename = "testfile.bin";
        java.io.RandomAccessFile raf = null;
        try {
            java.io.FileOutputStream fos = new java.io.FileOutputStream(filename);
            fos.write(new byte[] {10, 20, 30});
            fos.close();
            raf = new java.io.RandomAccessFile(filename, "r");
            // Read first byte
            int val1 = raf.readUnsignedByte();
            if (val1 != 10) {
                throw new AssertionError("Expected 10, got " + val1);
            }
            // Read second byte
            int val2 = raf.readUnsignedByte();
            if (val2 != 20) {
                throw new AssertionError("Expected 20, got " + val2);
            }
            // Read third byte
            int val3 = raf.readUnsignedByte();
            if (val3 != 30) {
                throw new AssertionError("Expected 30, got " + val3);
            }
        } finally {
            if (raf != null) {
                raf.close();
            }
            new java.io.File(filename).delete();
        }
    }

    private static void testReadUnsignedByteEOF() throws IOException {
        // Prepare a file with a single byte
        String filename = "testfile_eof.bin";
        java.io.FileOutputStream fos = null;
        java.io.RandomAccessFile raf = null;
        try {
            fos = new java.io.FileOutputStream(filename);
            fos.write(new byte[] {42});
            fos.close();
            raf = new java.io.RandomAccessFile(filename, "r");
            // Read the only byte
            int val = raf.readUnsignedByte();
            if (val != 42) {
                throw new AssertionError("Expected 42, got " + val);
            }
            // Next read should throw EOFException
            boolean threwEOF = false;
            try {
                raf.readUnsignedByte();
            } catch (EOFException e) {
                threwEOF = true;
            }
            if (!threwEOF) {
                throw new AssertionError("Expected EOFException on reading past EOF");
            }
        } finally {
            if (raf != null) {
                raf.close();
            }
            new java.io.File(filename).delete();
        }
    }
}
