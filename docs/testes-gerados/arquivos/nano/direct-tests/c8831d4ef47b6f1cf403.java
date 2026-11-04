/*
 * @test
 * @summary Test FtpClient.run() method for correct socket creation.
 * @run main FtpClientWitupTest
 */

import java.net.*;
import sun.net.ftp.impl.FtpClient;

public class FtpClientWitupTest {

    public static void main(String[] args) {
        testRunReturnsSocket();
    }

    public static void testRunReturnsSocket() {
        try {
            // Instantiate FtpClient
            FtpClient client = new FtpClient();
            // Invoke run method
            Socket socket = client.run();
            // Verify that the returned object is a Socket
            if (socket == null) {
                throw new AssertionError("run() returned null");
            }
            // Verify that the socket is an instance of Socket
            if (!(socket instanceof Socket)) {
                throw new AssertionError("run() did not return a Socket instance");
            }
            // Optionally, verify that the socket is connected or not (since it's a new socket, likely not connected)
            System.out.println("Test passed: run() returned a valid Socket instance.");
        } catch (Throwable t) {
            t.printStackTrace();
            throw new AssertionError("Test failed: " + t.getMessage(), t);
        }
    }
}
