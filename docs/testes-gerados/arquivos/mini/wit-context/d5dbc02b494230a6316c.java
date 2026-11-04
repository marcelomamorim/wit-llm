/*
 * @test
 * @summary Test sun.net.NetworkServer.startServer(int) for valid and invalid ports
 * @run main NetworkServerWitupTest
 */

import sun.net.NetworkServer;
import java.io.IOException;
import java.net.ServerSocket;

public class NetworkServerWitupTest {
    public static void main(String[] args) throws Exception {
        testValidPort();
        testInvalidPortNegative();
        testInvalidPortTooLarge();
    }

    private static void testValidPort() throws IOException {
        NetworkServer server = new NetworkServer();
        // Use a valid port number (e.g., 0 means any free port)
        server.startServer(0);
        ServerSocket ss = getServerSocket(server);
        if (ss == null || ss.isClosed()) {
            throw new AssertionError("ServerSocket should be open after startServer with valid port");
        }
        // Cleanup
        ss.close();
    }

    private static void testInvalidPortNegative() {
        NetworkServer server = new NetworkServer();
        try {
            server.startServer(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative port");
        } catch (IllegalArgumentException e) {
            // Expected
        } catch (IOException e) {
            // IOException is allowed if thrown by ServerSocket constructor
        }
    }

    private static void testInvalidPortTooLarge() {
        NetworkServer server = new NetworkServer();
        try {
            server.startServer(70000); // port > 65535
            throw new AssertionError("Expected IllegalArgumentException for port > 65535");
        } catch (IllegalArgumentException e) {
            // Expected
        } catch (IOException e) {
            // IOException is allowed if thrown by ServerSocket constructor
        }
    }

    // Helper method to access private serverSocket field via subclassing
    private static ServerSocket getServerSocket(NetworkServer server) {
        try {
            // NetworkServer.serverSocket is private, no public getter, so use subclass hack
            class NS extends NetworkServer {
                ServerSocket getSS() { return serverSocket; }
            }
            return ((NS)server).getSS();
        } catch (ClassCastException e) {
            return null;
        }
    }
}
