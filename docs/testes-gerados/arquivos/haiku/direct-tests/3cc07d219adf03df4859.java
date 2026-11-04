/*
 * @test
 * @summary Verifies argument validation and observable behavior of Socket.setSendBufferSize on the SocketAdaptor returned by SocketChannel.socket()
 * @run main SocketAdaptorWitupTest
 */

import java.io.IOException;
import java.net.Socket;
import java.nio.channels.SocketChannel;

public class SocketAdaptorWitupTest {

    public static void main(String[] args) throws Exception {
        try (SocketChannel ch = SocketChannel.open()) {
            Socket s = ch.socket();
            expectIllegalArgument(s, 0);
            expectIllegalArgument(s, -1);
            expectIllegalArgument(s, Integer.MIN_VALUE);
            s.setSendBufferSize(8192);
            if (s.getSendBufferSize() <= 0) {
                throw new RuntimeException("send buffer size not positive after set");
            }
        }
    }

    static void expectIllegalArgument(Socket s, int size) throws IOException {
        try {
            s.setSendBufferSize(size);
            throw new RuntimeException("IllegalArgumentException not thrown for size " + size);
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
