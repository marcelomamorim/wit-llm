/*
 * @test
 * @summary Test sun.nio.ch.DatagramChannelImpl.write(ByteBuffer) method
 * @run main DatagramChannelImplWitupTest
 */

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.spi.SelectorProvider;
import sun.nio.ch.DatagramChannelImpl;

public class DatagramChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testWriteWithNullBuffer();
        testWriteWithEmptyBuffer();
        testWriteWithNonEmptyBuffer();
    }

    private static void testWriteWithNullBuffer() {
        DatagramChannelImpl channel = new DatagramChannelImpl(SelectorProvider.provider());
        try {
            channel.write(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }

    private static void testWriteWithEmptyBuffer() throws IOException {
        DatagramChannelImpl channel = new DatagramChannelImpl(SelectorProvider.provider());
        ByteBuffer empty = ByteBuffer.allocate(0);
        int written = channel.write(empty);
        if (written != 0) {
            throw new RuntimeException("Expected 0 bytes written for empty buffer, got " + written);
        }
    }

    private static void testWriteWithNonEmptyBuffer() throws IOException {
        DatagramChannelImpl channel = new DatagramChannelImpl(SelectorProvider.provider());
        byte[] data = new byte[] {1, 2, 3, 4};
        ByteBuffer buffer = ByteBuffer.wrap(data);
        int written = channel.write(buffer);
        if (written < 0) {
            throw new RuntimeException("write returned negative value " + written);
        }
        if (buffer.position() != written) {
            throw new RuntimeException("Buffer position " + buffer.position() + " does not match bytes written " + written);
        }
    }
}
