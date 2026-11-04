/*
 * @test
 * @summary Tests for java.nio.channels.Channels
 * @run main ChannelsWitupTest
 */

import java.io.InputStream;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.Channels;
import java.nio.channels.Channel;
import java.io.IOException;

public class ChannelsWitupTest {

    public static void main(String[] args) throws Exception {
        testNewInputStreamWithNullChannel();
        testNewInputStreamWithValidChannel();
    }

    private static void testNewInputStreamWithNullChannel() {
        try {
            Channels.newInputStream(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testNewInputStreamWithValidChannel() throws IOException {
        ReadableByteChannel channel = new DummyReadableByteChannel();
        InputStream is = Channels.newInputStream(channel);
        if (!(is instanceof sun.nio.ch.ChannelInputStream)) {
            throw new AssertionError("Returned InputStream is not of expected type");
        }
        is.close();
    }

    private static class DummyReadableByteChannel implements ReadableByteChannel {
        private boolean open = true;
        @Override
        public int read(java.nio.ByteBuffer dst) throws IOException {
            return -1; // End of stream
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
}
