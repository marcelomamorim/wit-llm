/*
 * @test
 * @summary Basic behavior of Channels.newReader(ReadableByteChannel, Charset)
 * @run main ChannelsWitupTest
 */

import java.io.IOException;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;

public class ChannelsWitupTest {

    static final class BytesChannel implements ReadableByteChannel {
        private final byte[] data;
        private int pos;
        private boolean open = true;

        BytesChannel(byte[] data) {
            this.data = data;
        }

        @Override
        public int read(ByteBuffer dst) throws IOException {
            if (!open) {
                throw new IOException("closed");
            }
            if (pos >= data.length) {
                return -1;
            }
            int n = Math.min(dst.remaining(), data.length - pos);
            dst.put(data, pos, n);
            pos += n;
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

    public static void main(String[] args) throws Exception {
        testNullCharsetThrowsNPE();
        testUtf8Decoding();
        testIso88591Decoding();
        testCloseReaderClosesChannel();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static String readAll(Reader r) throws IOException {
        StringBuilder sb = new StringBuilder();
        char[] buf = new char[16];
        int n;
        while ((n = r.read(buf)) != -1) {
            sb.append(buf, 0, n);
        }
        return sb.toString();
    }

    static void testNullCharsetThrowsNPE() {
        boolean thrown = false;
        try {
            Channels.newReader(new BytesChannel(new byte[0]), null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null charset");
    }

    static void testUtf8Decoding() throws IOException {
        // "h\u00e9llo" encoded as UTF-8
        byte[] bytes = new byte[] {
            0x68, (byte) 0xC3, (byte) 0xA9, 0x6C, 0x6C, 0x6F
        };
        Reader r = Channels.newReader(new BytesChannel(bytes),
                                      Charset.forName("UTF-8"));
        check(r != null, "newReader returned null");
        String s = readAll(r);
        r.close();
        check("h\u00e9llo".equals(s), "UTF-8 decode mismatch: " + s);
    }

    static void testIso88591Decoding() throws IOException {
        byte[] bytes = new byte[] { 0x61, (byte) 0xE9, 0x62 };
        Reader r = Channels.newReader(new BytesChannel(bytes),
                                      Charset.forName("ISO-8859-1"));
        String s = readAll(r);
        r.close();
        check("a\u00e9b".equals(s), "ISO-8859-1 decode mismatch: " + s);
    }

    static void testCloseReaderClosesChannel() throws IOException {
        BytesChannel ch = new BytesChannel(new byte[] { 0x41 });
        Reader r = Channels.newReader(ch, Charset.forName("UTF-8"));
        check(ch.isOpen(), "channel should be open before close");
        r.close();
        check(!ch.isOpen(), "closing reader should close underlying channel");
    }
}
