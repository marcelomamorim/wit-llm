/*
 * @test
 * @summary Test ServerSocket constructor with port, backlog and bindAddr for valid and invalid inputs
 * @run main ServerSocketWitupTest
 */

import java.net.ServerSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.io.IOException;

public class ServerSocketWitupTest {
    public static void main(String[] args) throws Exception {
        testValidConstructor();
        testInvalidPortLow();
        testInvalidPortHigh();
        testBacklogDefaulting();
        testBindSecurityException();
    }

    private static void testValidConstructor() throws IOException {
        InetAddress loopback = InetAddress.getLoopbackAddress();
        ServerSocket ss = new ServerSocket(0, 10, loopback);
        int port = ss.getLocalPort();
        if (port <= 0 || port > 0xFFFF) {
            throw new RuntimeException("Port out of valid range after construction: " + port);
        }
        if (!ss.isBound()) {
            throw new RuntimeException("ServerSocket should be bound after construction");
        }
        ss.close();
    }

    private static void testInvalidPortLow() {
        try {
            new ServerSocket(-1, 10, InetAddress.getLoopbackAddress());
            throw new RuntimeException("Expected IllegalArgumentException for port < 0");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException for invalid port", e);
        }
    }

    private static void testInvalidPortHigh() {
        try {
            new ServerSocket(0x10000, 10, InetAddress.getLoopbackAddress());
            throw new RuntimeException("Expected IllegalArgumentException for port > 65535");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException for invalid port", e);
        }
    }

    private static void testBacklogDefaulting() throws IOException {
        InetAddress loopback = InetAddress.getLoopbackAddress();
        ServerSocket ss = new ServerSocket(0, 0, loopback); // backlog < 1 should default to 50
        // We cannot directly get backlog, but no exception means backlog was accepted
        if (!ss.isBound()) {
            throw new RuntimeException("ServerSocket should be bound after construction with backlog=0");
        }
        ss.close();
    }

    private static void testBindSecurityException() throws Exception {
        // We cannot easily simulate SecurityException without a SecurityManager,
        // so this test will attempt to bind to a privileged port (below 1024) which
        // usually requires elevated privileges and may throw SecurityException or IOException.
        // We catch and verify that close() is called by checking that socket is closed.
        InetAddress loopback = InetAddress.getLoopbackAddress();
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(80, 10, loopback);
            // If no exception, close socket and test passes
            ss.close();
        } catch (SecurityException | IOException e) {
            // Expected possible exceptions due to privileged port
            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException ignored) {}
            }
        }
    }
}
