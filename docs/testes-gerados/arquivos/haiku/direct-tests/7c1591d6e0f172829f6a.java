/*
 * @test
 * @summary Verifies ServerSocket.setReceiveBufferSize argument validation, closed-socket behavior and observable effect on open sockets
 * @run main ServerSocketWitupTest
 */

import java.net.ServerSocket;
import java.net.SocketException;

public class ServerSocketWitupTest {

    public static void main(String[] args) throws Exception {
        testNonPositiveSizeThrowsIllegalArgument();
        testClosedSocketThrowsSocketException();
        testValidSizeOnOpenSocketIsAccepted();
    }

    static void testNonPositiveSizeThrowsIllegalArgument() throws Exception {
        try (ServerSocket s = new ServerSocket()) {
            expectIllegalArgument(s, 0);
            expectIllegalArgument(s, -1);
            expectIllegalArgument(s, Integer.MIN_VALUE);
        }
    }

    static void expectIllegalArgument(ServerSocket s, int size) throws Exception {
        try {
            s.setReceiveBufferSize(size);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for size " + size);
    }

    static void testClosedSocketThrowsSocketException() throws Exception {
        ServerSocket s = new ServerSocket(0);
        s.close();
        if (!s.isClosed()) {
            throw new RuntimeException("Socket should be closed");
        }
        try {
            s.setReceiveBufferSize(4096);
        } catch (SocketException expected) {
            return;
        }
        throw new RuntimeException("Expected SocketException on closed socket");
    }

    static void testValidSizeOnOpenSocketIsAccepted() throws Exception {
        try (ServerSocket s = new ServerSocket(0)) {
            s.setReceiveBufferSize(65536);
            int size = s.getReceiveBufferSize();
            if (size <= 0) {
                throw new RuntimeException("Unexpected receive buffer size: " + size);
            }
        }
    }
}
