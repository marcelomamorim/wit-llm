/* @test    @summary Test setTimeToLive with valid and invalid values    @run main MulticastSocketWitupTest_Pfdc30142f36f */

import java.io.IOException;
import java.net.SocketException;
import java.net.MulticastSocket;

public class MulticastSocketWitupTest_Pfdc30142f36f {

    public static void main(String[] args) throws Exception {
        testSetTimeToLiveValid();
        testSetTimeToLiveNegative();
        testSetTimeToLiveExceedsMax();
        testSetTimeToLiveWhenClosed();
    }

    static void testSetTimeToLiveValid() throws IOException {
        MulticastSocket socket = new MulticastSocket();
        try {
            socket.setTimeToLive(128);
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException for valid ttl", e);
        }
        socket.close();
    }

    static void testSetTimeToLiveNegative() throws IOException {
        MulticastSocket socket = new MulticastSocket();
        boolean exceptionThrown = false;
        try {
            socket.setTimeToLive(-1);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for negative ttl");
        }
        socket.close();
    }

    static void testSetTimeToLiveExceedsMax() throws IOException {
        MulticastSocket socket = new MulticastSocket();
        boolean exceptionThrown = false;
        try {
            socket.setTimeToLive(256);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for ttl > 255");
        }
        socket.close();
    }

    static void testSetTimeToLiveWhenClosed() throws IOException {
        MulticastSocket socket = new MulticastSocket();
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
