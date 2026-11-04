/*
 * @test
 * @summary Verifies that RandomAccessFile.getFD returns a valid, stable FileDescriptor for opened files
 * @run main RandomAccessFileWitupTest
 */

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest {

    public static void main(String[] args) throws Exception {
        File f = File.createTempFile("witup", ".bin");
        try {
            try (RandomAccessFile raf = new RandomAccessFile(f, "rw")) {
                FileDescriptor fd = raf.getFD();
                check(fd != null, "getFD returned null for File constructor");
                check(fd.valid(), "FileDescriptor from File constructor is not valid");
                check(raf.getFD() == fd, "getFD returned different objects on repeated calls");
            }

            try (RandomAccessFile raf = new RandomAccessFile(f.getPath(), "r")) {
                FileDescriptor fd = raf.getFD();
                check(fd != null, "getFD returned null for String constructor");
                check(fd.valid(), "FileDescriptor from String constructor is not valid");
            }
        } finally {
            f.delete();
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
