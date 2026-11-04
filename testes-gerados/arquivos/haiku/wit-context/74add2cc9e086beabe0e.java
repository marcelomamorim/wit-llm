/*
 * @test
 * @summary Verifies ServerSocket(int port, int backlog) port validation and basic binding behavior
 * @run main ServerSocketWitupTest
 */

import java.io.IOException;
import java.net.ServerSocket;

public class ServerSocketWitupTest {
    public static void main(String[] args) throws IOException {
        testEphemeralPortBinds();
        testBacklogValuesAccepted();
        expectIllegalArgument(-1, 50);
        expectIllegalArgument(65536, 50);
        expectIllegalArgument(-1000, 0);
    }

    static void testEphemeralPortBinds() throws IOException {
        try (ServerSocket ss = new ServerSocket(0, 50)) {
            if (!ss.isBound()) {
                throw new RuntimeException("socket not bound");
            }
            if (ss.getLocalPort() <= 0) {
                throw new RuntimeException("port not allocated: " + ss.getLocalPort());
            }
            if (ss.isClosed()) {
                throw new RuntimeException("socket unexpectedly closed");
            }
        }
    }

    static void testBacklogValuesAccepted() throws IOException {
        int[] backlogs = {0, 1, -1};
        for (int b : backlogs) {
            try (ServerSocket ss = new ServerSocket(0, b)) {
                if (!ss.isBound()) {
                    throw new RuntimeException("not bound for backlog " + b);
                }
            }
        }
    }

    static void expectIllegalArgument(int port, int backlog) {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(port, backlog);
        } catch (IllegalArgumentException e) {
            return;
        } catch (IOException e) {
            throw new RuntimeException("unexpected IOException for port " + port, e);
        }
        try {
            ss.close();
        } catch (IOException e) {
            throw new RuntimeException("close failed", e);
        }
        throw new RuntimeException("expected IllegalArgumentException for port " + port);
    }
}
