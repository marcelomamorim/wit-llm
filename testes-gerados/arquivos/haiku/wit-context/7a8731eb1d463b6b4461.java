/*
 * @test
 * @summary Verifies DatagramSocket.setTrafficClass range validation and closed-socket behavior
 * @run main DatagramSocketWitupTest
 */

import java.net.DatagramSocket;
import java.net.SocketException;

public class DatagramSocketWitupTest {

    public static void main(String[] args) throws Exception {
        try (DatagramSocket s = new DatagramSocket()) {
            expectIllegalArgument(s, -1);
            expectIllegalArgument(s, 256);
            expectIllegalArgument(s, Integer.MIN_VALUE);
            expectIllegalArgument(s, Integer.MAX_VALUE);
            s.setTrafficClass(0);
            s.setTrafficClass(255);
        }

        DatagramSocket closed = new DatagramSocket();
        closed.close();
        expectIllegalArgument(closed, -1);
        expectIllegalArgument(closed, 256);
        expectSocketException(closed, 0);
        expectSocketException(closed, 255);
    }

    static void expectIllegalArgument(DatagramSocket s, int tc) throws Exception {
        try {
            s.setTrafficClass(tc);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for tc=" + tc);
    }

    static void expectSocketException(DatagramSocket s, int tc) throws Exception {
        try {
            s.setTrafficClass(tc);
        } catch (SocketException e) {
            return;
        }
        throw new RuntimeException("Expected SocketException for closed socket with tc=" + tc);
    }
}
