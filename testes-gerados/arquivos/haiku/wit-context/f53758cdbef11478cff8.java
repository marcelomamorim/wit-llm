/*
 * @test
 * @summary Verify that deleteFile throws IllegalStateException when the FTP client is not connected
 * @modules java.base/sun.net.ftp
 * @run main FtpClientWitupTest
 */

import sun.net.ftp.FtpClient;

public class FtpClientWitupTest {

    public static void main(String[] args) throws Exception {
        FtpClient client = FtpClient.create();
        if (client == null) {
            throw new RuntimeException("create() returned null");
        }
        try {
            client.deleteFile("witup.txt");
            throw new RuntimeException("Expected IllegalStateException was not thrown");
        } catch (IllegalStateException expected) {
            // expected: server is null, client not connected
        }
    }
}
