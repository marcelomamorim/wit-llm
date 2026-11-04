/*
 * @test
 * @summary Verifies public behavior of ServerSocket(int port, int backlog, InetAddress bindAddr): port range validation and binding with backlog below 1.
 * @run main ServerSocketWitupTest
 */

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class ServerSocketWitupTest {

    public static void main(String[] args) throws Exception {
        InetAddress lo = InetAddress.getLoopbackAddress();

        expectIllegalArgument(-1, lo);
        expectIllegalArgument(0x10000, lo);
        expectIllegalArgument(Integer.MIN_VALUE, lo);

        try (ServerSocket ss = new ServerSocket(0, 0, lo)) {
            checkBoundOnLoopback(ss);
        }

        try (ServerSocket ss = new ServerSocket(0, -5, lo)) {
            checkBoundOnLoopback(ss);
        }
    }

    static void expectIllegalArgument(int port, InetAddress addr) {
        try {
            ServerSocket ss = new ServerSocket(port, 50, addr);
            ss.close();
            throw new RuntimeException("Expected IllegalArgumentException for port " + port);
        } catch (IllegalArgumentException expected) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException for port " + port, e);
        }
    }

    static void checkBoundOnLoopback(ServerSocket ss) {
        if (!ss.isBound()) {
            throw new RuntimeException("ServerSocket should be bound");
        }
        if (ss.getLocalPort() <= 0) {
            throw new RuntimeException("Expected an ephemeral local port, got " + ss.getLocalPort());
        }
        if (!ss.getInetAddress().isLoopbackAddress()) {
            throw new RuntimeException("Expected loopback bind address, got " + ss.getInetAddress());
        }
    }
}
