/*
 * @test
 * @summary Test java.nio.channels.Channels.newReader(ReadableByteChannel, Charset) for null checks and basic behavior
 * @run main ChannelsWitupTest
 */

import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.io.Reader;
import java.util.Objects;

public class ChannelsWitupTest {
    public static void main(String[] args) throws Exception {
        testNullCharsetThrowsNPE();
        testNullChannelThrowsNPE();
        testValidCharsetReturnsReader();
    }

    private static void testNullCharsetThrowsNPE() {
        ReadableByteChannel dummyChannel = new DummyReadableByteChannel();
        try {
            Channels.newReader(dummyChannel, null);
            throw new RuntimeException("Expected NullPointerException for null charset");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testNullChannelThrowsNPE() {
        Charset cs = Charset.defaultCharset();
        try {
            Channels.newReader(null, cs);
            throw new RuntimeException("Expected NullPointerException for null channel");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testValidCharsetReturnsReader() throws Exception {
        ReadableByteChannel dummyChannel = new DummyReadableByteChannel();
        Charset cs = Charset.defaultCharset();
        Reader r = Channels.newReader(dummyChannel, cs);
        if (r == null) {
            throw new RuntimeException("newReader returned null Reader");
        }
        // We do not test read behavior as DummyReadableByteChannel is empty
    }

    // Minimal dummy ReadableByteChannel implementation for testing
    private static class DummyReadableByteChannel implements ReadableByteChannel {
        private boolean open = true;
        public int read(java.nio.ByteBuffer dst) {
            return -1; // simulate EOF
        }
        public boolean isOpen() {
            return open;
        }
        public void close() {
            open = false;
        }
    }
}
