/*
 * @test
 * @summary Verify that SocketAdaptor.setReceiveBufferSize rejects non-positive sizes through the public Socket API
 * @run main SocketAdaptorWitupTest
 */
import java.net.Socket;
import java.nio.channels.SocketChannel;

public class SocketAdaptorWitupTest {
    public static void main(String[] args) throws Exception {
        try (SocketChannel ch = SocketChannel.open()) {
            Socket s = ch.socket();
            expectIllegalArgument(s, 0);
            expectIllegalArgument(s, -1);
            expectIllegalArgument(s, Integer.MIN_VALUE);
            s.setReceiveBufferSize(1024);
        }
    }

    private static void expectIllegalArgument(Socket s, int size) throws Exception {
        try {
            s.setReceiveBufferSize(size);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for size " + size);
    }
}
