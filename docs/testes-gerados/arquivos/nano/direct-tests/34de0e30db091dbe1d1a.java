/*
 * @test
 * @summary Test the write(int b) method of java.nio.channels.Channels for correct byte writing behavior.
 * @run main ChannelsWitupTest
 */

import java.io.IOException;
import java.nio.channels.Channels;

public class ChannelsWitupTest {

    public static void main(String[] args) {
        testWriteSingleByte();
        testWriteMultipleBytes();
    }

    private static void testWriteSingleByte() {
        try {
            DummyChannel channel = new DummyChannel();
            Channels.write(65, channel); // ASCII 'A'
            if (channel.getWrittenBytes().length != 1 || channel.getWrittenBytes()[0] != 65) {
                throw new AssertionError("Single byte write failed: expected 65, got " + channel.getWrittenBytes()[0]);
            }
        } catch (IOException e) {
            throw new RuntimeException("IOException during testWriteSingleByte", e);
        }
    }

    private static void testWriteMultipleBytes() {
        try {
            DummyChannel channel = new DummyChannel();
            byte[] data = {66, 67, 68}; // 'B', 'C', 'D'
            channel.write(data);
            if (channel.getWrittenBytes().length != 3 || channel.getWrittenBytes()[0] != 66 || channel.getWrittenBytes()[1] != 67 || channel.getWrittenBytes()[2] != 68) {
                throw new AssertionError("Multiple bytes write failed: expected {66,67,68}, got " + java.util.Arrays.toString(channel.getWrittenBytes()));
            }
        } catch (IOException e) {
            throw new RuntimeException("IOException during testWriteMultipleBytes", e);
        }
    }

    private static class DummyChannel extends java.nio.channels.WritableByteChannel {
        private byte[] buffer = new byte[0];

        @Override
        public int write(ByteBuffer src) throws IOException {
            int remaining = src.remaining();
            byte[] data = new byte[remaining];
            src.get(data);
            byte[] newBuffer = new byte[buffer.length + remaining];
            System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
            System.arraycopy(data, 0, newBuffer, buffer.length, remaining);
            buffer = newBuffer;
            return remaining;
        }

        @Override
        public boolean isOpen() {
            return true;
        }

        @Override
        public void close() throws IOException {
            // no-op
        }

        public byte[] getWrittenBytes() {
            return buffer;
        }
    }
}
