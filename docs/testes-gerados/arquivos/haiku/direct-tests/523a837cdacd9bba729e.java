/*
 * @test
 * @summary Verifies RandomAccessFile.readUnsignedByte returns values in 0..255 and throws EOFException at end of file
 * @run main RandomAccessFileWitupTest
 */

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest {

    public static void main(String[] args) throws IOException {
        File f = File.createTempFile("raf-witup", ".bin");
        try {
            try (RandomAccessFile raf = new RandomAccessFile(f, "rw")) {
                raf.write(new byte[] {0x00, (byte) 0xFF, 0x7F, (byte) 0x80});
                raf.seek(0);

                check(raf.readUnsignedByte() == 0, "byte 0x00 should read as 0");
                check(raf.getFilePointer() == 1, "file pointer should advance to 1");
                check(raf.readUnsignedByte() == 255, "byte 0xFF should read as 255");
                check(raf.readUnsignedByte() == 127, "byte 0x7F should read as 127");
                check(raf.readUnsignedByte() == 128, "byte 0x80 should read as 128");
                check(raf.getFilePointer() == 4, "file pointer should be 4 after reading all bytes");

                expectEof(raf);
            }

            try (RandomAccessFile raf = new RandomAccessFile(f, "rw")) {
                raf.setLength(0);
                raf.seek(0);
                expectEof(raf);
            }
        } finally {
            f.delete();
        }
    }

    private static void expectEof(RandomAccessFile raf) throws IOException {
        try {
            raf.readUnsignedByte();
        } catch (EOFException e) {
            return;
        }
        throw new RuntimeException("expected EOFException at end of file");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
