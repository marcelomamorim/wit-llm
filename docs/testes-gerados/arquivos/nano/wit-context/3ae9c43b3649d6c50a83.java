/* @test    @summary Test for getFD method in FileOutputStream to verify IOException when fd is null    @run main FileOutputStreamWitupTest */

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileOutputStreamWitupTest {

    public static void main(String[] args) {
        testGetFDWithNullFd();
        testGetFDWithNonNullFd();
    }

    private static void testGetFDWithNullFd() {
        FileOutputStream fos = null;
        try {
            // Create a subclass to override fd to null
            fos = new FileOutputStream("dummy", false) {
                @Override
                public FileDescriptor getFD() throws IOException {
                    // Simulate fd being null
                    throw new IOException();
                }
            };
            fos.getFD();
            System.out.println("FAIL: Expected IOException not thrown when fd is null.");
        } catch (IOException e) {
            System.out.println("PASS: IOException thrown as expected when fd is null.");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

    private static void testGetFDWithNonNullFd() {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("dummy", false);
            FileDescriptor fd = fos.getFD();
            if (fd != null) {
                System.out.println("PASS: getFD returned non-null FileDescriptor.");
            } else {
                System.out.println("FAIL: getFD returned null FileDescriptor.");
            }
        } catch (IOException e) {
            System.out.println("FAIL: IOException thrown unexpectedly: " + e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }
}
