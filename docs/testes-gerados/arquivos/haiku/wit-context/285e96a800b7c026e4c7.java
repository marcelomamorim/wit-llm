/*
 * @test
 * @summary Verify that allocate fails with IllegalStateException when the FTP client is not connected
 * @modules java.base/sun.net.ftp
 * @run main FtpClientWitupTest
 */

import java.io.IOException;
import sun.net.ftp.FtpClient;

public class FtpClientWitupTest {

    public static void main(String[] args) throws Exception {
        FtpClient client = FtpClient.create();
        try {
            client.allocate(100L);
            throw new RuntimeException("Expected IllegalStateException was not thrown");
        } catch (IllegalStateException expected) {
            // expected: no server connection (server == null)
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException instead of IllegalStateException", e);
        }
    }
}
