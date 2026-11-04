/*
 * @test
 * @summary Checks public ServerSocket(int, int, InetAddress) port range validation and loopback binding
 * @run main ServerSocketWitupTest
 */

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class ServerSocketWitupTest {

    public static void main(String[] args) throws Exception {
        testValidLoopbackBind();
        expectIllegalArgument(-1, 10, InetAddress.getLoopbackAddress());
        expectIllegalArgument(65536, 10, InetAddress.getLoopbackAddress());
        expectIllegalArgument(Integer.MIN_VALUE, 0, InetAddress.getLoopbackAddress());
        expectIllegalArgument(Integer.MAX_VALUE, 50, InetAddress.getLoopbackAddress());
    }

    static void testValidLoopbackBind() throws IOException {
        try (ServerSocket s = new ServerSocket(0, 0, InetAddress.getLoopbackAddress())) {
            if (!s.isBound()) {
                throw new RuntimeException("socket should be bound");
            }
            if (s.getLocalPort() <= 0) {
                throw new RuntimeException("unexpected local port: " + s.getLocalPort());
            }
        }
    }

    static void expectIllegalArgument(int port, int backlog, InetAddress addr) throws IOException {
        try (ServerSocket s = new ServerSocket(port, backlog, addr)) {
            throw new RuntimeException("no IllegalArgumentException for port " + port);
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
