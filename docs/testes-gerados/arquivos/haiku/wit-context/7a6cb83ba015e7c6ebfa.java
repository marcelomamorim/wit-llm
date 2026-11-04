/*
 * @test
 * @summary Witup-generated checks for RandomAccessFile.readUnsignedShort: big-endian value and EOFException on short input
 * @run main RandomAccessFileWitupTest
 */

import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest {

    static void writeBytes(File f, byte[] data) throws IOException {
        try (FileOutputStream out = new FileOutputStream(f)) {
            out.write(data);
        }
    }

    static void checkValue(File f, byte[] data, int expected) throws IOException {
        writeBytes(f, data);
        try (RandomAccessFile raf = new RandomAccessFile(f, "r")) {
            int v = raf.readUnsignedShort();
            if (v != expected) {
                throw new AssertionError("expected " + expected + " but got " + v);
            }
        }
    }

    static void checkEOF(File f, byte[] data) throws IOException {
        writeBytes(f, data);
        try (RandomAccessFile raf = new RandomAccessFile(f, "r")) {
            try {
                int v = raf.readUnsignedShort();
                throw new AssertionError("expected EOFException but got value " + v);
            } catch (EOFException expected) {
                // expected
            }
        }
    }

    public static void main(String[] args) throws IOException {
        File f = File.createTempFile("RafWitup", ".bin");
        try {
            checkValue(f, new byte[] {0x12, 0x34}, 0x1234);
            checkValue(f, new byte[] {(byte) 0xFF, (byte) 0xFE}, 0xFFFE);
            checkValue(f, new byte[] {0x00, 0x01}, 0x0001);

            checkEOF(f, new byte[] {});
            checkEOF(f, new byte[] {0x41});

            writeBytes(f, new byte[] {0x00, 0x01, 0x02});
            try (RandomAccessFile raf = new RandomAccessFile(f, "r")) {
                int v = raf.readUnsignedShort();
                if (v != 0x0001) {
                    throw new AssertionError("expected 1 but got " + v);
                }
                if (raf.getFilePointer() != 2) {
                    throw new AssertionError("expected file pointer 2 but got " + raf.getFilePointer());
                }
            }
        } finally {
            f.delete();
        }
    }
}
