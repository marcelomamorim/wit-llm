/*
 * @test
 * @summary Verifies that FtpClient.rename throws IllegalStateException when the client is not connected
 * @modules java.base/sun.net.ftp java.base/sun.net.ftp.impl
 * @run main FtpClientWitupTest
 */

import sun.net.ftp.FtpClient;

public class FtpClientWitupTest {

    public static void main(String[] args) throws Exception {
        FtpClient client = FtpClient.create();
        boolean thrown = false;
        try {
            client.rename("from.txt", "to.txt");
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalStateException from rename on unconnected client");
        }
    }
}
