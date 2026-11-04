/*
 * @test
 * @summary Test sun.net.ftp.impl.FtpClient.siteCmd(String) method
 * @run main FtpClientWitupTest
 */

import sun.net.ftp.impl.FtpClient;
import sun.net.ftp.FtpProtocolException;
import java.io.IOException;

public class FtpClientWitupTest {
    public static void main(String[] args) throws Exception {
        FtpClient client = FtpClient.create();

        // Test normal usage with a simple SITE command
        FtpClient returned = client.siteCmd("HELP");
        if (returned != client) {
            throw new AssertionError("siteCmd should return this instance");
        }

        // Test with empty command
        returned = client.siteCmd("");
        if (returned != client) {
            throw new AssertionError("siteCmd should return this instance even with empty command");
        }

        // Test with command containing spaces
        returned = client.siteCmd("CHMOD 755 /path/to/file");
        if (returned != client) {
            throw new AssertionError("siteCmd should return this instance even with complex command");
        }

        // No explicit exception test because siteCmd only throws exceptions from issueCommandCheck
        // which depends on server interaction not available here.

        System.out.println("FtpClientWitupTest passed");
    }
}
