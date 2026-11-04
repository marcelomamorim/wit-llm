/*
 * @test
 * @summary Verifies RandomAccessFile.readByte returns signed byte values and throws EOFException at end of file
 * @run main RandomAccessFileWitupTest
 */

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest {

    public static void main(String[] args) throws IOException {
        File f = File.createTempFile("raf-readbyte", ".bin");
        try {
            try (RandomAccessFile raf = new RandomAccessFile(f, "rw")) {
                raf.write(new byte[] {1, (byte) 0xFF, 0x7F});
                raf.seek(0);
                check(raf.readByte() == 1, "first byte should be 1");
                check(raf.readByte() == -1, "second byte should be -1 (signed)");
                check(raf.readByte() == 127, "third byte should be 127");
                check(throwsEOF(raf), "readByte at end of file should throw EOFException");
            }

            try (RandomAccessFile raf = new RandomAccessFile(f.getPath(), "rw")) {
                raf.setLength(0);
                raf.seek(0);
                check(throwsEOF(raf), "readByte on empty file should throw EOFException");
            }
        } finally {
            f.delete();
        }
    }

    static boolean throwsEOF(RandomAccessFile raf) throws IOException {
        try {
            raf.readByte();
            return false;
        } catch (EOFException e) {
            return true;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
