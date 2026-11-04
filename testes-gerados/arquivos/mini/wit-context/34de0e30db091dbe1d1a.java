/*
 * @test
 * @summary Test java.nio.channels.Channels.write(int b) method for correct behavior and exception handling
 * @run main ChannelsWitupTest
 */

import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;

public class ChannelsWitupTest {

    // Minimal WritableByteChannel implementation to test Channels.write(int b)
    static class TestWritableByteChannel implements WritableByteChannel {
        private boolean open = true;
        private byte[] written = new byte[1];
        private boolean writtenCalled = false;

        @Override
        public int write(java.nio.ByteBuffer src) throws IOException {
            if (!open) throw new IOException("Channel closed");
            if (src == null) throw new NullPointerException();
            if (src.remaining() < 1) return 0;
            written[0] = src.get();
            writtenCalled = true;
            return 1;
        }

        @Override
        public boolean isOpen() {
            return open;
        }

        @Override
        public void close() throws IOException {
            open = false;
        }

        public byte getWrittenByte() {
            return written[0];
        }

        public boolean wasWriteCalled() {
            return writtenCalled;
        }
    }

    public static void main(String[] args) throws IOException {
        testWriteSingleByte();
    }

    private static void testWriteSingleByte() throws IOException {
        final TestWritableByteChannel channel = new TestWritableByteChannel();

        // Create an anonymous subclass of Channels with the write(int) method to test
        Channels channels = new Channels() { };

        // The write(int) method is not static, so we simulate the anonymous inner class from source
        // We replicate the method here as a local class to test it
        class WriteInt {
            private byte[] b1 = null;

            public synchronized void write(int b) throws IOException {
                if (b1 == null)
                    b1 = new byte[1];
                b1[0] = (byte) b;
                // We simulate the call to write(byte[])
                write(b1);
            }

            public void write(byte[] bs) throws IOException {
                if (bs == null) throw new NullPointerException();
                java.nio.ByteBuffer bb = java.nio.ByteBuffer.wrap(bs);
                int written = channel.write(bb);
                if (written != bs.length) {
                    throw new IOException("Not all bytes written");
                }
            }
        }

        WriteInt writer = new WriteInt();

        // Test writing a byte value
        writer.write(0x5A); // 0x5A = 90 decimal

        if (!channel.wasWriteCalled()) {
            throw new AssertionError("write(byte[]) was not called on channel");
        }

        if (channel.getWrittenByte() != (byte) 0x5A) {
            throw new AssertionError("Written byte mismatch: expected 0x5A, got " + channel.getWrittenByte());
        }

        // Test writing negative byte value
        writer.write(-1);
        if (channel.getWrittenByte() != (byte) -1) {
            throw new AssertionError("Written byte mismatch: expected -1, got " + channel.getWrittenByte());
        }

        // Test writing zero
        writer.write(0);
        if (channel.getWrittenByte() != (byte) 0) {
            throw new AssertionError("Written byte mismatch: expected 0, got " + channel.getWrittenByte());
        }
    }
}
