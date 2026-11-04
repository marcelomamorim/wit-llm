/*
 * @test
 * @summary Verify RandomAccessFile.readInt reads big-endian int and throws EOFException on short input
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
            throw new AssertionError(msg);
        }
    }

    static File newTempFile() throws IOException {
        File f = File.createTempFile("raf-readint", ".bin");
        f.deleteOnExit();
        return f;
    }

    static void writeBytes(File f, byte[] data) throws IOException {
        try (FileOutputStream out = new FileOutputStream(f)) {
            out.write(data);
        }
    }

    public static void main(String[] args) throws IOException {
        File f = newTempFile();
        try {
            writeBytes(f, new byte[] {1, 2, 3, 4});
            try (RandomAccessFile raf = new RandomAccessFile(f, "r")) {
                int v = raf.readInt();
                check(v == 0x01020304, "expected 0x01020304 but was " + v);
                check(raf.getFilePointer() == 4L, "file pointer should be 4");
            }

            writeBytes(f, new byte[] {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
            try (RandomAccessFile raf = new RandomAccessFile(f, "r")) {
                int v = raf.readInt();
                check(v == -1, "expected -1 but was " + v);
            }

            writeBytes(f, new byte[] {1, 2, 3});
            try (RandomAccessFile raf = new RandomAccessFile(f, "r")) {
                boolean thrown = false;
                try {
                    raf.readInt();
                } catch (EOFException e) {
                    thrown = true;
                }
                check(thrown, "expected EOFException for 3 bytes");
            }

            writeBytes(f, new byte[0]);
            try (RandomAccessFile raf = new RandomAccessFile(f, "r")) {
                boolean thrown = false;
                try {
                    raf.readInt();
                } catch (EOFException e) {
                    thrown = true;
                }
                check(thrown, "expected EOFException for empty file");
            }
        } finally {
            f.delete();
        }
    }
}
