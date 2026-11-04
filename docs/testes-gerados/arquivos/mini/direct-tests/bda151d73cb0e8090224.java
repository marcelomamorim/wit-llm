/*
 * @test
 * @summary Test Channels.read() method behavior
 * @run main ChannelsWitupTest
 */

import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class ChannelsWitupTest {

    static class TestReadableByteChannel implements ReadableByteChannel {
        private final byte[] data;
        private int pos = 0;
        private boolean open = true;

        TestReadableByteChannel(byte[] data) {
            this.data = data;
        }

        @Override
        public int read(java.nio.ByteBuffer dst) throws IOException {
            if (!open) throw new IOException("Channel closed");
            if (pos >= data.length) return -1;
            if (!dst.hasRemaining()) return 0;
            dst.put(data[pos++]);
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
    }

    public static void main(String[] args) throws IOException {
        // We test the Channels.read() method indirectly by creating a Channels.ChannelInputStream
        // and calling its read() method, which matches the tested method signature.

        // Create a channel with one byte
        final byte[] oneByte = {(byte)0xAB};
        TestReadableByteChannel channel = new TestReadableByteChannel(oneByte);
        java.io.InputStream in = Channels.newInputStream(channel);

        // First read() should return 0xAB & 0xFF = 171
        int first = in.read();
        if (first != 0xAB) {
            throw new AssertionError("Expected 0xAB (171), got " + first);
        }

        // Second read() should return -1 (end of stream)
        int second = in.read();
        if (second != -1) {
            throw new AssertionError("Expected -1 at end of stream, got " + second);
        }

        // Close and verify no exception on close
        in.close();

        // Test that read() after close returns -1
        int afterClose = in.read();
        if (afterClose != -1) {
            throw new AssertionError("Expected -1 after close, got " + afterClose);
        }

        System.out.println("PASS");
    }
}
