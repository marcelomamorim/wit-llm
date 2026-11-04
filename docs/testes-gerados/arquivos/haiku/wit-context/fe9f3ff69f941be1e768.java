/*
 * @test
 * @summary Verify that abort() on a FtpClient without an open connection throws IllegalStateException
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
        boolean thrown = false;
        try {
            client.abort();
        } catch (IllegalStateException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalStateException from abort() without connection");
        }
    }
}
