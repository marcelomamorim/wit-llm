/*
 * @test
 * @summary Verifies that SocketChannelImpl.write(ByteBuffer) rejects a null buffer with NullPointerException
 * @run main SocketChannelImplWitupTest
 */

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        try (SocketChannel sc = SocketChannel.open()) {
            boolean thrown = false;
            try {
                sc.write((ByteBuffer) null);
            } catch (NullPointerException expected) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("Expected NullPointerException for null buffer");
            }
        }
    }
}
