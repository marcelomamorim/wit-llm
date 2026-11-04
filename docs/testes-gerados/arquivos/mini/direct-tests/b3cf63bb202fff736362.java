/*
 * @test
 * @summary Test sun.net.ftp.impl.FtpClient.rename method behavior
 * @run main FtpClientWitupTest
 */

import sun.net.ftp.impl.FtpClient;
import sun.net.ftp.FtpProtocolException;
import java.io.IOException;

public class FtpClientWitupTest {

    public static void main(String[] args) throws Exception {
        FtpClient client = FtpClient.create();

        // Test rename with normal strings
        FtpClient result = client.rename("oldName.txt", "newName.txt");
        if (result != client) {
            throw new RuntimeException("rename should return the same FtpClient instance");
        }

        // Test rename with empty strings
        result = client.rename("", "");
        if (result != client) {
            throw new RuntimeException("rename with empty strings should return the same FtpClient instance");
        }

        // Test rename with special characters
        result = client.rename("file@123", "file#456");
        if (result != client) {
            throw new RuntimeException("rename with special characters should return the same FtpClient instance");
        }

        // Test rename with null arguments - expect NullPointerException
        try {
            client.rename(null, "target");
            throw new RuntimeException("Expected NullPointerException for null 'from' argument");
        } catch (NullPointerException e) {
            // expected
        }

        try {
            client.rename("source", null);
            throw new RuntimeException("Expected NullPointerException for null 'to' argument");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
