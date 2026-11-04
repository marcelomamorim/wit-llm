/*
 * @test
 * @summary DatagramSocket.setTrafficClass validates range and closed state
 * @run main DatagramSocketWitupTest
 */

import java.net.DatagramSocket;
import java.net.SocketException;

public class DatagramSocketWitupTest {

    public static void main(String[] args) throws Exception {
        testNegativeTrafficClassRejected();
        testTooLargeTrafficClassRejected();
        testClosedSocketRejectsTrafficClass();
        testBoundaryValuesAccepted();
    }

    static void testNegativeTrafficClassRejected() throws Exception {
        expectIllegalArgument(-1);
    }

    static void testTooLargeTrafficClassRejected() throws Exception {
        expectIllegalArgument(256);
    }

    static void testClosedSocketRejectsTrafficClass() throws Exception {
        DatagramSocket s = new DatagramSocket();
        s.close();
        if (!s.isClosed()) {
            throw new RuntimeException("socket should be closed");
        }
        try {
            s.setTrafficClass(0);
        } catch (SocketException expected) {
            return;
        }
        throw new RuntimeException("SocketException expected on closed socket");
    }

    static void testBoundaryValuesAccepted() throws Exception {
        try (DatagramSocket s = new DatagramSocket()) {
            s.setTrafficClass(0);
            s.setTrafficClass(255);
        }
    }

    static void expectIllegalArgument(int tc) throws Exception {
        try (DatagramSocket s = new DatagramSocket()) {
            s.setTrafficClass(tc);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException expected for tc=" + tc);
    }
}
