/*
 * @test
 * @summary Verify that SocketAdaptor.setSoTimeout rejects negative timeouts and keeps the previous value
 * @run main SocketAdaptorWitupTest
 */

import java.net.Socket;
import java.nio.channels.SocketChannel;

public class SocketAdaptorWitupTest {

    public static void main(String[] args) throws Exception {
        try (SocketChannel sc = SocketChannel.open()) {
            Socket s = sc.socket();

            s.setSoTimeout(0);
            if (s.getSoTimeout() != 0) {
                throw new AssertionError("timeout 0 not stored");
            }

            s.setSoTimeout(1500);
            if (s.getSoTimeout() != 1500) {
                throw new AssertionError("positive timeout not stored");
            }

            expectIllegalArgument(s, -1);
            expectIllegalArgument(s, Integer.MIN_VALUE);

            if (s.getSoTimeout() != 1500) {
                throw new AssertionError("previous timeout changed after rejected value");
            }
        }
    }

    private static void expectIllegalArgument(Socket s, int timeout) throws Exception {
        try {
            s.setSoTimeout(timeout);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new AssertionError("expected IllegalArgumentException for negative timeout");
    }
}
