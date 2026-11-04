/*
 * @test
 * @summary Smoke test for the public sun.net.ftp.FtpClient.create() factory
 * @modules java.base/sun.net.ftp
 * @run main FtpClientWitupTest
 */
import sun.net.ftp.FtpClient;

public class FtpClientWitupTest {
    public static void main(String[] args) {
        FtpClient client = FtpClient.create();
        if (client == null) {
            throw new RuntimeException("FtpClient.create() returned null");
        }
    }
}
