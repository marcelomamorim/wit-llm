/*
 * @test
 * @summary Verify that removeDirectory on a client that was never connected fails with IllegalStateException
 * @modules java.base/sun.net.ftp
 * @run main FtpClientWitupTest
 */

import sun.net.ftp.FtpClient;

public class FtpClientWitupTest {

    public static void main(String[] args) throws Exception {
        FtpClient client = FtpClient.create();
        boolean thrown = false;
        try {
            client.removeDirectory("somedir");
        } catch (IllegalStateException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalStateException from removeDirectory on unconnected client");
        }
    }
}
