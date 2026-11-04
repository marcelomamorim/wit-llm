/*
 * @test
 * @summary Test sun.net.ftp.impl.FtpClient removeDirectory method
 * @run main FtpClientWitupTest
 */

import sun.net.ftp.impl.FtpClient;
import sun.net.ftp.FtpProtocolException;
import java.io.IOException;

public class FtpClientWitupTest {
    public static void main(String[] args) throws Exception {
        FtpClient client = FtpClient.create();

        // Test removeDirectory with a normal directory name
        FtpClient result = client.removeDirectory("testDir");
        if (result != client) {
            throw new RuntimeException("removeDirectory should return this instance");
        }

        // Test removeDirectory with empty string
        result = client.removeDirectory("");
        if (result != client) {
            throw new RuntimeException("removeDirectory should return this instance even for empty string");
        }

        // Test removeDirectory with special characters
        result = client.removeDirectory("../someDir");
        if (result != client) {
            throw new RuntimeException("removeDirectory should return this instance even for special chars");
        }

        // No exceptions expected from removeDirectory as per current code
        System.out.println("All removeDirectory tests passed");
    }
}
