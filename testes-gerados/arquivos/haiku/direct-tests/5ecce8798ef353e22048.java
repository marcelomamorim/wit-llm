/*
 * @test
 * @summary Verify getOption argument validation and basic retrieval through the public AsynchronousSocketChannel API
 * @run main AsynchronousSocketChannelImplWitupTest
 */

import java.io.IOException;
import java.net.SocketOption;
import java.net.StandardSocketOptions;
import java.nio.channels.AsynchronousSocketChannel;

public class AsynchronousSocketChannelImplWitupTest {
    public static void main(String[] args) throws IOException {
        try (AsynchronousSocketChannel ch = AsynchronousSocketChannel.open()) {
            Integer rcvBuf = ch.getOption(StandardSocketOptions.SO_RCVBUF);
            if (rcvBuf == null || rcvBuf <= 0) {
                throw new RuntimeException("SO_RCVBUF should be a positive Integer");
            }

            Boolean reuse = ch.getOption(StandardSocketOptions.SO_REUSEADDR);
            if (reuse == null) {
                throw new RuntimeException("SO_REUSEADDR should return a non-null Boolean");
            }

            try {
                ch.getOption((SocketOption<Integer>) null);
                throw new RuntimeException("expected NullPointerException for null option");
            } catch (NullPointerException expected) {
                // expected
            }

            try {
                ch.getOption(StandardSocketOptions.IP_MULTICAST_TTL);
                throw new RuntimeException("expected UnsupportedOperationException for unsupported option");
            } catch (UnsupportedOperationException expected) {
                // expected
            }
        }
    }
}
