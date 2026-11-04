/*
 * @test
 * @summary Verify that siteCmd fails with IllegalStateException when the client is not connected
 * @modules java.base/sun.net.ftp
 * @run main FtpClientWitupTest
 */
import sun.net.ftp.FtpClient;

public class FtpClientWitupTest {
    public static void main(String[] args) throws Exception {
        FtpClient client = FtpClient.create();
        try {
            client.siteCmd("HELP");
            throw new RuntimeException("expected IllegalStateException when server is null");
        } catch (IllegalStateException expected) {
        }
    }
}
