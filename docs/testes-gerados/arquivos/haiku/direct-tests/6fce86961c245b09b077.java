/*
 * @test
 * @summary Verify SocketAdaptor.connect(SocketAddress) argument validation and blocking-mode behavior through the public SocketChannel.socket() API
 * @run main SocketAdaptorWitupTest
 */

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.IllegalBlockingModeException;
import java.nio.channels.SocketChannel;

public class SocketAdaptorWitupTest {

    public static void main(String[] args) throws IOException {
        try (SocketChannel sc = SocketChannel.open()) {
            Socket s = sc.socket();

            if (s.getChannel() != sc) {
                throw new RuntimeException("getChannel() must return the adapted channel");
            }

            try {
                s.connect(null);
                throw new RuntimeException("Expected IllegalArgumentException for null address");
            } catch (IllegalArgumentException e) {
                // expected
            }

            sc.configureBlocking(false);

            try {
                s.connect(null);
                throw new RuntimeException("Expected IllegalArgumentException for null address in non-blocking mode");
            } catch (IllegalArgumentException e) {
                // null check precedes the blocking-mode check
            }

            try {
                s.connect(new InetSocketAddress(InetAddress.getLoopbackAddress(), 1));
                throw new RuntimeException("Expected IllegalBlockingModeException in non-blocking mode");
            } catch (IllegalBlockingModeException e) {
                // expected
            }
        }
    }
}
