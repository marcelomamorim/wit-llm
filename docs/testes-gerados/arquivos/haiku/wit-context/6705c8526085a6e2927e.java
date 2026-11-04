/*
 * @test
 * @summary Verifies that SocketChannelImpl.connect(null) throws NullPointerException and leaves the channel open and unconnected
 * @run main SocketChannelImplWitupTest
 */

import java.nio.channels.SocketChannel;

public class SocketChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        try (SocketChannel sc = SocketChannel.open()) {
            boolean thrown = false;
            try {
                sc.connect(null);
            } catch (NullPointerException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("Expected NullPointerException for null address");
            }
            if (!sc.isOpen()) {
                throw new RuntimeException("Channel must remain open after failed validation");
            }
            if (sc.isConnected()) {
                throw new RuntimeException("Channel must not be connected after failed validation");
            }
        }
    }
}
