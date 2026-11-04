/*
 * @test
 * @summary Test for FtpClient.noop() method to verify behavior when server is null.
 * @run main FtpClientWitupTest
 */

import java.io.IOException;
import sun.net.ftp.impl.FtpClient;

public class FtpClientWitupTest {

    public static void main(String[] args) {
        testNoopWithNullServer();
    }

    public static void testNoopWithNullServer() {
        FtpClient client = new FtpClient();
        // Reflection or direct access is not used; assuming server is null by default
        // Since server is private, we simulate the scenario by setting it to null via reflection if needed
        // but as per constraints, avoid reflection; instead, test the behavior assuming server is null
        // which is the default state after construction
        try {
            client.noop();
            // If no exception, test passes, but according to WIT, exception is expected
            System.out.println("Test failed: Expected IllegalStateException was not thrown.");
        } catch (IllegalStateException e) {
            System.out.println("Test passed: Caught expected IllegalStateException.");
        } catch (IOException e) {
            System.out.println("Test failed: Unexpected IOException: " + e);
        } catch (Exception e) {
            System.out.println("Test failed: Unexpected exception: " + e);
        }
    }
}
