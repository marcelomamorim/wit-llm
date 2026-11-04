/*
 * @test
 * @summary Verifies that RandomAccessFile.getFD() returns a non-null, stable FileDescriptor for successfully opened files
 * @run main RandomAccessFileWitupTest
 */

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws IOException {
        File f = File.createTempFile("raf_getfd_witup", ".tmp");
        f.deleteOnExit();
        try {
            try (RandomAccessFile raf = new RandomAccessFile(f, "rw")) {
                FileDescriptor fd1 = raf.getFD();
                check(fd1 != null, "getFD() returned null for File constructor");
                FileDescriptor fd2 = raf.getFD();
                check(fd1 == fd2, "getFD() returned different objects on repeated calls");
            }

            try (RandomAccessFile raf = new RandomAccessFile(f.getPath(), "r")) {
                FileDescriptor fd = raf.getFD();
                check(fd != null, "getFD() returned null for String constructor in read mode");
            }
        } finally {
            f.delete();
        }
    }
}
