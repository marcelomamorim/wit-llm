/*
 * @test
 * @summary Smoke test for the public FtpClient.create() factory
 * @modules java.base/sun.net.ftp
 * @run main FtpClientWitupTest
 */
import sun.net.ftp.FtpClient;

public class FtpClientWitupTest {
    public static void main(String[] args) {
        FtpClient first = FtpClient.create();
        FtpClient second = FtpClient.create();
        if (first == null || second == null) {
            throw new RuntimeException("create() returned null");
        }
        if (first == second) {
            throw new RuntimeException("create() returned the same instance twice");
        }
    }
}
