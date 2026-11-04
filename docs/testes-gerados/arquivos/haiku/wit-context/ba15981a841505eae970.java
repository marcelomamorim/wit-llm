/*
 * @test
 * @summary Verify that DatagramSocketAdaptor.setReceiveBufferSize rejects non-positive sizes and accepts a positive size
 * @run main DatagramSocketAdaptorWitupTest
 */

import java.net.DatagramSocket;
import java.nio.channels.DatagramChannel;

public class DatagramSocketAdaptorWitupTest {

    public static void main(String[] args) throws Exception {
        try (DatagramChannel dc = DatagramChannel.open()) {
            DatagramSocket s = dc.socket();
            checkIllegal(s, 0);
            checkIllegal(s, -1);
            checkIllegal(s, Integer.MIN_VALUE);
            s.setReceiveBufferSize(8192);
            if (s.getReceiveBufferSize() <= 0) {
                throw new RuntimeException("receive buffer size must be positive");
            }
        }
    }

    private static void checkIllegal(DatagramSocket s, int size) throws Exception {
        try {
            s.setReceiveBufferSize(size);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for size " + size);
    }
}
