/*
 * @test
 * @summary Test the write(ByteBuffer) method in Channels class for normal and exceptional behavior.
 * @run main ChannelsWitupTest
 */

import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.WritableByteChannel;
import java.io.IOException;

public class ChannelsWitupTest {

    static class DummyChannel implements WritableByteChannel {
        boolean open = true;
        boolean closed = false;
        int writtenBytes = 0;
        boolean writeCalled = false;
        ByteBuffer lastBuffer = null;

        @Override
        public int write(ByteBuffer src) throws IOException {
            writeCalled = true;
            if (!open) {
                throw new ClosedChannelException();
            }
            lastBuffer = src;
            int remaining = src.remaining();
            writtenBytes += remaining;
            src.position(src.limit());
            return remaining;
        }

        @Override
        public boolean isOpen() {
            return open;
        }

        @Override
        public void close() throws IOException {
            open = false;
        }
    }

    public static void main(String[] args) throws Exception {
        testNormalWrite();
        testWriteWhenClosedChannel();
    }

    static void testNormalWrite() throws Exception {
        DummyChannel channel = new DummyChannel();
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{1,2,3,4,5,6,7,8,9,10});
        buffer.flip();
        int result = Channels.write(channel, buffer);
        assert result == 10 : "Expected 10 bytes written, got " + result;
        assert channel.writeCalled : "write() method was not called";
        assert channel.lastBuffer == buffer : "Buffer passed to write() does not match";
        assert buffer.position() == buffer.limit() : "Buffer position not at limit after write";
    }

    static void testWriteWhenClosedChannel() throws Exception {
        DummyChannel channel = new DummyChannel();
        channel.open = false;
        ByteBuffer buffer = ByteBuffer.allocate(5);
        buffer.put(new byte[]{1,2,3,4,5});
        buffer.flip();
        try {
            Channels.write(channel, buffer);
            assert false : "Expected ClosedChannelException";
        } catch (ClosedChannelException e) {
            // expected
        }
    }

    // Helper method to invoke the method under test
    static int write(WritableByteChannel ch, ByteBuffer src) throws IOException {
        // Since the method is static in Channels, call directly
        return java.nio.channels.Channels.write(src);
    }
}
