/*
 * @test
 * @summary Test readUnsignedByte method of RandomAccessFile
 * @run main RandomAccessFileWitupTest_P8a357cf14af8
 */

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest_P8a357cf14af8 {
    public static void main(String[] args) throws Exception {
        testReadUnsignedByteReturnsCorrectValue();
        testReadUnsignedByteThrowsEOFExceptionAtEOF();
    }

    private static void testReadUnsignedByteReturnsCorrectValue() throws IOException {
        File tempFile = File.createTempFile("rafTest", ".tmp");
        tempFile.deleteOnExit();
        RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
        try {
            // Write bytes 0x00, 0x7F, 0xFF
            raf.write(new byte[] {0x00, 0x7F, (byte)0xFF});
            raf.seek(0);

            int b0 = raf.readUnsignedByte();
            if (b0 != 0x00) {
                throw new AssertionError("Expected 0x00 but got " + b0);
            }

            int b1 = raf.readUnsignedByte();
            if (b1 != 0x7F) {
                throw new AssertionError("Expected 0x7F but got " + b1);
            }

            int b2 = raf.readUnsignedByte();
            if (b2 != 0xFF) {
                throw new AssertionError("Expected 0xFF but got " + b2);
            }
        } finally {
            raf.close();
            tempFile.delete();
        }
    }

    private static void testReadUnsignedByteThrowsEOFExceptionAtEOF() throws IOException {
        File tempFile = File.createTempFile("rafTestEOF", ".tmp");
        tempFile.deleteOnExit();
        RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
        try {
            // Write a single byte
            raf.write(0x01);
            raf.seek(0);
            raf.readUnsignedByte(); // read the only byte

            boolean threwEOF = false;
            try {
                raf.readUnsignedByte(); // should throw EOFException
            } catch (EOFException e) {
                threwEOF = true;
            }
            if (!threwEOF) {
                throw new AssertionError("Expected EOFException when reading past EOF");
            }
        } finally {
            raf.close();
            tempFile.delete();
        }
    }
}
