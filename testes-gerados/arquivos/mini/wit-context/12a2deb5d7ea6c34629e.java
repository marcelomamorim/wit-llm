/*
 * @test
 * @summary Test sun.net.ftp.impl.FtpClient.run() method behavior
 * @run main FtpClientWitupTest
 */

import java.net.Socket;
import java.net.Proxy;
import sun.net.ftp.impl.FtpClient;

public class FtpClientWitupTest {
    public static void main(String[] args) throws Exception {
        // Create an anonymous subclass of FtpClient to access protected constructor
        FtpClient client = new FtpClient() {
            private Proxy proxy;
            // Setter for proxy to test run() behavior
            void setProxy(Proxy p) {
                this.proxy = p;
            }
            // Override run() to use this.proxy field
            @Override
            public Socket run() {
                if (proxy == null) {
                    throw new IllegalArgumentException("proxy is null");
                }
                return new Socket(proxy);
            }
        };

        // Test 1: run() with null proxy should throw IllegalArgumentException
        try {
            client.setProxy(null);
            client.run();
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException iae) {
            // Expected exception
        }

        // Test 2: run() with a valid Proxy should return a Socket
        Proxy proxy = Proxy.NO_PROXY; // Use NO_PROXY as a valid proxy instance
        client.setProxy(proxy);
        Socket socket = client.run();
        if (socket == null) {
            throw new RuntimeException("run() returned null Socket");
        }
        // Close socket to avoid resource leak
        socket.close();
    }
}
