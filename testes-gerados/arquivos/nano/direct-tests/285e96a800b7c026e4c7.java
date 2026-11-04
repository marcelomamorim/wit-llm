/*
 * @test    @summary Test the allocate method of sun.net.ftp.impl.FtpClient
 * @run main FtpClientAllocateTest
 */

import java.io.IOException;
import sun.net.ftp.impl.FtpClient;

public class FtpClientAllocateTest {

    public static void main(String[] args) {
        testAllocateWithPositiveSize();
        testAllocateWithZeroSize();
        testAllocateWithNegativeSize();
    }

    private static void testAllocateWithPositiveSize() {
        try {
            FtpClient client = FtpClient.create();
            long size = 1024L;
            FtpClient result = client.allocate(size);
            if (result != null) {
                System.out.println("testAllocateWithPositiveSize passed");
            } else {
                System.err.println("testAllocateWithPositiveSize failed: result is null");
            }
        } catch (Exception e) {
            System.err.println("testAllocateWithPositiveSize failed with exception: " + e);
        }
    }

    private static void testAllocateWithZeroSize() {
        try {
            FtpClient client = FtpClient.create();
            long size = 0L;
            FtpClient result = client.allocate(size);
            if (result != null) {
                System.out.println("testAllocateWithZeroSize passed");
            } else {
                System.err.println("testAllocateWithZeroSize failed: result is null");
            }
        } catch (Exception e) {
            System.err.println("testAllocateWithZeroSize failed with exception: " + e);
        }
    }

    private static void testAllocateWithNegativeSize() {
        try {
            FtpClient client = FtpClient.create();
            long size = -1L;
            FtpClient result = client.allocate(size);
            System.err.println("testAllocateWithNegativeSize failed: exception expected but not thrown");
        } catch (sun.net.ftp.FtpProtocolException e) {
            System.out.println("testAllocateWithNegativeSize passed with expected exception");
        } catch (IOException e) {
            System.err.println("testAllocateWithNegativeSize failed with unexpected IOException: " + e);
        } catch (Exception e) {
            System.err.println("testAllocateWithNegativeSize failed with unexpected exception: " + e);
        }
    }
}
