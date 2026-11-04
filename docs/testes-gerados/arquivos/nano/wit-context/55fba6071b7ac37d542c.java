/*
 * @test
 * @summary Test for setReceiveBufferSize method in SocketAdaptor
 * @run main SocketAdaptorWitupTest
 */

import java.net.SocketException;
import sun.nio.ch.SocketAdaptor;

public class SocketAdaptorWitupTest {

    public static void main(String[] args) {
        testSetReceiveBufferSizeValid();
        testSetReceiveBufferSizeZero();
        testSetReceiveBufferSizeNegative();
    }

    private static void testSetReceiveBufferSizeValid() {
        try {
            SocketAdaptor socketAdaptor = createSocketAdaptor();
            int validSize = 1024;
            socketAdaptor.setReceiveBufferSize(validSize);
            System.out.println("Valid size test passed.");
        } catch (Exception e) {
            System.err.println("Valid size test failed: " + e);
        }
    }

    private static void testSetReceiveBufferSizeZero() {
        try {
            SocketAdaptor socketAdaptor = createSocketAdaptor();
            int zeroSize = 0;
            socketAdaptor.setReceiveBufferSize(zeroSize);
            System.err.println("Zero size test failed: exception expected but not thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("Zero size test passed: " + e);
        } catch (Exception e) {
            System.err.println("Zero size test failed with unexpected exception: " + e);
        }
    }

    private static void testSetReceiveBufferSizeNegative() {
        try {
            SocketAdaptor socketAdaptor = createSocketAdaptor();
            int negativeSize = -1;
            socketAdaptor.setReceiveBufferSize(negativeSize);
            System.err.println("Negative size test failed: exception expected but not thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("Negative size test passed: " + e);
        } catch (Exception e) {
            System.err.println("Negative size test failed with unexpected exception: " + e);
        }
    }

    private static SocketAdaptor createSocketAdaptor() {
        // Since SocketAdaptor constructor is package-private, use the static factory method
        try {
            return SocketAdaptor.create(null); // Passing null for SocketChannelImpl as placeholder
        } catch (SocketException e) {
            throw new RuntimeException("Failed to create SocketAdaptor", e);
        }
    }
}
