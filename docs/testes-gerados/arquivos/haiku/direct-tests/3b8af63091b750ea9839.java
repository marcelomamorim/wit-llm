/*
 * @test
 * @summary Verifies RandomAccessFile.readChar big-endian decoding and EOFException behavior
 * @run main RandomAccessFileWitupTest
 */

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest {

    public static void main(String[] args) throws IOException {
        File f = File.createTempFile("raf-readchar", ".bin");
        try {
            try (RandomAccessFile raf = new RandomAccessFile(f, "rw")) {
                raf.write(new byte[]{0x12, 0x34, (byte) 0xFF, (byte) 0x80, 0x00, 0x41});
                raf.seek(0);
                check(raf.readChar() == (char) 0x1234, "first char big-endian");
                check(raf.readChar() == (char) 0xFF80, "second char with high bit set");
                check(raf.readChar() == 'A', "third char 0x0041");
                check(raf.getFilePointer() == 6L, "pointer advanced by 6");
            }

            try (RandomAccessFile raf = new RandomAccessFile(f, "rw")) {
                raf.setLength(1);
                raf.seek(0);
                expectEOF(raf);
                check(raf.getFilePointer() == 1L, "pointer advanced only past the one byte read");
            }

            try (RandomAccessFile raf = new RandomAccessFile(f, "rw")) {
                raf.setLength(0);
                raf.seek(0);
                expectEOF(raf);
                check(raf.getFilePointer() == 0L, "pointer unchanged on empty file");
            }
        } finally {
            f.delete();
        }
    }

    private static void expectEOF(RandomAccessFile raf) throws IOException {
        try {
            raf.readChar();
        } catch (EOFException e) {
            return;
        }
        throw new RuntimeException("Expected EOFException was not thrown");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }
}
