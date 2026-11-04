/*
 * @test
 * @summary RandomAccessFile.readShort reads big-endian signed shorts and throws EOFException when only one byte remains
 * @run main RandomAccessFileWitupTest
 */

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest {

    public static void main(String[] args) throws IOException {
        File f = File.createTempFile("raf-readshort", ".bin");
        try {
            try (RandomAccessFile raf = new RandomAccessFile(f, "rw")) {
                raf.write(new byte[] {0x12, 0x34, 0x7F, (byte) 0xFF, (byte) 0x80, 0x00, 0x01});
                raf.seek(0);

                check(raf.readShort() == 0x1234, "first readShort should be 0x1234");
                check(raf.readShort() == (short) 0x7FFF, "second readShort should be 0x7FFF");
                check(raf.readShort() == (short) 0x8000, "third readShort should be negative 0x8000");
                check(raf.getFilePointer() == 6, "file pointer should be 6 after three shorts");

                boolean eof = false;
                try {
                    raf.readShort();
                } catch (EOFException e) {
                    eof = true;
                }
                check(eof, "readShort with one trailing byte should throw EOFException");
            }
        } finally {
            f.delete();
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
