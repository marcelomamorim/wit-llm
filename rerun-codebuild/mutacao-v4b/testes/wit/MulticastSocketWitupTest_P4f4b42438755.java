/* @test
   @summary Tests for java.net.MulticastSocket.setTimeToLive(int ttl).
   @run main MulticastSocketWitupTest_P4f4b42438755
 */
import java.io.IOException;
import java.net.SocketException;
public class MulticastSocketWitupTest_P4f4b42438755 {
    public static void main(String[] args) throws Exception {
        testSetTimeToLiveValid();
        testSetTimeToLiveNegative();
        testSetTimeToLiveExceedsMax();
        testSetTimeToLiveSocketClosed();
    }

    static void testSetTimeToLiveValid() throws Exception {
        java.net.MulticastSocket socket = new java.net.MulticastSocket();
        try {
            socket.setTimeToLive(0);
            socket.setTimeToLive(128);
            socket.setTimeToLive(255);
        } finally {
            socket.close();
        }
    }

    static void testSetTimeToLiveNegative() throws Exception {
        java.net.MulticastSocket socket = new java.net.MulticastSocket();
        try {
            boolean exceptionThrown = false;
            try {
                socket.setTimeToLive(-1);
            } catch (IllegalArgumentException e) {
                exceptionThrown = true;
            }
            if (!exceptionThrown) {
                throw new AssertionError("Expected IllegalArgumentException for ttl < 0");
            }
        } finally {
            socket.close();
        }
    }

    static void testSetTimeToLiveExceedsMax() throws Exception {
        java.net.MulticastSocket socket = new java.net.MulticastSocket();
        try {
            boolean exceptionThrown = false;
            try {
                socket.setTimeToLive(256);
            } catch (IllegalArgumentException e) {
                exceptionThrown = true;
            }
            if (!exceptionThrown) {
                throw new AssertionError("Expected IllegalArgumentException for ttl > 255");
            }
        } finally {
            socket.close();
        }
    }

    static void testSetTimeToLiveSocketClosed() throws Exception {
        java.net.MulticastSocket socket = new java.net.MulticastSocket();
        socket.close();
        boolean exceptionThrown = false;
        try {
            socket.setTimeToLive(10);
        } catch (SocketException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected SocketException when socket is closed");
        }
    }
}
