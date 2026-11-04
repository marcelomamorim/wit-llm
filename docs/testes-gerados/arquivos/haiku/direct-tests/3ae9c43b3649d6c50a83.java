/*
 * @test
 * @summary FileOutputStream.getFD returns the descriptor associated with the stream
 * @run main FileOutputStreamWitupTest
 */

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;

public class FileOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        FileDescriptor fdIn = new FileDescriptor();
        FileOutputStream fromFd = new FileOutputStream(fdIn);
        if (fromFd.getFD() != fdIn) {
            throw new RuntimeException("getFD must return the descriptor passed to the constructor");
        }

        File f = new File("FileOutputStreamWitupTest.tmp");
        try (FileOutputStream fos = new FileOutputStream(f)) {
            FileDescriptor fd = fos.getFD();
            if (fd == null) {
                throw new RuntimeException("getFD must not return null for a file-backed stream");
            }
            if (!fd.valid()) {
                throw new RuntimeException("descriptor of an open file stream must be valid");
            }
            if (fos.getFD() != fd) {
                throw new RuntimeException("repeated getFD calls must return the same descriptor");
            }
            fos.write(42);
        } finally {
            f.delete();
        }
    }
}
