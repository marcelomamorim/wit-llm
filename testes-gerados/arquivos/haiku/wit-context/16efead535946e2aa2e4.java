/*
 * @test
 * @summary Verifies that structureMount on an unconnected FTP client fails with IllegalStateException
 * @modules java.base/sun.net.ftp
 * @run main FtpClientWitupTest
 */
import sun.net.ftp.FtpClient;

public class FtpClientWitupTest {
    public static void main(String[] args) throws Exception {
        FtpClient client = FtpClient.create();
        try {
            client.structureMount("F");
            throw new RuntimeException("Expected IllegalStateException for unconnected client");
        } catch (IllegalStateException expected) {
        }
    }
}
