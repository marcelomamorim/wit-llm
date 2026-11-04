/*
 * @test
 * @summary Test for java.nio.channels.Channels.newReader(ReadableByteChannel, Charset)
 * @run main ChannelsWitupTest
 */

import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.io.IOException;
import java.io.StringReader;

public class ChannelsWitupTest {

    public static void main(String[] args) {
        testNullCharset();
        testNullChannel();
    }

    public static void testNullCharset() {
        ReadableByteChannel channel = new StringChannel();
        try {
            Channels.newReader(channel, null);
            System.err.println("Expected NullPointerException for null charset");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
            System.exit(1);
        }
    }

    public static void testNullChannel() {
        Charset charset = Charset.defaultCharset();
        try {
            Channels.newReader(null, charset);
            System.err.println("Expected NullPointerException for null channel");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
            System.exit(1);
        }
    }

    static class StringChannel implements ReadableByteChannel {
        @Override
        public int read(java.nio.ByteBuffer dst) throws IOException {
            return -1; // End of stream
        }
        @Override
        public boolean isOpen() {
            return true;
        }
        @Override
        public void close() throws IOException {
            // no-op
        }
    }
}
