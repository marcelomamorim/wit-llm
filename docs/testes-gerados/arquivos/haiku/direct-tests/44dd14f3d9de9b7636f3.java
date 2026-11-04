/*
 * @test
 * @summary Basic observable behavior of Channels.newInputStream
 * @run main ChannelsWitupTest
 */

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class ChannelsWitupTest {

    static final class ArrayChannel implements ReadableByteChannel {
        private final byte[] data;
        private int pos;
        private boolean open = true;

        ArrayChannel(byte[] data) {
            this.data = data;
        }

        @Override
        public int read(ByteBuffer dst) throws IOException {
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

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullChannelRejected() {
        try {
            Channels.newInputStream(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null channel");
    }

    static void testReadsBytesInOrder() throws Exception {
        ArrayChannel ch = new ArrayChannel(new byte[] {1, 2, 3});
        InputStream in = Channels.newInputStream(ch);
        check(in != null, "newInputStream returned null");
        check(in.read() == 1, "first byte mismatch");
        check(in.read() == 2, "second byte mismatch");
        check(in.read() == 3, "third byte mismatch");
        check(in.read() == -1, "expected end of stream");
    }

    static void testBulkReadReturnsData() throws Exception {
        ArrayChannel ch = new ArrayChannel(new byte[] {10, 20, 30, 40});
        InputStream in = Channels.newInputStream(ch);
        byte[] buf = new byte[4];
        int total = 0;
        while (total < buf.length) {
            int n = in.read(buf, total, buf.length - total);
            if (n < 0) {
                break;
            }
            total += n;
        }
        check(total == 4, "expected 4 bytes, got " + total);
        check(buf[0] == 10 && buf[1] == 20 && buf[2] == 30 && buf[3] == 40,
              "bulk read content mismatch");
    }

    static void testCloseClosesChannel() throws Exception {
        ArrayChannel ch = new ArrayChannel(new byte[] {7});
        InputStream in = Channels.newInputStream(ch);
        check(ch.isOpen(), "channel should be open before close");
        in.close();
        check(!ch.isOpen(), "channel should be closed after stream close");
    }

    public static void main(String[] args) throws Exception {
        testNullChannelRejected();
        testReadsBytesInOrder();
        testBulkReadReturnsData();
        testCloseClosesChannel();
    }
}
