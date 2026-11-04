/*
 * @test
 * @summary Verifies SocketAdaptor.setSoTimeout through the public Socket API of a SocketChannel
 * @run main SocketAdaptorWitupTest
 */

import java.net.Socket;
import java.net.SocketException;
import java.nio.channels.SocketChannel;

public class SocketAdaptorWitupTest {

    public static void main(String[] args) throws Exception {
        try (SocketChannel ch = SocketChannel.open()) {
            Socket s = ch.socket();

            if (s.getSoTimeout() != 0) {
                throw new RuntimeException("Default SO_TIMEOUT expected 0, got " + s.getSoTimeout());
            }

            s.setSoTimeout(1500);
            if (s.getSoTimeout() != 1500) {
                throw new RuntimeException("Expected 1500, got " + s.getSoTimeout());
            }

            s.setSoTimeout(0);
            if (s.getSoTimeout() != 0) {
                throw new RuntimeException("Expected 0, got " + s.getSoTimeout());
            }

            s.setSoTimeout(Integer.MAX_VALUE);
            if (s.getSoTimeout() != Integer.MAX_VALUE) {
                throw new RuntimeException("Expected Integer.MAX_VALUE, got " + s.getSoTimeout());
            }

            boolean thrown = false;
            try {
                s.setSoTimeout(-1);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("IllegalArgumentException expected for negative timeout");
            }

            if (s.getSoTimeout() != Integer.MAX_VALUE) {
                throw new RuntimeException("Timeout changed after rejected negative value: " + s.getSoTimeout());
            }
        }
    }
}
