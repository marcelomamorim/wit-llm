/*
 * @test
 * @summary Test sun.nio.ch.DatagramChannelImpl.read(ByteBuffer) for NPE and UnsupportedOperationException
 * @run main DatagramChannelImplWitupTest
 */

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.spi.SelectorProvider;
import sun.nio.ch.DatagramChannelImpl;

public class DatagramChannelImplWitupTest {
    public static void main(String[] args) throws Exception {
        testReadNullBuffer();
        testReadUnsupportedOperationException();
    }

    private static void testReadNullBuffer() {
        DatagramChannelImpl channel = new DatagramChannelImpl(SelectorProvider.provider());
        try {
            channel.read(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (IOException ioe) {
            throw new RuntimeException("Unexpected IOException", ioe);
        }
    }

    private static void testReadUnsupportedOperationException() {
        DatagramChannelImpl channel = new DatagramChannelImpl(SelectorProvider.provider());
        ByteBuffer buffer = ByteBuffer.allocate(1);
        try {
            channel.read(buffer);
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        } catch (IOException ioe) {
            throw new RuntimeException("Unexpected IOException", ioe);
        }
    }
}
