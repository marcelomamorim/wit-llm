/*
 * @test
 * @summary ServerSocket.setSocketFactory accepts the first factory and rejects a second one with SocketException
 * @run main ServerSocketWitupTest
 */

import java.net.ServerSocket;
import java.net.SocketException;
import java.net.SocketImplFactory;

public class ServerSocketWitupTest {
    public static void main(String[] args) throws Exception {
        SocketImplFactory first = () -> null;
        ServerSocket.setSocketFactory(first);

        boolean thrown = false;
        try {
            ServerSocket.setSocketFactory(() -> null);
        } catch (SocketException e) {
            thrown = true;
            if (!"factory already defined".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }
        if (!thrown) {
            throw new RuntimeException("expected SocketException on second setSocketFactory call");
        }
    }
}
