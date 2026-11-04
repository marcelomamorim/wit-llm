/*
 * @test
 * @summary Test sun.nio.ch.DatagramChannelImpl.write(ByteBuffer) for null and unsupported operation exceptions
 * @run main DatagramChannelImplWitupTest
 */

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.spi.SelectorProvider;
import sun.nio.ch.DatagramChannelImpl;

public class DatagramChannelImplWitupTest {
    public static void main(String[] args) throws Exception {
        SelectorProvider sp = SelectorProvider.provider();
        DatagramChannelImpl channel = new DatagramChannelImpl(sp);

        // Test 1: write with null ByteBuffer should throw NullPointerException
        try {
            channel.write(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test 2: write with non-null ByteBuffer should throw UnsupportedOperationException
        // According to WIT expath, writeLock.lock() triggers UnsupportedOperationException
        // We test with a direct ByteBuffer with zero capacity to minimize side effects
        ByteBuffer buf = ByteBuffer.allocate(0);
        try {
            channel.write(buf);
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
