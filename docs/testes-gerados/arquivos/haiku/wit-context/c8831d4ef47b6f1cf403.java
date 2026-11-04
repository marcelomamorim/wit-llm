/*
 * @test
 * @summary Basic observable check that FtpClient.create() returns a non-null client
 * @modules java.base/sun.net.ftp java.base/sun.net.ftp.impl
 * @run main FtpClientWitupTest
 */

import sun.net.ftp.FtpClient;

public class FtpClientWitupTest {

    public static void main(String[] args) throws Exception {
        FtpClient client = FtpClient.create();
        if (client == null) {
            throw new RuntimeException("FtpClient.create() returned null");
        }
    }
}
