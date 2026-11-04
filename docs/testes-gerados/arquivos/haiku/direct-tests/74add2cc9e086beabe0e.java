/*
 * @test
 * @summary ServerSocket(int port, int backlog) binds an ephemeral port, accepts connections and rejects out-of-range ports
 * @run main ServerSocketWitupTest
 */
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketWitupTest {
    public static void main(String[] args) throws Exception {
        try (ServerSocket ss = new ServerSocket(0, 10)) {
            if (!ss.isBound()) {
                throw new AssertionError("server socket should be bound");
            }
            if (ss.isClosed()) {
                throw new AssertionError("server socket should not be closed");
            }
            int port = ss.getLocalPort();
            if (port <= 0) {
                throw new AssertionError("expected ephemeral port > 0 but was " + port);
            }
            try (Socket client = new Socket(InetAddress.getLoopbackAddress(), port);
                 Socket accepted = ss.accept()) {
                if (accepted.getPort() != client.getLocalPort()) {
                    throw new AssertionError("accepted peer port mismatch");
                }
            }
        }

        try {
            new ServerSocket(-1, 10);
            throw new AssertionError("expected IllegalArgumentException for port -1");
        } catch (IllegalArgumentException expected) {
        }

        try {
            new ServerSocket(65536, 10);
            throw new AssertionError("expected IllegalArgumentException for port 65536");
        } catch (IllegalArgumentException expected) {
        }
    }
}
