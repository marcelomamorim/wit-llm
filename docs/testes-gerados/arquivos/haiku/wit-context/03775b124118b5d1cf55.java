/*
 * @test
 * @summary Verifies that SocketChannel.read(ByteBuffer) rejects a null buffer with NullPointerException
 * @run main SocketChannelImplWitupTest
 */

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketChannelImplWitupTest {
    public static void main(String[] args) throws Exception {
        try (SocketChannel sc = SocketChannel.open()) {
            try {
                sc.read((ByteBuffer) null);
                throw new AssertionError("expected NullPointerException");
            } catch (NullPointerException expected) {
                // expected
            }
        }
    }
}
