/* @test    @summary Test setTrafficClass with valid and invalid values    @run main SocketWitupTest */

import java.net.Socket;
import java.net.SocketException;

public class SocketWitupTest {

    public static void main(String[] args) {
        testSetTrafficClassValid();
        testSetTrafficClassInvalidNegative();
        testSetTrafficClassInvalidAboveRange();
        testSetTrafficClassWhenSocketClosed();
    }

    static void testSetTrafficClassValid() {
        Socket socket = new Socket();
        try {
            socket.setTrafficClass(0);
            socket.setTrafficClass(255);
        } catch (SocketException e) {
            throw new RuntimeException("Unexpected SocketException for valid values", e);
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    static void testSetTrafficClassInvalidNegative() {
        Socket socket = new Socket();
        boolean thrown = false;
        try {
            socket.setTrafficClass(-1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        } catch (SocketException e) {
            throw new RuntimeException("Expected IllegalArgumentException, but got SocketException", e);
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
                // ignore
            }
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException for tc < 0");
        }
    }

    static void testSetTrafficClassInvalidAboveRange() {
        Socket socket = new Socket();
        boolean thrown = false;
        try {
            socket.setTrafficClass(256);
        } catch (IllegalArgumentException e) {
            thrown = true;
        } catch (SocketException e) {
            throw new RuntimeException("Expected IllegalArgumentException, but got SocketException", e);
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
                // ignore
            }
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException for tc > 255");
        }
    }

    static void testSetTrafficClassWhenSocketClosed() {
        Socket socket = new Socket();
        try {
            socket.close();
            boolean thrown = false;
            try {
                socket.setTrafficClass(10);
            } catch (SocketException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new AssertionError("Expected SocketException when socket is closed");
            }
        } finally {
            // no action needed
        }
    }
}
