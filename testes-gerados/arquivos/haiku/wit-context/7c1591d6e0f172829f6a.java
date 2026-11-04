/*
 * @test
 * @summary WITUP-generated checks for ServerSocket.setReceiveBufferSize argument validation and closed-socket behavior
 * @run main ServerSocketWitupTest
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;

public class ServerSocketWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectIllegalArgument(int size) throws IOException {
        ServerSocket ss = new ServerSocket();
        try {
            ss.setReceiveBufferSize(size);
            throw new RuntimeException("IllegalArgumentException expected for size " + size);
        } catch (IllegalArgumentException expected) {
            // expected
        } finally {
            ss.close();
        }
    }

    static void testNonPositiveSizesRejected() throws IOException {
        expectIllegalArgument(0);
        expectIllegalArgument(-1);
        expectIllegalArgument(Integer.MIN_VALUE);
    }

    static void testPositiveSizeAcceptedOnUnboundSocket() throws IOException {
        ServerSocket ss = new ServerSocket();
        try {
            ss.setReceiveBufferSize(4096);
            check(!ss.isClosed(), "socket should remain open after setting receive buffer size");
        } finally {
            ss.close();
        }
    }

    static void testClosedSocketRejected() throws IOException {
        ServerSocket ss = new ServerSocket();
        ss.close();
        try {
            ss.setReceiveBufferSize(1024);
            throw new RuntimeException("SocketException expected on closed socket");
        } catch (SocketException expected) {
            // expected
        }
    }

    public static void main(String[] args) throws IOException {
        testNonPositiveSizesRejected();
        testPositiveSizeAcceptedOnUnboundSocket();
        testClosedSocketRejected();
    }
}
