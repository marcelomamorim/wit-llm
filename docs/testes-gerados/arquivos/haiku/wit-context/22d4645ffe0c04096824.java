/*
 * @test
 * @summary Verifies that the OutputStream returned by Channels.newOutputStream writes the low 8 bits of the int argument through write(int)
 * @run main ChannelsWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;

public class ChannelsWitupTest {

    static final class CollectingChannel implements WritableByteChannel {
        final ByteArrayOutputStream sink = new ByteArrayOutputStream();
        private boolean open = true;

        @Override
        public int write(ByteBuffer src) {
            int n = src.remaining();
            byte[] tmp = new byte[n];
            src.get(tmp);
            sink.write(tmp, 0, n);
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

        byte[] bytes() {
            return sink.toByteArray();
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws IOException {
        CollectingChannel ch = new CollectingChannel();
        OutputStream out = Channels.newOutputStream(ch);

        out.write(0x41);
        check(ch.bytes().length == 1, "expected exactly one byte, got " + ch.bytes().length);
        check(ch.bytes()[0] == (byte) 0x41, "expected 0x41 but got " + ch.bytes()[0]);

        out.write(0x1FF);
        check(ch.bytes().length == 2, "expected two bytes after second write");
        check(ch.bytes()[1] == (byte) 0xFF, "expected low 8 bits 0xFF but got " + ch.bytes()[1]);

        out.write(-1);
        check(ch.bytes().length == 3, "expected three bytes after negative write");
        check(ch.bytes()[2] == (byte) 0xFF, "expected 0xFF for -1 but got " + ch.bytes()[2]);

        out.write(0);
        check(ch.bytes().length == 4, "expected four bytes after zero write");
        check(ch.bytes()[3] == 0, "expected 0 but got " + ch.bytes()[3]);

        CollectingChannel ordered = new CollectingChannel();
        OutputStream os = Channels.newOutputStream(ordered);
        os.write(1);
        os.write(2);
        os.write(3);
        byte[] expected = {1, 2, 3};
        check(java.util.Arrays.equals(expected, ordered.bytes()),
              "bytes not written in order: " + java.util.Arrays.toString(ordered.bytes()));
    }
}
