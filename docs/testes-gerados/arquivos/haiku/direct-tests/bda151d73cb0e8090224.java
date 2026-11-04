/*
 * @test
 * @summary Verifies that the single-byte read() of the stream returned by Channels.newInputStream returns unsigned byte values and -1 at end of stream
 * @run main ChannelsWitupTest
 */

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class ChannelsWitupTest {

    static class ArrayChannel implements ReadableByteChannel {
        private final byte[] data;
        private int pos = 0;
        private boolean open = true;

        ArrayChannel(byte[] data) {
            this.data = data;
        }

        @Override
        public int read(ByteBuffer dst) {
            if (pos >= data.length)
                return -1;
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

    static void check(boolean cond, String msg) {
        if (!cond)
            throw new RuntimeException(msg);
    }

    public static void main(String[] args) throws Exception {
        // Empty channel: read() must return -1 immediately
        InputStream empty = Channels.newInputStream(new ArrayChannel(new byte[0]));
        check(empty.read() == -1, "empty stream should return -1");

        // Bytes above 0x7F must be returned as unsigned values
        InputStream high = Channels.newInputStream(
                new ArrayChannel(new byte[] { (byte) 0x80, (byte) 0x7F }));
        check(high.read() == 128, "byte 0x80 should read as 128");
        check(high.read() == 127, "byte 0x7F should read as 127");
        check(high.read() == -1, "end of stream should return -1");
        check(high.read() == -1, "repeated read at end should return -1");

        // 0xFF must be masked to 255, not -1
        InputStream ff = Channels.newInputStream(new ArrayChannel(new byte[] { (byte) 0xFF }));
        check(ff.read() == 255, "byte 0xFF should read as 255");
        check(ff.read() == -1, "stream should be at end after single byte");
    }
}
