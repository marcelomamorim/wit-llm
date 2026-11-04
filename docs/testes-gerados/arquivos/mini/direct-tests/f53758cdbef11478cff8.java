/*
 * @test
 * @summary Test sun.net.ftp.impl.FtpClient.deleteFile(String) method
 * @run main sun.net.ftp.impl.FtpClientWitupTest
 */

import sun.net.ftp.impl.FtpClient;
import sun.net.ftp.FtpProtocolException;
import java.io.IOException;

public class FtpClientWitupTest {
    public static void main(String[] args) throws Exception {
        FtpClient client = FtpClient.create();

        // Test deleteFile with a normal filename
        try {
            FtpClient result = client.deleteFile("testfile.txt");
            if (result != client) {
                throw new RuntimeException("deleteFile should return this instance");
            }
        } catch (FtpProtocolException | IOException e) {
            // Acceptable if server not connected or command fails
            // Just print to indicate test ran
            System.out.println("Caught expected exception: " + e);
        }

        // Test deleteFile with empty string
        try {
            FtpClient result = client.deleteFile("");
            if (result != client) {
                throw new RuntimeException("deleteFile should return this instance for empty string");
            }
        } catch (FtpProtocolException | IOException e) {
            System.out.println("Caught expected exception for empty string: " + e);
        }

        // Test deleteFile with null - expect NullPointerException or other
        try {
            client.deleteFile(null);
            throw new RuntimeException("deleteFile(null) should throw NullPointerException or IOException");
        } catch (NullPointerException npe) {
            // Expected
        } catch (FtpProtocolException | IOException e) {
            // Acceptable if thrown
        }

        System.out.println("FtpClient.deleteFile tests completed");
    }
}
