/*
 * @test
 * @summary Verify RandomAccessFile.readUnsignedShort returns big-endian unsigned values, advances the file pointer, and throws EOFException at end of file
 * @run main RandomAccessFileWitupTest
 */

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest {

    public static void main(String[] args) throws Exception {
        File f = File.createTempFile("raf-witup", ".bin");
        try {
            check(f, new byte[]{0x12, 0x34}, 0x1234);
            check(f, new byte[]{(byte) 0xFF, (byte) 0xFE}, 0xFFFE);
            check(f, new byte[]{0, 0}, 0);
            checkEof(f, new byte[0]);
            checkEof(f, new byte[]{0x7F});
            checkPointer(f);
        } finally {
            f.delete();
        }
    }

    static void check(File f, byte[] data, int expected) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(f, "rw")) {
            raf.setLength(0);
            raf.write(data);
            raf.seek(0);
            int v = raf.readUnsignedShort();
            if (v != expected) {
                throw new RuntimeException("Expected " + expected + " but got " + v);
            }
        }
    }

    static void checkEof(File f, byte[] data) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(f, "rw")) {
            raf.setLength(0);
            raf.write(data);
            raf.seek(0);
            boolean thrown = false;
            try {
                raf.readUnsignedShort();
            } catch (EOFException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("Expected EOFException for data length " + data.length);
            }
        }
    }

    static void checkPointer(File f) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(f, "rw")) {
            raf.setLength(0);
            raf.write(new byte[]{1, 2, 3, 4});
            raf.seek(0);
            int first = raf.readUnsignedShort();
            if (first != 0x0102) {
                throw new RuntimeException("Expected 0x0102 but got " + first);
            }
            if (raf.getFilePointer() != 2) {
                throw new RuntimeException("Expected file pointer 2 but got " + raf.getFilePointer());
            }
            int second = raf.readUnsignedShort();
            if (second != 0x0304) {
                throw new RuntimeException("Expected 0x0304 but got " + second);
            }
            if (raf.getFilePointer() != 4) {
                throw new RuntimeException("Expected file pointer 4 but got " + raf.getFilePointer());
            }
        }
    }
}
