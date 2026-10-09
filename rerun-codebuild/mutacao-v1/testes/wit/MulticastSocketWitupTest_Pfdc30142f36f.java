/* @test    @summary Test for MulticastSocket.setTimeToLive(int) method handling of invalid TTL values    @run main MulticastSocketWitupTest_Pfdc30142f36f */

import java.net.MulticastSocket;
import java.net.SocketException;
import java.io.IOException;

public class MulticastSocketWitupTest_Pfdc30142f36f {

    public static void main(String[] args) throws IOException {
        testSetTimeToLiveInvalidNegative();
        testSetTimeToLiveInvalidTooLarge();
        testSetTimeToLiveValidValues();
    }

    private static void testSetTimeToLiveInvalidNegative() {
        MulticastSocket socket = null;
        try {
            socket = new MulticastSocket();
            socket.setTimeToLive(-1);
            throw new AssertionError("Expected IllegalArgumentException for ttl = -1");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }

    private static void testSetTimeToLiveInvalidTooLarge() {
        MulticastSocket socket = null;
        try {
            socket = new MulticastSocket();
            socket.setTimeToLive(256);
            throw new AssertionError("Expected IllegalArgumentException for ttl = 256");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }

    private static void testSetTimeToLiveValidValues() {
        MulticastSocket socket = null;
        try {
            socket = new MulticastSocket();
            for (int ttl = 0; ttl <= 255; ttl++) {
                socket.setTimeToLive(ttl);
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException during valid TTL tests: " + e);
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}
