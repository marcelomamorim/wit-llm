/*
 * @test
 * @summary Test for Channels.newReader(ReadableByteChannel, Charset)
 * @run main ChannelsWitupTest
 */

import java.io.Reader;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CharsetDecoder;
import java.nio.ByteBuffer;
import java.io.IOException;

public class ChannelsWitupTest {

    public static void main(String[] args) {
        testNewReaderWithNullChannel();
        testNewReaderWithNullCharset();
        testNewReaderWithValidChannelAndCharset();
    }

    static void testNewReaderWithNullChannel() {
        try {
            java.nio.channels.Channels.newReader(null, Charset.defaultCharset());
            System.err.println("Failed: Expected NullPointerException for null channel");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    static void testNewReaderWithNullCharset() {
        try {
            ReadableByteChannel channel = new DummyReadableByteChannel();
            java.nio.channels.Channels.newReader(channel, null);
            System.err.println("Failed: Expected NullPointerException for null charset");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    static void testNewReaderWithValidChannelAndCharset() {
        ReadableByteChannel channel = new DummyReadableByteChannel();
        Charset charset = Charset.forName("UTF-8");
        Reader reader = java.nio.channels.Channels.newReader(channel, charset);
        if (reader == null) {
            System.err.println("Failed: Reader is null");
        } else {
            System.out.println("Passed: newReader returns non-null Reader");
        }
        try {
            reader.close();
        } catch (IOException e) {
            System.err.println("Failed: Exception closing reader");
        }
    }

    static class DummyReadableByteChannel implements ReadableByteChannel {
        private boolean open = true;
        @Override
        public int read(ByteBuffer dst) throws IOException {
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
