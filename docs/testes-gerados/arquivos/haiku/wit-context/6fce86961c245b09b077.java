/*
 * @test
 * @summary Verify SocketAdaptor.connect(SocketAddress) rejects a null address with IllegalArgumentException
 * @run main SocketAdaptorWitupTest
 */

import java.net.Socket;
import java.nio.channels.SocketChannel;

public class SocketAdaptorWitupTest {
    public static void main(String[] args) throws Exception {
        try (SocketChannel sc = SocketChannel.open()) {
            Socket s = sc.socket();
            try {
                s.connect(null);
                throw new RuntimeException("Expected IllegalArgumentException");
            } catch (IllegalArgumentException expected) {
            }
            if (!sc.isOpen()) {
                throw new RuntimeException("Channel closed unexpectedly");
            }
        }
    }
}
