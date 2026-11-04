/*
 * @test
 * @summary Tests RandomAccessFile.readShort big-endian decoding and EOFException when fewer than two bytes remain
 * @run main RandomAccessFileWitupTest
 */

import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static File newFileWithBytes(int... bytes) throws IOException {
        File f = File.createTempFile("witup", ".bin");
        f.deleteOnExit();
        try (FileOutputStream out = new FileOutputStream(f)) {
            for (int b : bytes) {
                out.write(b);
            }
        }
        return f;
    }

    static void testBigEndianPositive() throws IOException {
        File f = newFileWithBytes(0x12, 0x34);
        try (RandomAccessFile raf = new RandomAccessFile(f, "r")) {
            short s = raf.readShort();
            check(s == 0x1234, "expected 0x1234 but got " + s);
            check(raf.getFilePointer() == 2L,
                  "file pointer should advance by 2 but is " + raf.getFilePointer());
        }
    }

    static void testSignedNegative() throws IOException {
        File f = newFileWithBytes(0xFF, 0xFE);
        try (RandomAccessFile raf = new RandomAccessFile(f, "r")) {
            short s = raf.readShort();
            check(s == (short) -2, "expected -2 but got " + s);
        }
    }

    static void testSequentialReads() throws IOException {
        File f = newFileWithBytes(0x00, 0x01, 0x00, 0x02);
        try (RandomAccessFile raf = new RandomAccessFile(f, "r")) {
            check(raf.readShort() == 1, "first readShort mismatch");
            check(raf.readShort() == 2, "second readShort mismatch");
            check(raf.getFilePointer() == 4L, "file pointer should be 4");
        }
    }

    static void testEmptyFileThrowsEOF() throws IOException {
        File f = newFileWithBytes();
        boolean thrown = false;
        try (RandomAccessFile raf = new RandomAccessFile(f, "r")) {
            try {
                raf.readShort();
            } catch (EOFException e) {
                thrown = true;
            }
        }
        check(thrown, "EOFException expected for empty file");
    }

    static void testSingleByteThrowsEOF() throws IOException {
        File f = newFileWithBytes(0x01);
        boolean thrown = false;
        try (RandomAccessFile raf = new RandomAccessFile(f, "r")) {
            try {
                raf.readShort();
            } catch (EOFException e) {
                thrown = true;
            }
        }
        check(thrown, "EOFException expected when only one byte is available");
    }

    public static void main(String[] args) throws Exception {
        testBigEndianPositive();
        testSignedNegative();
        testSequentialReads();
        testEmptyFileThrowsEOF();
        testSingleByteThrowsEOF();
    }
}
