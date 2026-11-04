/*
 * @test
 * @summary Test ServerSocket(int port, int backlog) constructor for valid and invalid ports
 * @run main ServerSocketWitupTest
 */

import java.net.ServerSocket;
import java.io.IOException;

public class ServerSocketWitupTest {
    public static void main(String[] args) throws Exception {
        // Test valid port and backlog
        ServerSocket ss1 = new ServerSocket(0, 10); // ephemeral port
        int localPort = ss1.getLocalPort();
        if (localPort <= 0 || localPort > 65535) {
            throw new RuntimeException("Unexpected local port: " + localPort);
        }
        ss1.close();

        ServerSocket ss2 = new ServerSocket(80, 50); // common HTTP port
        if (ss2.getLocalPort() != 80) {
            throw new RuntimeException("Expected port 80 but got " + ss2.getLocalPort());
        }
        ss2.close();

        // Test invalid port: negative port
        try {
            new ServerSocket(-1, 10);
            throw new RuntimeException("Expected IllegalArgumentException for port -1");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // Test invalid port: port > 65535
        try {
            new ServerSocket(70000, 10);
            throw new RuntimeException("Expected IllegalArgumentException for port 70000");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
