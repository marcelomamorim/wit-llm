/*
 * @test
 * @summary removeDirectory on an unconnected FtpClient must not report success
 * @modules java.base/sun.net.ftp java.base/sun.net.ftp.impl
 * @run main FtpClientWitupTest
 */

import sun.net.ftp.FtpClient;

public class FtpClientWitupTest {

    public static void main(String[] args) throws Exception {
        FtpClient client = FtpClient.create();
        if (client == null) {
            throw new RuntimeException("create() returned null");
        }
        boolean returned = false;
        try {
            FtpClient result = client.removeDirectory("witup_dir");
            returned = (result == client);
        } catch (Exception e) {
            returned = false;
        }
        if (returned) {
            throw new RuntimeException("removeDirectory succeeded without a connected server");
        }
    }
}
