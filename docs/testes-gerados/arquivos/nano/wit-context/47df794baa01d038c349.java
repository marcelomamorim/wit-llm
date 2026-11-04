/*
 * @test
 * @summary Test removeDirectory method for null server state
 * @run main FtpClientWitupTest
 */

import sun.net.ftp.impl.FtpClient;
import sun.net.ftp.FtpProtocolException;
import java.io.IOException;

public class FtpClientWitupTest {

    public static void main(String[] args) {
        testRemoveDirectoryWithNullServer();
    }

    public static void testRemoveDirectoryWithNullServer() {
        FtpClient client = new FtpClient();
        // Simulate null server state
        client.server = null;
        try {
            client.removeDirectory("testDir");
            System.out.println("Test failed: Expected IllegalStateException was not thrown.");
        } catch (IllegalStateException e) {
            System.out.println("Test passed: Caught expected IllegalStateException.");
        } catch (FtpProtocolException | IOException e) {
            System.out.println("Test failed: Unexpected exception: " + e);
        } catch (Exception e) {
            System.out.println("Test failed: Unexpected exception: " + e);
        }
    }
}
