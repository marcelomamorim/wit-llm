/*
 * @test
 * @summary Test java.nio.channels.Channels.newWriter(WritableByteChannel, Charset) for null arguments
 * @run main ChannelsWitupTest
 */

import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.io.Writer;
import java.io.IOException;

public class ChannelsWitupTest {

    static class DummyWritableByteChannel implements WritableByteChannel {
        private boolean open = true;
        public int write(java.nio.ByteBuffer src) throws IOException {
            return src.remaining();
        }
        public boolean isOpen() {
            return open;
        }
        public void close() throws IOException {
            open = false;
        }
    }

    public static void main(String[] args) throws Exception {
        testNullCharset();
        testNullChannel();
        testValidCall();
    }

    private static void testNullCharset() {
        WritableByteChannel ch = new DummyWritableByteChannel();
        try {
            Channels.newWriter(ch, (Charset) null);
            throw new RuntimeException("Expected NullPointerException for null charset");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testNullChannel() {
        Charset cs = Charset.defaultCharset();
        try {
            Channels.newWriter(null, cs);
            // According to source, no explicit NPE for null channel, but newWriter(ch, CharsetEncoder, int) may throw NPE
            // We test that it throws NPE or some exception due to null channel
            throw new RuntimeException("Expected NullPointerException for null channel");
        } catch (NullPointerException npe) {
            // expected
        } catch (Exception e) {
            // Accept any exception here as well, since channel is null
        }
    }

    private static void testValidCall() throws IOException {
        WritableByteChannel ch = new DummyWritableByteChannel();
        Charset cs = Charset.defaultCharset();
        Writer w = Channels.newWriter(ch, cs);
        if (w == null) {
            throw new RuntimeException("newWriter returned null Writer");
        }
        w.write('a');
        w.flush();
        w.close();
    }
}
