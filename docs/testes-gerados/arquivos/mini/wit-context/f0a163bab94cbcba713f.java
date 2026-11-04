/*
 * @test
 * @summary Test ServerSocket(int port) constructor for valid and invalid port values
 * @run main ServerSocketWitupTest
 */

import java.net.ServerSocket;
import java.io.IOException;

public class ServerSocketWitupTest {
    public static void main(String[] args) throws Exception {
        testValidPort();
        testInvalidPortNegative();
        testInvalidPortTooLarge();
    }

    private static void testValidPort() throws IOException {
        // Test with a valid port number (e.g., 0 means ephemeral port)
        ServerSocket ss = new ServerSocket(0);
        int localPort = ss.getLocalPort();
        if (localPort <= 0 || localPort > 65535) {
            throw new RuntimeException("ServerSocket bound to invalid port: " + localPort);
        }
        ss.close();
    }

    private static void testInvalidPortNegative() {
        // Test with invalid negative port, expect IllegalArgumentException
        try {
            new ServerSocket(-1);
            throw new RuntimeException("Expected IllegalArgumentException for port -1");
        } catch (IllegalArgumentException e) {
            // Expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException for port -1", e);
        }
    }

    private static void testInvalidPortTooLarge() {
        // Test with invalid port > 65535, expect IllegalArgumentException
        try {
            new ServerSocket(70000);
            throw new RuntimeException("Expected IllegalArgumentException for port 70000");
        } catch (IllegalArgumentException e) {
            // Expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException for port 70000", e);
        }
    }
}
