/*
 * @test
 * @summary Test setSoTimeout method for SocketAdaptor.
 * @run main SocketAdaptorWitupTest
 */

import java.net.SocketException;

public class SocketAdaptorWitupTest {

    public static void main(String[] args) {
        testSetSoTimeoutPositive();
        testSetSoTimeoutZero();
        testSetSoTimeoutNegative();
    }

    static void testSetSoTimeoutPositive() {
        try {
            sun.nio.ch.SocketAdaptor adaptor = createSocketAdaptor();
            adaptor.setSoTimeout(1000);
            assert adaptor.timeout == 1000 : "Timeout should be set to 1000";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in positive timeout test", e);
        }
    }

    static void testSetSoTimeoutZero() {
        try {
            sun.nio.ch.SocketAdaptor adaptor = createSocketAdaptor();
            adaptor.setSoTimeout(0);
            assert adaptor.timeout == 0 : "Timeout should be set to 0";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in zero timeout test", e);
        }
    }

    static void testSetSoTimeoutNegative() {
        try {
            sun.nio.ch.SocketAdaptor adaptor = createSocketAdaptor();
            adaptor.setSoTimeout(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative timeout");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in negative timeout test", e);
        }
    }

    static sun.nio.ch.SocketAdaptor createSocketAdaptor() {
        try {
            // Create a dummy SocketChannel to pass to the constructor
            java.nio.channels.SocketChannel sc = java.nio.channels.SocketChannel.open();
            return (sun.nio.ch.SocketAdaptor) sun.nio.ch.SocketAdaptor.create(sc);
        } catch (Exception e) {
            throw new AssertionError("Failed to create SocketAdaptor", e);
        }
    }
}
