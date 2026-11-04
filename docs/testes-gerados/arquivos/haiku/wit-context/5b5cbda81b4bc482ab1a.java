/*
 * @test
 * @summary Verifies that getSystem() on an unconnected client throws IllegalStateException
 * @modules java.base/sun.net.ftp
 * @run main FtpClientWitupTest
 */

import sun.net.ftp.FtpClient;

public class FtpClientWitupTest {

    public static void main(String[] args) throws Exception {
        FtpClient client = FtpClient.create();
        boolean thrown = false;
        try {
            client.getSystem();
        } catch (IllegalStateException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalStateException from getSystem() without connection");
        }

        thrown = false;
        try {
            client.getSystem();
        } catch (IllegalStateException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalStateException on repeated getSystem() call");
        }
    }
}
