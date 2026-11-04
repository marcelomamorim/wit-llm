/*
 * @test
 * @summary Witup-generated tests for FileOutputStream(FileDescriptor) constructor: null guard and shared descriptor behaviour
 * @run main FileOutputStreamWitupTest
 */

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class FileOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testNullFileDescriptorThrowsNPE();
        testGetFDReturnsSuppliedDescriptor();
        testWriteThroughDescriptorReachesFile();
    }

    static void testNullFileDescriptorThrowsNPE() {
        try {
            new FileOutputStream((FileDescriptor) null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testGetFDReturnsSuppliedDescriptor() throws IOException {
        File tmp = File.createTempFile("fosWitupFd", ".bin");
        try {
            FileOutputStream fos1 = new FileOutputStream(tmp);
            try {
                FileDescriptor fd = fos1.getFD();
                FileOutputStream fos2 = new FileOutputStream(fd);
                try {
                    if (fos2.getFD() != fd) {
                        throw new RuntimeException("getFD() does not return the supplied descriptor");
                    }
                } finally {
                    fos2.close();
                }
            } finally {
                fos1.close();
            }
        } finally {
            tmp.delete();
        }
    }

    static void testWriteThroughDescriptorReachesFile() throws IOException {
        File tmp = File.createTempFile("fosWitupWrite", ".bin");
        try {
            FileOutputStream fos1 = new FileOutputStream(tmp);
            FileOutputStream fos2 = new FileOutputStream(fos1.getFD());
            try {
                fos2.write(new byte[] {'a', 'b', 'c'});
            } finally {
                fos2.close();
                fos1.close();
            }
            byte[] content = Files.readAllBytes(tmp.toPath());
            if (content.length != 3 || content[0] != 'a' || content[1] != 'b' || content[2] != 'c') {
                throw new RuntimeException("unexpected file content length=" + content.length);
            }
        } finally {
            tmp.delete();
        }
    }
}
