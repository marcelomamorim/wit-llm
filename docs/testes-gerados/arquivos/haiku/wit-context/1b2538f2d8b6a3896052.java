/*
 * @test
 * @summary Verify ServerSocketAdaptor.setReceiveBufferSize rejects non-positive sizes and accepts a positive size
 * @run main ServerSocketAdaptorWitupTest
 */

import java.net.ServerSocket;
import java.nio.channels.ServerSocketChannel;

public class ServerSocketAdaptorWitupTest {
    public static void main(String[] args) throws Exception {
        try (ServerSocketChannel ch = ServerSocketChannel.open();
             ServerSocket ss = ch.socket()) {
            int[] invalid = {0, -1, Integer.MIN_VALUE};
            for (int size : invalid) {
                try {
                    ss.setReceiveBufferSize(size);
                    throw new RuntimeException("expected IllegalArgumentException for " + size);
                } catch (IllegalArgumentException expected) {
                }
            }
            ss.setReceiveBufferSize(4096);
        }
    }
}
