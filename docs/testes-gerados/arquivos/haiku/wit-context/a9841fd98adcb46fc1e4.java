/*
 * @test
 * @summary Checks public Channels.newWriter(WritableByteChannel, Charset) null guards and UTF-8 output
 * @run main ChannelsWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;

public class ChannelsWitupTest {

    interface Action {
        void run() throws Exception;
    }

    static final class RecordingChannel implements WritableByteChannel {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        private boolean open = true;

        @Override
        public int write(ByteBuffer src) throws IOException {
            int n = src.remaining();
            byte[] b = new byte[n];
            src.get(b);
            out.write(b, 0, n);
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

    static void expectNpe(String name, Action action) {
        try {
            action.run();
        } catch (NullPointerException expected) {
            return;
        } catch (Exception e) {
            throw new RuntimeException(name + ": unexpected exception " + e, e);
        }
        throw new RuntimeException(name + ": NullPointerException not thrown");
    }

    public static void main(String[] args) throws Exception {
        Charset utf8 = Charset.forName("UTF-8");

        expectNpe("nullCharset", () -> Channels.newWriter(new RecordingChannel(), (Charset) null));
        expectNpe("nullChannel", () -> Channels.newWriter(null, utf8));

        RecordingChannel ch = new RecordingChannel();
        Writer w = Channels.newWriter(ch, utf8);
        if (w == null) {
            throw new RuntimeException("newWriter returned null");
        }
        w.write("caf\u00e9");
        w.flush();

        byte[] expected = {0x63, 0x61, 0x66, (byte) 0xC3, (byte) 0xA9};
        byte[] actual = ch.out.toByteArray();
        if (actual.length != expected.length) {
            throw new RuntimeException("unexpected length: " + actual.length);
        }
        for (int i = 0; i < expected.length; i++) {
            if (actual[i] != expected[i]) {
                throw new RuntimeException("unexpected byte at " + i + ": " + actual[i]);
            }
        }
        w.close();
    }
}
