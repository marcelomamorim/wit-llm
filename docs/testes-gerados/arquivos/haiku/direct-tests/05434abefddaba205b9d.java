/*
 * @test
 * @summary Verifies observable behavior of RandomAccessFile.readFully(byte[]) on normal reads, zero-length arrays, and end-of-file.
 * @run main RandomAccessFileWitupTest
 */

import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.util.Arrays;

public class RandomAccessFileWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        File f = File.createTempFile("raf_witup", ".bin");
        try {
            byte[] data = {1, 2, 3, 4, 5, 6, 7, 8};
            try (FileOutputStream fos = new FileOutputStream(f)) {
                fos.write(data);
            }

            // Full read fills the array and advances the file pointer.
            try (RandomAccessFile raf = new RandomAccessFile(f, "r")) {
                byte[] buf = new byte[8];
                raf.readFully(buf);
                check(Arrays.equals(data, buf), "readFully(byte[]) content mismatch");
                check(raf.getFilePointer() == 8, "file pointer should be 8 after full read");
            }

            // Zero-length array: no bytes consumed.
            try (RandomAccessFile raf = new RandomAccessFile(f.getPath(), "r")) {
                raf.readFully(new byte[0]);
                check(raf.getFilePointer() == 0, "zero-length read must not move file pointer");
            }

            // Read starting from a seek position reads the remaining bytes.
            try (RandomAccessFile raf = new RandomAccessFile(f, "r")) {
                raf.seek(3);
                byte[] buf = new byte[5];
                raf.readFully(buf);
                check(Arrays.equals(new byte[] {4, 5, 6, 7, 8}, buf),
                      "readFully after seek returned wrong bytes");
            }

            // Requesting more bytes than remain throws EOFException.
            try (RandomAccessFile raf = new RandomAccessFile(f, "r")) {
                raf.seek(4);
                boolean thrown = false;
                try {
                    raf.readFully(new byte[8]);
                } catch (EOFException e) {
                    thrown = true;
                }
                check(thrown, "expected EOFException when reading past end of file");
            }
        } finally {
            f.delete();
        }
    }
}
