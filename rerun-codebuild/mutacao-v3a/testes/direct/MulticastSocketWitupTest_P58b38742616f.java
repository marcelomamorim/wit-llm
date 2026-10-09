/* @test    @summary Test setTimeToLive with valid and invalid values    @run main MulticastSocketWitupTest_P58b38742616f */

import java.io.IOException;
import java.net.*;

public class MulticastSocketWitupTest_P58b38742616f {

    public static void main(String[] args) throws Exception {
        testSetTimeToLiveValid();
        testSetTimeToLiveInvalidNegative();
        testSetTimeToLiveInvalidExceeds();
        testSetTimeToLiveOnClosedSocket();
    }

    private static void testSetTimeToLiveValid() throws IOException {
        MulticastSocket socket = new MulticastSocket();
        try {
            socket.setTimeToLive(128);
            System.out.println("setTimeToLive with 128 succeeded");
        } finally {
            socket.close();
        }
    }

    private static void testSetTimeToLiveInvalidNegative() {
        MulticastSocket socket = null;
        try {
            socket = new MulticastSocket();
            socket.setTimeToLive(-1);
            System.out.println("Error: Negative ttl did not throw exception");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected IllegalArgumentException for negative ttl");
        } catch (IOException e) {
            System.out.println("Error: Unexpected IOException: " + e);
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }

    private static void testSetTimeToLiveInvalidExceeds() {
        MulticastSocket socket = null;
        try {
            socket = new MulticastSocket();
            socket.setTimeToLive(300);
            System.out.println("Error: ttl > 255 did not throw exception");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected IllegalArgumentException for ttl > 255");
        } catch (IOException e) {
            System.out.println("Error: Unexpected IOException: " + e);
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }

    private static void testSetTimeToLiveOnClosedSocket() {
        MulticastSocket socket = null;
        try {
            socket = new MulticastSocket();
            socket.close();
            socket.setTimeToLive(10);
            System.out.println("Error: setTimeToLive on closed socket did not throw exception");
        } catch (SocketException e) {
            System.out.println("Caught expected SocketException when calling setTimeToLive on closed socket");
        } catch (IOException e) {
            System.out.println("Error: Unexpected IOException: " + e);
        }
    }
}
