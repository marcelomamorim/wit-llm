/*
 * @test
 * @summary Verifies that makeDirectory on a freshly created FtpClient without an open connection throws IllegalStateException
 * @modules java.base/sun.net.ftp
 * @run main FtpClientWitupTest
 */

import sun.net.ftp.FtpClient;

public class FtpClientWitupTest {

    public static void main(String[] args) throws Exception {
        FtpClient client = FtpClient.create();
        boolean thrown = false;
        try {
            client.makeDirectory("witup");
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IllegalStateException when server is not connected");
        }
    }
}
