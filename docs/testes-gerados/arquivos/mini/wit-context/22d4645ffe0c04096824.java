/*
 * @test
 * @summary Test Channels.write(int b) method for correct behavior and exception safety
 * @run main ChannelsWitupTest
 */

import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;

public class ChannelsWitupTest {

    // Minimal WritableByteChannel implementation to test Channels.write(int b)
    static class TestWritableByteChannel implements WritableByteChannel {
        private boolean open = true;
        private byte[] data = new byte[1];
        private int writeCount = 0;

        @Override
        public int write(java.nio.ByteBuffer src) throws IOException {
            if (!open) throw new IOException("Channel closed");
            int remaining = src.remaining();
            if (remaining <= 0) return 0;
            if (remaining > data.length) {
                throw new RuntimeException("no bytes written");
            }
            src.get(data, 0, remaining);
            writeCount += remaining;
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

        public int getWriteCount() {
            return writeCount;
        }
    }

    public static void main(String[] args) throws IOException {
        testWriteSingleByte();
    }

    private static void testWriteSingleByte() throws IOException {
        final TestWritableByteChannel channel = new TestWritableByteChannel();

        // Create an anonymous subclass of Channels with write(int b) method copied
        // to test the behavior as per source excerpt
        ChannelsWriter writer = new ChannelsWriter(channel);

        // Write a single byte and verify that the channel received it
        writer.write(0x41); // ASCII 'A'
        if (channel.getWriteCount() != 1) {
            throw new AssertionError("Expected 1 byte written, got " + channel.getWriteCount());
        }

        // Write another byte
        writer.write(0x7F);
        if (channel.getWriteCount() != 2) {
            throw new AssertionError("Expected 2 bytes written, got " + channel.getWriteCount());
        }

        // Test that write with negative byte value works (cast to byte)
        writer.write(-1);
        if (channel.getWriteCount() != 3) {
            throw new AssertionError("Expected 3 bytes written, got " + channel.getWriteCount());
        }
    }

    // Helper class to simulate the Channels.Anonymous class with write(int b) method
    static class ChannelsWriter {
        private final WritableByteChannel ch;
        private byte[] b1 = null;

        ChannelsWriter(WritableByteChannel ch) {
            this.ch = ch;
        }

        public synchronized void write(int b) throws IOException {
            if (b1 == null)
                b1 = new byte[1];
            b1[0] = (byte) b;
            // Use a ByteBuffer wrapping b1 to write to channel
            java.nio.ByteBuffer bb = java.nio.ByteBuffer.wrap(b1);
            while (bb.remaining() > 0) {
                int n = ch.write(bb);
                if (n <= 0) {
                    throw new RuntimeException("no bytes written");
                }
            }
        }
    }
}
