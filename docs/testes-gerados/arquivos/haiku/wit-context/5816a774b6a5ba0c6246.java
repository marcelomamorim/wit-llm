/*
 * @test
 * @summary Verify that FileInputStream.getFD returns the stream's FileDescriptor
 * @run main FileInputStreamWitupTest
 */

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;

public class FileInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        File f = File.createTempFile("fisWitup", ".tmp");
        try {
            FileInputStream in = new FileInputStream(f);
            try {
                FileDescriptor fd = in.getFD();
                check(fd != null, "getFD returned null for a file-based stream");
                check(fd.valid(), "descriptor should be valid while the stream is open");
            } finally {
                in.close();
            }

            FileInputStream byName = new FileInputStream(f.getPath());
            try {
                check(byName.getFD() != null, "getFD returned null for a path-based stream");
            } finally {
                byName.close();
            }

            FileInputStream wrap = new FileInputStream(FileDescriptor.in);
            check(wrap.getFD() == FileDescriptor.in,
                  "getFD should return the descriptor passed to the constructor");
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
