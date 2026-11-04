/*
 * @test
 * @summary Tests for java.nio.channels.Channels.newWriter method.
 * @run main ChannelsWitupTest
 */

import java.nio.channels.WritableByteChannel;
import java.nio.channels.Channels;
import java.nio.charset.Charset;
import java.io.Writer;
import java.io.IOException;

public class ChannelsWitupTest {

    public static void main(String[] args) throws Exception {
        testNullCharsetThrows();
        testValidChannelAndCharset();
    }

    private static void testNullCharsetThrows() {
        WritableByteChannel ch = new DummyWritableByteChannel();
        try {
            Channels.newWriter(ch, null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
            assert e.getMessage().equals("charset") : "Unexpected message: " + e.getMessage();
        }
    }

    private static void testValidChannelAndCharset() throws IOException {
        WritableByteChannel ch = new DummyWritableByteChannel();
        Charset charset = Charset.forName("UTF-8");
        Writer writer = Channels.newWriter(ch, charset);
        if (writer == null) {
            throw new AssertionError("Writer should not be null");
        }
        // Optionally, test that writing works, but since DummyWritableByteChannel does nothing, skip
        writer.close();
    }

    // Dummy implementation of WritableByteChannel for testing
    private static class DummyWritableByteChannel implements WritableByteChannel {
        private boolean open = true;
        @Override
        public int write(java.nio.ByteBuffer src) throws IOException {
            return src.remaining();
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
