/*
 * @test
 * @summary Checks Channels.newReader(ReadableByteChannel, Charset) null handling and decoding behavior
 * @run main ChannelsWitupTest
 */

import java.io.IOException;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ChannelsWitupTest {

    static final class ArrayChannel implements ReadableByteChannel {
        private final ByteBuffer data;
        private boolean open = true;

        ArrayChannel(byte[] bytes) {
            this.data = ByteBuffer.wrap(bytes);
        }

        @Override
        public int read(ByteBuffer dst) throws IOException {
            if (!data.hasRemaining()) {
                return -1;
            }
            int n = Math.min(dst.remaining(), data.remaining());
            for (int i = 0; i < n; i++) {
                dst.put(data.get());
            }
            return n;
        }

        @Override
        public boolean isOpen() {
            return open;
        }

        @Override
        public void close() {
            open = false;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static String readAll(Reader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        char[] buf = new char[16];
        int n;
        while ((n = reader.read(buf)) != -1) {
            sb.append(buf, 0, n);
        }
        return sb.toString();
    }

    static void testNullCharsetRejected() {
        boolean thrown = false;
        try {
            Channels.newReader(new ArrayChannel(new byte[0]), (Charset) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null charset must throw NullPointerException");
    }

    static void testNullChannelRejected() {
        boolean thrown = false;
        try {
            Channels.newReader((ReadableByteChannel) null, StandardCharsets.UTF_8);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null channel must throw NullPointerException");
    }

    static void testReturnsNonNullReader() {
        Reader r = Channels.newReader(new ArrayChannel(new byte[0]), StandardCharsets.UTF_8);
        check(r != null, "newReader must return a non-null Reader");
    }

    static void testDecodesAsciiBytes() throws IOException {
        byte[] bytes = "abc".getBytes(StandardCharsets.US_ASCII);
        Reader r = Channels.newReader(new ArrayChannel(bytes), StandardCharsets.US_ASCII);
        String s = readAll(r);
        check("abc".equals(s), "expected 'abc' but got '" + s + "'");
    }

    static void testDecodesUtf8Bytes() throws IOException {
        byte[] bytes = "h\u00e9".getBytes(StandardCharsets.UTF_8);
        Reader r = Channels.newReader(new ArrayChannel(bytes), StandardCharsets.UTF_8);
        String s = readAll(r);
        check("h\u00e9".equals(s), "expected UTF-8 decoded text but got '" + s + "'");
    }

    static void testEmptyChannelYieldsEof() throws IOException {
        Reader r = Channels.newReader(new ArrayChannel(new byte[0]), StandardCharsets.UTF_8);
        check(r.read() == -1, "empty channel must yield end of stream");
    }

    public static void main(String[] args) throws Exception {
        testNullCharsetRejected();
        testNullChannelRejected();
        testReturnsNonNullReader();
        testDecodesAsciiBytes();
        testDecodesUtf8Bytes();
        testEmptyChannelYieldsEof();
    }
}
