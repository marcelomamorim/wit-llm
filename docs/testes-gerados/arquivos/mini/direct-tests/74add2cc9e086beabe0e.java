/*
 * @test
 * @summary Test ServerSocket(int port, int backlog) constructor behavior
 * @run main ServerSocketWitupTest
 */

import java.net.ServerSocket;
import java.io.IOException;

public class ServerSocketWitupTest {
    public static void main(String[] args) throws Exception {
        testValidPortAndBacklog();
        testZeroPort();
        testNegativeBacklog();
    }

    private static void testValidPortAndBacklog() throws IOException {
        ServerSocket ss = new ServerSocket(0, 10);
        int localPort = ss.getLocalPort();
        if (localPort <= 0) {
            throw new RuntimeException("Expected local port > 0, got: " + localPort);
        }
        ss.close();
    }

    private static void testZeroPort() throws IOException {
        ServerSocket ss = new ServerSocket(0, 50);
        int localPort = ss.getLocalPort();
        if (localPort <= 0) {
            throw new RuntimeException("Expected local port > 0 for ephemeral port, got: " + localPort);
        }
        ss.close();
    }

    private static void testNegativeBacklog() throws IOException {
        // Negative backlog is allowed by the constructor, but may be adjusted internally.
        // We test that socket is still created and bound.
        ServerSocket ss = new ServerSocket(0, -1);
        int localPort = ss.getLocalPort();
        if (localPort <= 0) {
            throw new RuntimeException("Expected local port > 0 even with negative backlog, got: " + localPort);
        }
        ss.close();
    }
}
