/*
 * @test
 * @summary Verifies ServerSocket(int port) rejects out-of-range ports and accepts ephemeral port 0
 * @run main ServerSocketWitupTest
 */

import java.io.IOException;
import java.net.ServerSocket;

public class ServerSocketWitupTest {

    public static void main(String[] args) throws Exception {
        checkOutOfRange(-1);
        checkOutOfRange(65536);
        checkOutOfRange(Integer.MIN_VALUE);
        checkOutOfRange(Integer.MAX_VALUE);
        checkEphemeralPortBinding();
    }

    static void checkOutOfRange(int port) throws IOException {
        boolean thrown = false;
        try (ServerSocket s = new ServerSocket(port)) {
            System.err.println("No exception for port " + port);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for port " + port);
        }
    }

    static void checkEphemeralPortBinding() throws IOException {
        ServerSocket s = new ServerSocket(0);
        try {
            if (!s.isBound()) {
                throw new RuntimeException("Socket should be bound after constructor with port 0");
            }
            if (s.isClosed()) {
                throw new RuntimeException("Socket should not be closed after construction");
            }
            if (s.getLocalPort() <= 0) {
                throw new RuntimeException("Expected allocated local port > 0, got " + s.getLocalPort());
            }
        } finally {
            s.close();
        }
        if (!s.isClosed()) {
            throw new RuntimeException("Socket should be closed after close()");
        }
    }
}
