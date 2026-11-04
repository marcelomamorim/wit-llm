/*
 * @test
 * @summary Verifies that SocketAdaptor.setSendBufferSize rejects non-positive sizes through the public SocketChannel.socket() view
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
            s.setSendBufferSize(4096);
            if (s.getChannel() != ch) {
                throw new RuntimeException("getChannel() did not return the adapted channel");
            }
        }
    }

    static void expectIllegalArgument(Socket s, int size) throws Exception {
        try {
            s.setSendBufferSize(size);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for size " + size);
    }
}
