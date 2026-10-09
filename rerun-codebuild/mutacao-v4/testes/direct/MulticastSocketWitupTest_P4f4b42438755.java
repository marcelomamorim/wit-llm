/* @test
   @summary Tests the setTimeToLive method of MulticastSocket for valid and invalid inputs.
   @run main MulticastSocketWitupTest_P4f4b42438755
 */
import java.net.MulticastSocket;
import java.net.SocketException;
import java.io.IOException;

public class MulticastSocketWitupTest_P4f4b42438755 {
    public static void main(String[] args) throws Exception {
        testSetTimeToLiveValid();
        testSetTimeToLiveNegative();
        testSetTimeToLiveTooLarge();
        testSetTimeToLiveWhenClosed();
    }

    private static void testSetTimeToLiveValid() throws IOException {
        MulticastSocket socket = new MulticastSocket();
        try {
            socket.setTimeToLive(128);
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException for valid ttl", e);
        } finally {
            socket.close();
        }
    }

    private static void testSetTimeToLiveNegative() throws IOException {
        MulticastSocket socket = new MulticastSocket();
        try {
            socket.setTimeToLive(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative ttl");
        } catch (IllegalArgumentException e) {
            // expected
        } finally {
            socket.close();
        }
    }

    private static void testSetTimeToLiveTooLarge() throws IOException {
        MulticastSocket socket = new MulticastSocket();
        try {
            socket.setTimeToLive(256);
            throw new AssertionError("Expected IllegalArgumentException for ttl > 255");
        } catch (IllegalArgumentException e) {
            // expected
        } finally {
            socket.close();
        }
    }

    private static void testSetTimeToLiveWhenClosed() throws IOException {
        MulticastSocket socket = new MulticastSocket();
        socket.close();
        try {
            socket.setTimeToLive(10);
            throw new AssertionError("Expected SocketException when socket is closed");
        } catch (SocketException e) {
            // expected
        }
    }
}
