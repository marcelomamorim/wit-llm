/*
 * @test
 * @summary ServerSocketAdaptor.setReceiveBufferSize rejects non-positive sizes and accepts positive sizes
 * @run main ServerSocketAdaptorWitupTest
 */

import java.net.ServerSocket;
import java.nio.channels.ServerSocketChannel;

public class ServerSocketAdaptorWitupTest {

    public static void main(String[] args) throws Exception {
        try (ServerSocketChannel ch = ServerSocketChannel.open()) {
            ServerSocket ss = ch.socket();
            expectIllegalArgument(ss, 0);
            expectIllegalArgument(ss, -1);
            expectIllegalArgument(ss, Integer.MIN_VALUE);
            ss.setReceiveBufferSize(8192);
            if (ss.getReceiveBufferSize() <= 0) {
                throw new RuntimeException("receive buffer size not positive after set");
            }
        }
    }

    static void expectIllegalArgument(ServerSocket ss, int size) throws Exception {
        try {
            ss.setReceiveBufferSize(size);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("no IllegalArgumentException for size " + size);
    }
}
