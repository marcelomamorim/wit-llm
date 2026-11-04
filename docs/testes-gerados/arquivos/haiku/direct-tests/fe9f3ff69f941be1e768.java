/*
 * @test
 * @summary Smoke test for sun.net.ftp.FtpClient.abort() through the public create() factory
 * @modules java.base/sun.net.ftp
 * @run main FtpClientWitupTest
 */

import sun.net.ftp.FtpClient;

public class FtpClientWitupTest {
    public static void main(String[] args) throws Exception {
        FtpClient client = FtpClient.create();
        if (client == null) {
            throw new AssertionError("FtpClient.create() returned null");
        }
        try {
            client.abort();
        } catch (java.io.IOException e) {
        } catch (RuntimeException e) {
        }
    }
}
