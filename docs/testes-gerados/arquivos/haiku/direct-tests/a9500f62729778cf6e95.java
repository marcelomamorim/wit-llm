/*
 * @test
 * @summary Verify RandomAccessFile.readBoolean returns false only for 0, true for other unsigned byte values, and throws EOFException at end of file
 * @run main RandomAccessFileWitupTest
 */

import java.io.EOFException;
import java.io.File;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest {

    public static void main(String[] args) throws Exception {
        testMixedBytes();
        testEmptyFileThrowsEOF();
    }

    private static void testMixedBytes() throws Exception {
        File f = File.createTempFile("raf-witup-mixed", ".bin");
        try {
            try (RandomAccessFile raf = new RandomAccessFile(f, "rw")) {
                raf.write(new byte[] {0, 1, (byte) 0x80, 0x7f});
                raf.seek(0);
                check(!raf.readBoolean(), "byte 0x00 should read as false");
                check(raf.readBoolean(), "byte 0x01 should read as true");
                check(raf.readBoolean(), "byte 0x80 should read as true");
                check(raf.readBoolean(), "byte 0x7f should read as true");

                boolean eofThrown = false;
                try {
                    raf.readBoolean();
                } catch (EOFException e) {
                    eofThrown = true;
                }
                check(eofThrown, "readBoolean at end of file should throw EOFException");
            }
        } finally {
            f.delete();
        }
    }

    private static void testEmptyFileThrowsEOF() throws Exception {
        File f = File.createTempFile("raf-witup-empty", ".bin");
        try {
            try (RandomAccessFile raf = new RandomAccessFile(f, "rw")) {
                boolean eofThrown = false;
                try {
                    raf.readBoolean();
                } catch (EOFException e) {
                    eofThrown = true;
                }
                check(eofThrown, "readBoolean on empty file should throw EOFException");
            }
        } finally {
            f.delete();
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
