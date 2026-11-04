/*
 * @test
 * @summary Verify ServerSocket.setSocketFactory accepts the first factory and rejects a second one with SocketException
 * @run main ServerSocketWitupTest
 */

import java.net.ServerSocket;
import java.net.SocketException;
import java.net.SocketImplFactory;

public class ServerSocketWitupTest {
    public static void main(String[] args) throws Exception {
        ServerSocket.setSocketFactory(null);
        SocketImplFactory f = () -> null;
        ServerSocket.setSocketFactory(f);
        boolean thrown = false;
        try {
            ServerSocket.setSocketFactory(f);
        } catch (SocketException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected SocketException when factory already defined");
        }
    }
}
