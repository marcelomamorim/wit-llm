/*
 * @test
 * @summary Test sun.net.ftp.impl.FtpClient allocate(long) method
 * @run main FtpClientWitupTest
 */

import sun.net.ftp.impl.FtpClient;
import sun.net.ftp.FtpProtocolException;
import java.io.IOException;

public class FtpClientWitupTest {
    public static void main(String[] args) throws Exception {
        FtpClient client = FtpClient.create();

        // Test allocate with zero size
        FtpClient result = client.allocate(0L);
        if (result != client) {
            throw new RuntimeException("allocate(0) did not return this");
        }

        // Test allocate with positive size
        long size = 12345L;
        result = client.allocate(size);
        if (result != client) {
            throw new RuntimeException("allocate(" + size + ") did not return this");
        }

        // Test allocate with negative size (behavior not defined in source, expect normal call)
        size = -1L;
        result = client.allocate(size);
        if (result != client) {
            throw new RuntimeException("allocate(" + size + ") did not return this");
        }

        System.out.println("All allocate tests passed");
    }
}
