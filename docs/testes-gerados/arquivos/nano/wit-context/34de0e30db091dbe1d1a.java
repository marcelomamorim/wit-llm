/*
 * @test
 * @summary Tests for java.nio.channels.Channels class methods.
 * @run main ChannelsWitupTest
 */

import java.io.IOException;
import java.nio.channels.WritableByteChannel;
import java.nio.channels.Channels;

public class ChannelsWitupTest {

    public static void main(String[] args) throws Exception {
        testWriteMethodBehavior();
    }

    public static void testWriteMethodBehavior() throws IOException {
        DummyChannel channel = new DummyChannel();
        // Test normal write
        Channels.writeFully(channel, ByteBuffer.wrap(new byte[]{1, 2, 3}));
        assert channel.writtenBytes == 3 : "Should write all bytes";
        // Test write with zero bytes remaining
        channel.reset();
        channel.setRemainingBytes(0);
        Channels.writeFully(channel, ByteBuffer.wrap(new byte[]{4, 5}));
        assert channel.writtenBytes == 0 : "No bytes should be written when buffer has no remaining";
        // Test non-blocking channel throws if non-blocking
        channel.setBlocking(false);
        try {
            Channels.writeFully(channel, ByteBuffer.wrap(new byte[]{6}));
            assert false : "Should throw IllegalBlockingModeException";
        } catch (IllegalBlockingModeException e) {
            // expected
        }
    }

    static class DummyChannel implements WritableByteChannel {
        boolean blocking = true;
        int remainingBytes = 0;
        int writtenBytes = 0;

        @Override
        public int write(ByteBuffer src) {
            int remaining = src.remaining();
            int toWrite = Math.min(remaining, remainingBytes);
            src.position(src.position() + toWrite);
            writtenBytes += toWrite;
            return toWrite;
        }

        @Override
        public boolean isOpen() {
            return true;
        }

        @Override
        public void close() throws IOException {
            // no-op
        }

        public void setRemainingBytes(int bytes) {
            this.remainingBytes = bytes;
        }

        public void setBlocking(boolean blocking) {
            this.blocking = blocking;
        }

        public Object blockingLock() {
            return this;
        }

        public boolean isBlocking() {
            return blocking;
        }
    }
}
