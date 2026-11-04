/*
 * @test
 * @summary Test sun.nio.ch.DatagramChannelImpl.read(ByteBuffer) method behavior
 * @run main DatagramChannelImplWitupTest
 */

import sun.nio.ch.DatagramChannelImpl;
import java.nio.ByteBuffer;
import java.nio.channels.spi.SelectorProvider;
import java.io.IOException;

public class DatagramChannelImplWitupTest {
    public static void main(String[] args) throws Exception {
        testReadWithNullBuffer();
        testReadWithEmptyBuffer();
        testReadWithNormalBuffer();
    }

    private static void testReadWithNullBuffer() {
        DatagramChannelImpl channel = new DatagramChannelImpl(SelectorProvider.provider());
        try {
            channel.read(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        } catch (IOException ioe) {
            throw new RuntimeException("Unexpected IOException", ioe);
        }
    }

    private static void testReadWithEmptyBuffer() throws IOException {
        DatagramChannelImpl channel = new DatagramChannelImpl(SelectorProvider.provider());
        ByteBuffer buf = ByteBuffer.allocate(0);
        int result = channel.read(buf);
        if (result != 0) {
            throw new RuntimeException("Expected read to return 0 for empty buffer, got " + result);
        }
    }

    private static void testReadWithNormalBuffer() throws IOException {
        DatagramChannelImpl channel = new DatagramChannelImpl(SelectorProvider.provider());
        ByteBuffer buf = ByteBuffer.allocate(10);
        int result = channel.read(buf);
        if (result < -1) {
            throw new RuntimeException("read returned invalid value " + result);
        }
    }
}
