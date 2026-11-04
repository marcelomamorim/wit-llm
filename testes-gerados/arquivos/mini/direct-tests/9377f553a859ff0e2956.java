/*
 * @test
 * @summary Test sun.net.ftp.impl.FtpClient noop() method
 * @run main FtpClientWitupTest
 */

import sun.net.ftp.impl.FtpClient;
import sun.net.ftp.FtpProtocolException;
import java.io.IOException;

public class FtpClientWitupTest {
    public static void main(String[] args) throws Exception {
        FtpClient client = FtpClient.create();
        // The noop method should return the same instance and not throw exceptions in normal conditions
        FtpClient result = client.noop();
        if (result != client) {
            throw new RuntimeException("noop() did not return the same instance");
        }
    }
}
