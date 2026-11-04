/*
 * @test
 * @summary Verify that FtpClient.noop() throws IllegalStateException when no server connection exists
 * @modules java.base/sun.net.ftp
 * @run main FtpClientWitupTest
 */

import sun.net.ftp.FtpClient;

public class FtpClientWitupTest {

    public static void main(String[] args) throws Exception {
        FtpClient client = FtpClient.create();
        boolean thrown = false;
        try {
            client.noop();
        } catch (IllegalStateException expected) {
            thrown = true;
        } catch (Exception other) {
            throw new RuntimeException("Unexpected exception type: " + other, other);
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalStateException from noop() without a connection");
        }
    }
}
