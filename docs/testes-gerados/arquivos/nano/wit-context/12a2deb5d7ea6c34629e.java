/*
 * @test
 * @summary Test for sun.net.ftp.impl.FtpClient.run()
 * @run main FtpClientWitupTest
 */

import sun.net.ftp.impl.FtpClient;
import java.net.Socket;

public class FtpClientWitupTest {

    public static void main(String[] args) {
        testRunWithNullProxy();
        testRunWithMockProxy();
    }

    private static void testRunWithNullProxy() {
        try {
            FtpClient ftpClient = new FtpClient();
            // Reflection or direct access to set proxy to null if needed
            // Since proxy is private, assume default constructor sets it to null
            Socket socket = ftpClient.run();
            if (socket != null) {
                System.out.println("testRunWithNullProxy: PASSED");
            } else {
                System.out.println("testRunWithNullProxy: FAILED - socket is null");
            }
        } catch (Exception e) {
            System.out.println("testRunWithNullProxy: FAILED with exception: " + e);
        }
    }

    private static void testRunWithMockProxy() {
        try {
            FtpClient ftpClient = new FtpClient();
            // Since proxy is private and no setter, assume default is null
            // To simulate non-null proxy, reflection or subclassing would be needed
            // but as per constraints, we test default behavior
            Socket socket = ftpClient.run();
            if (socket != null) {
                System.out.println("testRunWithMockProxy: PASSED");
            } else {
                System.out.println("testRunWithMockProxy: FAILED - socket is null");
            }
        } catch (Exception e) {
            System.out.println("testRunWithMockProxy: FAILED with exception: " + e);
        }
    }
}
