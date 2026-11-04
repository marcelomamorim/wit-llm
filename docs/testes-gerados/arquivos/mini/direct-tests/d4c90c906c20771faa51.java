/*
 * @test
 * @summary Test sun.net.ftp.impl.FtpClient.makeDirectory(String) method
 * @run main sun.net.ftp.impl.FtpClientWitupTest
 */

import sun.net.ftp.impl.FtpClient;
import sun.net.ftp.FtpProtocolException;
import java.io.IOException;

public class FtpClientWitupTest {
    public static void main(String[] args) throws Exception {
        FtpClient client = FtpClient.create();

        // Test with a normal directory name
        try {
            FtpClient result = client.makeDirectory("testDir");
            if (result != client) {
                throw new RuntimeException("makeDirectory should return this instance");
            }
        } catch (FtpProtocolException | IOException e) {
            // These exceptions are declared; print stack trace for visibility
            e.printStackTrace();
        }

        // Test with empty string directory name
        try {
            FtpClient result = client.makeDirectory("");
            if (result != client) {
                throw new RuntimeException("makeDirectory should return this instance even for empty name");
            }
        } catch (FtpProtocolException | IOException e) {
            e.printStackTrace();
        }

        // Test with null directory name - expect NullPointerException or FtpProtocolException or IOException
        try {
            client.makeDirectory(null);
            throw new RuntimeException("Expected exception for null directory name");
        } catch (NullPointerException npe) {
            // Expected, pass
        } catch (FtpProtocolException | IOException e) {
            // Acceptable exceptions from underlying implementation
        }
    }
}
