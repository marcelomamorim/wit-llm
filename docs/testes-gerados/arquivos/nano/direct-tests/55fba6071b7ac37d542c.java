/*
 * @test
 * @summary Test setReceiveBufferSize method of SocketAdaptor for valid and invalid sizes
 * @run main SocketAdaptorWitupTest
 */

import java.net.SocketException;
import java.net.StandardSocketOptions;

public class SocketAdaptorWitupTest {

    public static void main(String[] args) {
        testSetReceiveBufferSizeValid();
        testSetReceiveBufferSizeInvalid();
    }

    static void testSetReceiveBufferSizeValid() {
        sun.nio.ch.SocketAdaptor adaptor = createSocketAdaptor();
        int validSize = 1024;
        try {
            adaptor.setReceiveBufferSize(validSize);
            System.out.println("Valid size set successfully.");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid size", e);
        }
    }

    static void testSetReceiveBufferSizeInvalid() {
        sun.nio.ch.SocketAdaptor adaptor = createSocketAdaptor();
        int invalidSize = -1;
        try {
            adaptor.setReceiveBufferSize(invalidSize);
            throw new AssertionError("Expected IllegalArgumentException for invalid size");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected IllegalArgumentException.");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }

    static sun.nio.ch.SocketAdaptor createSocketAdaptor() {
        // Create a dummy SocketChannel to pass to SocketAdaptor constructor
        // Since the constructor is package-private, we simulate the creation via reflection
        try {
            java.nio.channels.SocketChannel sc = java.nio.channels.SocketChannel.open();
            return (sun.nio.ch.SocketAdaptor) java.lang.reflect.Proxy.newProxyInstance(
                sun.nio.ch.SocketAdaptor.class.getClassLoader(),
                new Class<?>[] { sun.nio.ch.SocketAdaptor.class },
                (proxy, method, args) -> {
                    throw new UnsupportedOperationException("Mock proxy");
                }
            );
        } catch (Exception e) {
            // Fallback: create a real SocketAdaptor instance via reflection
            try {
                java.lang.reflect.Constructor<sun.nio.ch.SocketAdaptor> ctor = sun.nio.ch.SocketAdaptor.class.getDeclaredConstructor(sun.nio.ch.SocketChannelImpl.class);
                ctor.setAccessible(true);
                java.nio.channels.SocketChannel sc = java.nio.channels.SocketChannel.open();
                return ctor.newInstance(sc); 
            } catch (Exception ex) {
                throw new RuntimeException("Failed to create SocketAdaptor instance", ex);
            }
        }
    }
}
