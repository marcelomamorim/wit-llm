/*
 * @test
 * @summary Test for Channels.newWriter(WritableByteChannel, Charset)
 * @run main ChannelsWitupTest
 */

import java.nio.channels.WritableByteChannel;
import java.nio.channels.Channels;
import java.nio.charset.Charset;
import java.io.Writer;
import java.io.IOException;

public class ChannelsWitupTest {

    public static void main(String[] args) throws IOException {
        testNewWriterWithNullChannel();
        testNewWriterWithNullCharset();
        testNewWriterWithValidChannelAndCharset();
    }

    static void testNewWriterWithNullChannel() {
        try {
            Charset charset = Charset.forName("UTF-8");
            Channels.newWriter(null, charset);
            throw new AssertionError("Expected NullPointerException for null channel");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testNewWriterWithNullCharset() {
        try {
            WritableByteChannel channel = new DummyWritableByteChannel();
            Channels.newWriter(channel, null);
            throw new AssertionError("Expected NullPointerException for null charset");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testNewWriterWithValidChannelAndCharset() throws IOException {
        WritableByteChannel channel = new DummyWritableByteChannel();
        Charset charset = Charset.forName("UTF-8");
        Writer writer = Channels.newWriter(channel, charset);
        if (writer == null) {
            throw new AssertionError("Writer should not be null");
        }
        String testString = "hello";
        writer.write(testString);
        writer.flush();
        // Verify that data was written to the channel
        byte[] data = ((DummyWritableByteChannel) channel).getData();
        String result = new String(data, charset);
        if (!result.equals(testString)) {
            throw new AssertionError("Data mismatch: expected '" + testString + "', got '" + result + "'");
        }
        writer.close();
    }

    static class DummyWritableByteChannel implements WritableByteChannel {
        private final java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        private boolean open = true;
        @Override
        public int write(java.nio.ByteBuffer src) throws IOException {
            int remaining = src.remaining();
            byte[] bytes = new byte[remaining];
            src.get(bytes);
            baos.write(bytes);
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
        public byte[] getData() {
            return baos.toByteArray();
        }
    }
}
