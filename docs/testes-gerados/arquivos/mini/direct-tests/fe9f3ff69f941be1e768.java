/*
 * @test
 * @summary Test sun.net.ftp.impl.FtpClient.abort() method behavior
 * @run main FtpClientWitupTest
 */

import sun.net.ftp.impl.FtpClient;
import sun.net.ftp.FtpProtocolException;
import java.io.IOException;

public class FtpClientWitupTest {
    public static void main(String[] args) throws Exception {
        FtpClient client = FtpClient.create();

        // The abort method should return the same instance (this) without error
        FtpClient result = client.abort();
        if (result != client) {
            throw new RuntimeException("abort() did not return the same FtpClient instance");
        }

        // Since abort() calls issueCommandCheck("ABOR"), which may throw exceptions,
        // we test that abort() throws no exception in normal conditions.
        // No exception means pass.

        System.out.println("Test passed: abort() returns this and does not throw under normal conditions.");
    }
}
