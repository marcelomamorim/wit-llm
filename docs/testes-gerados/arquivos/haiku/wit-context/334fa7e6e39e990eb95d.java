/*
 * @test
 * @summary Verify RandomAccessFile.readByte returns bytes in order and throws EOFException at end of file
 * @run main RandomAccessFileWitupTest
 */
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest {

    public static void main(String[] args) throws Exception {
        File empty = File.createTempFile("raf-witup-empty", ".bin");
        try {
            try (RandomAccessFile raf = new RandomAccessFile(empty, "r")) {
                expectEof(raf);
            }
        } finally {
            empty.delete();
        }

        File data = File.createTempFile("raf-witup-data", ".bin");
        try {
            try (RandomAccessFile raf = new RandomAccessFile(data, "rw")) {
                raf.write(0x7F);
                raf.write(0x80);
                raf.seek(0);
                if (raf.readByte() != 0x7F) {
                    throw new AssertionError("first byte mismatch");
                }
                if (raf.readByte() != (byte) 0x80) {
                    throw new AssertionError("second byte mismatch");
                }
                if (raf.getFilePointer() != 2) {
                    throw new AssertionError("file pointer mismatch");
                }
                expectEof(raf);
            }

            try (RandomAccessFile raf = new RandomAccessFile(data, "r")) {
                if (raf.readByte() != 0x7F) {
                    throw new AssertionError("read-only first byte mismatch");
                }
                if (raf.readByte() != (byte) 0x80) {
                    throw new AssertionError("read-only second byte mismatch");
                }
                expectEof(raf);
            }
        } finally {
            data.delete();
        }
    }

    private static void expectEof(RandomAccessFile raf) throws IOException {
        try {
            raf.readByte();
        } catch (EOFException e) {
            return;
        }
        throw new AssertionError("expected EOFException");
    }
}
