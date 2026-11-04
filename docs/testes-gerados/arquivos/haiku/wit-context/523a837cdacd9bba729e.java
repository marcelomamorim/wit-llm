/*
 * @test
 * @summary Verify RandomAccessFile.readUnsignedByte returns unsigned byte values and throws EOFException at end of file
 * @run main RandomAccessFileWitupTest
 */

import java.io.EOFException;
import java.io.File;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest {

    public static void main(String[] args) throws Exception {
        File f = File.createTempFile("raf-witup", ".bin");
        try {
            try (RandomAccessFile raf = new RandomAccessFile(f, "rw")) {
                raf.write(0xFF);
                raf.seek(0);
                int v = raf.readUnsignedByte();
                if (v != 255) {
                    throw new AssertionError("expected 255 but got " + v);
                }
                try {
                    raf.readUnsignedByte();
                    throw new AssertionError("expected EOFException at end of file");
                } catch (EOFException expected) {
                }
            }

            try (RandomAccessFile raf = new RandomAccessFile(f.getPath(), "rw")) {
                raf.setLength(0);
                raf.seek(0);
                try {
                    raf.readUnsignedByte();
                    throw new AssertionError("expected EOFException on empty file");
                } catch (EOFException expected) {
                }
            }

            try (RandomAccessFile raf = new RandomAccessFile(f, "rw")) {
                raf.write(0x80);
                raf.seek(0);
                int v = raf.readUnsignedByte();
                if (v != 128) {
                    throw new AssertionError("expected 128 but got " + v);
                }
            }
        } finally {
            f.delete();
        }
    }
}
