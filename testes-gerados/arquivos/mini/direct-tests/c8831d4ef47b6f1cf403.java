/*
 * @test
 * @summary Test sun.net.ftp.impl.FtpClient run() method returns a Socket using the proxy
 * @run main FtpClientWitupTest
 */

import java.net.*;
import sun.net.ftp.impl.FtpClient;

public class FtpClientWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a Proxy instance for testing
        Proxy proxy = new Proxy(Proxy.Type.DIRECT, new InetSocketAddress("localhost", 80));

        // Create an anonymous subclass of FtpClient to set the proxy field
        FtpClient client = new FtpClient() {
            {
                this.proxy = proxy;
            }
        };

        // Call run() and verify it returns a Socket
        Socket socket = client.run();
        if (socket == null) {
            throw new AssertionError("run() returned null Socket");
        }

        // Verify the socket's proxy is the one set
        Proxy socketProxy = socket.proxy();
        if (!proxy.equals(socketProxy)) {
            throw new AssertionError("Socket proxy does not match the expected proxy");
        }

        socket.close();
    }
}
