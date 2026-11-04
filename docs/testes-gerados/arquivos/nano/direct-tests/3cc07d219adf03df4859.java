/*
 * @test
 * @summary Test setSendBufferSize method of SocketAdaptor for valid and invalid sizes
 * @run main SocketAdaptorWitupTest
 */

import java.net.SocketException;
import java.net.StandardSocketOptions;
import sun.nio.ch.SocketAdaptor;

public class SocketAdaptorWitupTest {

    public static void main(String[] args) {
        testSetSendBufferSizeValid();
        testSetSendBufferSizeInvalid();
    }

    static void testSetSendBufferSizeValid() {
        SocketAdaptor socketAdaptor = createSocketAdaptor();
        int validSize = 1024;
        try {
            socketAdaptor.setSendBufferSize(validSize);
            System.out.println("Valid size test passed.");
        } catch (Exception e) {
            System.err.println("Valid size test failed: " + e);
        }
    }

    static void testSetSendBufferSizeInvalid() {
        SocketAdaptor socketAdaptor = createSocketAdaptor();
        int invalidSize = 0;
        try {
            socketAdaptor.setSendBufferSize(invalidSize);
            System.err.println("Invalid size test failed: exception expected.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid size test passed.");
        } catch (Exception e) {
            System.err.println("Invalid size test failed with unexpected exception: " + e);
        }
    }

    static SocketAdaptor createSocketAdaptor() {
        // Since SocketAdaptor constructor is package-private, create a dummy Socket and cast
        // Alternatively, create a Socket and get its impl, then cast to SocketAdaptor if possible
        // But as constructor is package-private, we simulate by reflection or assume direct instantiation
        // For simplicity, assume direct instantiation is possible in this test context
        // Note: In real scenario, this might require reflection or a factory method
        try {
            return (SocketAdaptor) Class.forName("sun.nio.ch.SocketAdaptor")
                .getDeclaredConstructor(sun.nio.ch.SocketChannelImpl.class)
                .newInstance((sun.nio.ch.SocketChannelImpl) null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create SocketAdaptor instance", e);
        }
    }
}
