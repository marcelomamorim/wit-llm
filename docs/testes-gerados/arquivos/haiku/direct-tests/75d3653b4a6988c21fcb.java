/*
 * @test
 * @summary Verify RandomAccessFile.seek accepts non-negative offsets and rejects negative offsets with IOException
 * @run main RandomAccessFileWitupTest
 */

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest {

    public static void main(String[] args) throws Exception {
        File f = File.createTempFile("raf-seek-witup", ".tmp");
        try {
            try (RandomAccessFile raf = new RandomAccessFile(f, "rw")) {
                raf.write(new byte[] {1, 2, 3, 4, 5, 6, 7, 8});

                raf.seek(0);
                check(raf.getFilePointer() == 0L, "pointer should be 0 after seek(0)");

                raf.seek(5);
                check(raf.getFilePointer() == 5L, "pointer should be 5 after seek(5)");
                check(raf.read() == 6, "byte at offset 5 should be 6");

                raf.seek(8);
                check(raf.getFilePointer() == 8L, "pointer should be 8 after seek(8)");
                check(raf.read() == -1, "read at end of file should return -1");

                try {
                    raf.seek(-1L);
                    throw new RuntimeException("seek(-1) should throw IOException");
                } catch (IOException e) {
                    check("Negative seek offset".equals(e.getMessage()),
                          "unexpected message: " + e.getMessage());
                }
                check(raf.getFilePointer() == 8L,
                      "pointer should be unchanged after rejected seek");

                try {
                    raf.seek(Long.MIN_VALUE);
                    throw new RuntimeException("seek(Long.MIN_VALUE) should throw IOException");
                } catch (IOException e) {
                    check("Negative seek offset".equals(e.getMessage()),
                          "unexpected message: " + e.getMessage());
                }
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
