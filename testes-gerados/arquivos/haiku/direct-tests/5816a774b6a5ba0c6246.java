/*
 * @test
 * @summary FileInputStream.getFD returns a valid, non-null FileDescriptor that is stable across calls
 * @run main FileInputStreamWitupTest
 */

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        File f = new File("FileInputStreamWitupTest.data");
        try (FileOutputStream out = new FileOutputStream(f)) {
            out.write(new byte[] {1, 2, 3});
        }
        try {
            try (FileInputStream in = new FileInputStream(f)) {
                FileDescriptor fd = in.getFD();
                check(fd != null, "getFD returned null for file-based stream");
                check(fd.valid(), "FileDescriptor from open file should be valid");
                check(in.getFD() == fd, "getFD should return the same FileDescriptor on repeated calls");
            }
        } finally {
            f.delete();
        }

        FileInputStream stdin = new FileInputStream(FileDescriptor.in);
        check(stdin.getFD() == FileDescriptor.in,
              "getFD should return the FileDescriptor passed to the constructor");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
