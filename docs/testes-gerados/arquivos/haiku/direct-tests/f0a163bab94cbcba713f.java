/*
 * @test
 * @summary ServerSocket(int port) binds to an ephemeral port when port is 0 and delegates to the open/bound state
 * @run main ServerSocketWitupTest
 */

import java.net.ServerSocket;

public class ServerSocketWitupTest {

    public static void main(String[] args) throws Exception {
        testPortZeroBindsEphemeralPort();
        testTwoPortZeroSocketsGetDistinctPorts();
        testCloseAfterConstruction();
    }

    static void testPortZeroBindsEphemeralPort() throws Exception {
        try (ServerSocket ss = new ServerSocket(0)) {
            check(ss.isBound(), "socket should be bound");
            check(!ss.isClosed(), "socket should be open");
            check(ss.getLocalPort() > 0, "an ephemeral port should be assigned");
            check(ss.getSoTimeout() == 0, "default SO_TIMEOUT should be 0");
        }
    }

    static void testTwoPortZeroSocketsGetDistinctPorts() throws Exception {
        try (ServerSocket a = new ServerSocket(0);
             ServerSocket b = new ServerSocket(0)) {
            check(a.getLocalPort() != b.getLocalPort(),
                  "two simultaneously open sockets must not share a port");
        }
    }

    static void testCloseAfterConstruction() throws Exception {
        ServerSocket ss = new ServerSocket(0);
        int port = ss.getLocalPort();
        check(port > 0, "port should be assigned before close");
        ss.close();
        check(ss.isClosed(), "socket should be closed after close()");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
