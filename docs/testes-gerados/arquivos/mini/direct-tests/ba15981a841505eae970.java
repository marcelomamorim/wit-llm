/*
 * @test
 * @summary Test sun.nio.ch.DatagramSocketAdaptor.setReceiveBufferSize(int) method
 * @run main DatagramSocketAdaptorWitupTest
 */

import sun.nio.ch.DatagramSocketAdaptor;
import sun.nio.ch.DatagramChannelImpl;
import java.net.SocketException;
import java.net.StandardSocketOptions;
import java.net.DatagramSocket;

public class DatagramSocketAdaptorWitupTest {

    public static void main(String[] args) throws Exception {
        testSetReceiveBufferSizeValid();
        testSetReceiveBufferSizeZero();
        testSetReceiveBufferSizeNegative();
    }

    private static DatagramSocketAdaptor createAdaptor() throws Exception {
        // Use DatagramChannelImpl.create() to get a DatagramChannelImpl instance
        DatagramChannelImpl dc = DatagramChannelImpl.open();
        return (DatagramSocketAdaptor) DatagramSocketAdaptor.create(dc);
    }

    private static void testSetReceiveBufferSizeValid() throws Exception {
        DatagramSocketAdaptor adaptor = createAdaptor();
        // Set a valid buffer size
        int size = 1024;
        adaptor.setReceiveBufferSize(size);
        // Verify by getting the option from the underlying channel
        int actual = adaptor.getChannel().getOption(StandardSocketOptions.SO_RCVBUF);
        if (actual < size) {
            throw new RuntimeException("setReceiveBufferSize did not set the expected size: expected at least " + size + ", got " + actual);
        }
        adaptor.close();
    }

    private static void testSetReceiveBufferSizeZero() throws Exception {
        DatagramSocketAdaptor adaptor = createAdaptor();
        try {
            adaptor.setReceiveBufferSize(0);
            throw new RuntimeException("Expected IllegalArgumentException for size=0");
        } catch (IllegalArgumentException e) {
            // expected
        } finally {
            adaptor.close();
        }
    }

    private static void testSetReceiveBufferSizeNegative() throws Exception {
        DatagramSocketAdaptor adaptor = createAdaptor();
        try {
            adaptor.setReceiveBufferSize(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative size");
        } catch (IllegalArgumentException e) {
            // expected
        } finally {
            adaptor.close();
        }
    }
}
