/*
 * @test
 * @summary Verify that Socket.setReceiveBufferSize on a channel-backed socket rejects non-positive sizes and accepts positive sizes
 * @run main SocketAdaptorWitupTest
 */

import java.net.Socket;
import java.net.SocketException;
import java.nio.channels.SocketChannel;

public class SocketAdaptorWitupTest {

    public static void main(String[] args) throws Exception {
        try (SocketChannel ch = SocketChannel.open()) {
            Socket s = ch.socket();

            expectIllegalArgument(s, 0);
            expectIllegalArgument(s, -1);
            expectIllegalArgument(s, Integer.MIN_VALUE);

            s.setReceiveBufferSize(1);
            s.setReceiveBufferSize(65536);
            if (s.getReceiveBufferSize() <= 0) {
                throw new RuntimeException("receive buffer size should be positive after set");
            }

            if (s.getChannel() != ch) {
                throw new RuntimeException("socket.getChannel() should return the owning channel");
            }
        }
    }

    static void expectIllegalArgument(Socket s, int size) throws SocketException {
        try {
            s.setReceiveBufferSize(size);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for size " + size);
    }
}
