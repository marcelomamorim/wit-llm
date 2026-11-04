/*
 * @test
 * @summary Verify DatagramSocketAdaptor.setReceiveBufferSize rejects non-positive sizes and accepts positive sizes
 * @run main DatagramSocketAdaptorWitupTest
 */

import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.channels.DatagramChannel;

public class DatagramSocketAdaptorWitupTest {

    public static void main(String[] args) throws Exception {
        try (DatagramChannel ch = DatagramChannel.open()) {
            DatagramSocket s = ch.socket();

            s.setReceiveBufferSize(8192);
            int before = s.getReceiveBufferSize();
            if (before <= 0) {
                throw new RuntimeException("receive buffer size not positive: " + before);
            }

            expectIllegalArgument(s, 0);
            expectIllegalArgument(s, -1);
            expectIllegalArgument(s, Integer.MIN_VALUE);

            int after = s.getReceiveBufferSize();
            if (after != before) {
                throw new RuntimeException("rejected size changed buffer size: "
                        + before + " -> " + after);
            }

            s.setReceiveBufferSize(1);
            if (s.getReceiveBufferSize() <= 0) {
                throw new RuntimeException("small positive size not applied");
            }
        }
    }

    static void expectIllegalArgument(DatagramSocket s, int size) throws SocketException {
        try {
            s.setReceiveBufferSize(size);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for size " + size);
    }
}
