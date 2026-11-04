/*
 * @test
 * @summary Verify RandomAccessFile.readInt reads big-endian ints and throws EOFException when fewer than 4 bytes remain
 * @run main RandomAccessFileWitupTest
 */

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest {

    public static void main(String[] args) throws IOException {
        File f = File.createTempFile("raf-readint", ".bin");
        try {
            testBigEndianRoundTrip(f);
            testNegativeAndExtremeValues(f);
            testSequentialReads(f);
            testThreeBytesThrowsEOF(f);
            testEmptyFileThrowsEOF(f);
        } finally {
            f.delete();
        }
    }

    static void check(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException(msg);
        }
    }

    static void testBigEndianRoundTrip(File f) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(f, "rw")) {
            raf.setLength(0);
            raf.writeInt(0x01020304);
            raf.seek(0);
            check(raf.readInt() == 0x01020304, "big-endian round trip failed");
        }
        try (RandomAccessFile raf = new RandomAccessFile(f, "r")) {
            raf.seek(0);
            check(raf.readInt() == 0x01020304, "read-only big-endian read failed");
        }
    }

    static void testNegativeAndExtremeValues(File f) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(f, "rw")) {
            raf.setLength(0);
            raf.writeInt(-1);
            raf.writeInt(Integer.MIN_VALUE);
            raf.writeInt(Integer.MAX_VALUE);
            raf.seek(0);
            check(raf.readInt() == -1, "-1 not read back");
            check(raf.readInt() == Integer.MIN_VALUE, "MIN_VALUE not read back");
            check(raf.readInt() == Integer.MAX_VALUE, "MAX_VALUE not read back");
        }
    }

    static void testSequentialReads(File f) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(f, "rw")) {
            raf.setLength(0);
            raf.writeInt(1);
            raf.writeInt(2);
            raf.seek(0);
            check(raf.readInt() == 1, "first sequential int wrong");
            check(raf.readInt() == 2, "second sequential int wrong");
            check(raf.getFilePointer() == 8, "file pointer not advanced by 8");
        }
    }

    static void testThreeBytesThrowsEOF(File f) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(f, "rw")) {
            raf.setLength(0);
            raf.write(new byte[] {1, 2, 3});
            raf.seek(0);
            boolean thrown = false;
            try {
                raf.readInt();
            } catch (EOFException e) {
                thrown = true;
            }
            check(thrown, "EOFException expected when only 3 bytes available");
        }
    }

    static void testEmptyFileThrowsEOF(File f) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(f, "rw")) {
            raf.setLength(0);
            raf.seek(0);
            boolean thrown = false;
            try {
                raf.readInt();
            } catch (EOFException e) {
                thrown = true;
            }
            check(thrown, "EOFException expected on empty file");
        }
    }
}
