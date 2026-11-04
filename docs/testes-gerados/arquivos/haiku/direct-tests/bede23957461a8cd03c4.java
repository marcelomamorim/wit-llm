/*
 * @test
 * @summary Verifies RandomAccessFile.writeChars writes big-endian UTF-16 code units, handles empty and surrogate strings, and throws NullPointerException for null
 * @run main RandomAccessFileWitupTest
 */

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest {

    public static void main(String[] args) throws Exception {
        File f = File.createTempFile("raf_writechars", ".bin");
        try {
            testTwoChars(f);
            testEmptyString(f);
            testSurrogatePair(f);
            testNullString(f);
        } finally {
            f.delete();
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testTwoChars(File f) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(f, "rw")) {
            raf.setLength(0);
            raf.writeChars("AB");
            check(raf.getFilePointer() == 4, "file pointer after writeChars(\"AB\") should be 4");
            check(raf.length() == 4, "length after writeChars(\"AB\") should be 4");

            raf.seek(0);
            check(raf.read() == 0, "byte 0 should be 0x00");
            check(raf.read() == 0x41, "byte 1 should be 0x41");
            check(raf.read() == 0, "byte 2 should be 0x00");
            check(raf.read() == 0x42, "byte 3 should be 0x42");

            raf.seek(0);
            check(raf.readChar() == 'A', "readChar should return 'A'");
            check(raf.readChar() == 'B', "readChar should return 'B'");
        }
    }

    static void testEmptyString(File f) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(f, "rw")) {
            raf.setLength(0);
            raf.writeChars("");
            check(raf.getFilePointer() == 0, "empty string should not move file pointer");
            check(raf.length() == 0, "empty string should not extend file");
        }
    }

    static void testSurrogatePair(File f) throws IOException {
        String s = new String(new char[] { (char) 0xD83D, (char) 0xDE00 });
        try (RandomAccessFile raf = new RandomAccessFile(f, "rw")) {
            raf.setLength(0);
            raf.writeChars(s);
            check(raf.getFilePointer() == 4, "surrogate pair should write 4 bytes");
            raf.seek(0);
            check(raf.read() == 0xD8, "high byte of D83D should be 0xD8");
            check(raf.read() == 0x3D, "low byte of D83D should be 0x3D");
            check(raf.read() == 0xDE, "high byte of DE00 should be 0xDE");
            check(raf.read() == 0x00, "low byte of DE00 should be 0x00");
        }
    }

    static void testNullString(File f) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(f, "rw")) {
            raf.setLength(0);
            try {
                raf.writeChars(null);
                throw new RuntimeException("writeChars(null) should throw NullPointerException");
            } catch (NullPointerException e) {
                // expected: s.length() dereferences null
            }
        }
    }
}
