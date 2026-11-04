/* @test    @summary Test setTrafficClass method for valid and invalid inputs    @run main SocketWitupTest */

import java.net.Socket;
import java.net.SocketException;

public class SocketWitupTest {

    public static void main(String[] args) {
        testSetTrafficClassValid();
        testSetTrafficClassInvalidNegative();
        testSetTrafficClassInvalidTooHigh();
        testSetTrafficClassWhenClosed();
    }

    static void testSetTrafficClassValid() {
        try {
            Socket socket = new Socket();
            socket.setTrafficClass(0);
            socket.setTrafficClass(255);
            socket.close();
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in valid traffic class test", e);
        }
    }

    static void testSetTrafficClassInvalidNegative() {
        try {
            Socket socket = new Socket();
            socket.setTrafficClass(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative tc");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for negative tc", e);
        }
    }

    static void testSetTrafficClassInvalidTooHigh() {
        try {
            Socket socket = new Socket();
            socket.setTrafficClass(256);
            throw new AssertionError("Expected IllegalArgumentException for tc > 255");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for tc > 255", e);
        }
    }

    static void testSetTrafficClassWhenClosed() {
        try {
            Socket socket = new Socket();
            socket.close();
            socket.setTrafficClass(10);
            // Depending on implementation, may throw SocketException or not
        } catch (SocketException e) {
            // expected if socket is closed
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception when setting traffic class on closed socket", e);
        }
    }
}
