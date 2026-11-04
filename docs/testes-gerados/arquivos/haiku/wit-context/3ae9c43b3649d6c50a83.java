/*
 * @test
 * @summary Verifies observable behavior of FileOutputStream.getFD() for file-backed and descriptor-backed streams
 * @run main FileOutputStreamWitupTest
 */

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileOutputStreamWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws IOException {
        File f = new File("FileOutputStreamWitupTest.tmp");
        try {
            FileOutputStream fos = new FileOutputStream(f);
            FileDescriptor fd1 = fos.getFD();
            check(fd1 != null, "getFD returned null for file-backed stream");
            check(fd1.valid(), "getFD returned invalid descriptor for open file");
            check(fos.getFD() == fd1, "getFD returned different instances on repeated calls");
            fos.write(1);
            fos.close();
            check(fos.getFD() != null, "getFD returned null after close");

            FileOutputStream appendFos = new FileOutputStream(f, true);
            try {
                check(appendFos.getFD() != null, "getFD returned null for append stream");
                check(appendFos.getFD().valid(), "getFD returned invalid descriptor for append stream");
            } finally {
                appendFos.close();
            }
        } finally {
            f.delete();
        }

        FileOutputStream fdStream = new FileOutputStream(FileDescriptor.out);
        check(fdStream.getFD() == FileDescriptor.out,
              "getFD did not return the descriptor used in the constructor");
    }
}
