/*
 * @test
 * @summary siteCmd on an unconnected FtpClient must not complete successfully
 * @modules java.base/sun.net.ftp
 * @run main FtpClientWitupTest
 */

import sun.net.ftp.FtpClient;

public class FtpClientWitupTest {

    public static void main(String[] args) throws Exception {
        FtpClient client = FtpClient.create();
        boolean returned = false;
        try {
            client.siteCmd("HELP");
            returned = true;
        } catch (Exception expected) {
        }
        if (returned) {
            throw new RuntimeException("siteCmd succeeded without a connection");
        }
    }
}
