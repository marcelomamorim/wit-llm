/*
 * @test
 * @summary WITUP-derived checks for RandomAccessFile.readBoolean EOF and boolean mapping
 * @run main RandomAccessFileWitupTest
 */

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest {

    public static void main(String[] args) throws IOException {
        File f = File.createTempFile("witup", ".bin");
        try {
            testEmptyFileThrowsEOF(f);
            check(readBooleanAfterWriting(f, 0) == false, "byte 0 must read as false");
            check(readBooleanAfterWriting(f, 1) == true, "byte 1 must read as true");
            check(readBooleanAfterWriting(f, 0xFF) == true, "byte 0xFF must read as true");
        } finally {
            f.delete();
        }
    }

    static void testEmptyFileThrowsEOF(File f) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(f, "rw")) {
            raf.setLength(0);
            raf.seek(0);
            try {
                raf.readBoolean();
            } catch (EOFException e) {
                return;
            }
            throw new RuntimeException("expected EOFException on empty file");
        }
    }

    static boolean readBooleanAfterWriting(File f, int value) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(f, "rw")) {
            raf.setLength(0);
            raf.write(value);
            raf.seek(0);
            return raf.readBoolean();
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
