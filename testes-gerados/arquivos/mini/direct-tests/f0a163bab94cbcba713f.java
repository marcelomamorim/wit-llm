/*
 * @test
 * @summary Test ServerSocket constructor with port parameter
 * @run main ServerSocketWitupTest
 */

import java.net.ServerSocket;
import java.io.IOException;

public class ServerSocketWitupTest {
    public static void main(String[] args) throws IOException {
        // Test with a valid port number (e.g., 0 for ephemeral port)
        ServerSocket ss = new ServerSocket(0);
        int localPort = ss.getLocalPort();
        if (localPort <= 0) {
            throw new RuntimeException("Expected local port > 0, got: " + localPort);
        }
        ss.close();

        // Test with a typical valid port number (e.g., 80 or 8080)
        // Note: Binding to privileged ports (<1024) may require permissions, so use 8080
        ServerSocket ss2 = new ServerSocket(8080);
        int localPort2 = ss2.getLocalPort();
        if (localPort2 != 8080) {
            throw new RuntimeException("Expected local port 8080, got: " + localPort2);
        }
        ss2.close();

        // Test with an invalid port number (negative) - expect IOException
        boolean threw = false;
        try {
            new ServerSocket(-1);
        } catch (IOException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IOException for negative port");
        }

        // Test with an invalid port number (greater than 65535) - expect IOException
        threw = false;
        try {
            new ServerSocket(70000);
        } catch (IOException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IOException for port > 65535");
        }
    }
}
