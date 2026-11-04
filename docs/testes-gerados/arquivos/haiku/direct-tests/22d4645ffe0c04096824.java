/*
 * @test
 * @summary Verifies that OutputStream.write(int) from Channels.newOutputStream writes the low 8 bits to the channel
 * @run main ChannelsWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;

public class ChannelsWitupTest {

    static class Sink implements WritableByteChannel {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        boolean open = true;

        @Override
        public int write(ByteBuffer src) {
            int n = src.remaining();
            byte[] a = new byte[n];
            src.get(a);
            out.write(a, 0, n);
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
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) throws Exception {
        Sink sink = new Sink();
        OutputStream os = Channels.newOutputStream(sink);

        os.write(0x41);
        byte[] after1 = sink.out.toByteArray();
        check(after1.length == 1, "expected 1 byte, got " + after1.length);
        check(after1[0] == (byte) 0x41, "expected 0x41");

        os.write(0x1FF);
        byte[] after2 = sink.out.toByteArray();
        check(after2.length == 2, "expected 2 bytes, got " + after2.length);
        check(after2[1] == (byte) 0xFF, "expected low 8 bits 0xFF for 0x1FF");

        os.write(-1);
        byte[] after3 = sink.out.toByteArray();
        check(after3.length == 3, "expected 3 bytes, got " + after3.length);
        check(after3[2] == (byte) 0xFF, "expected 0xFF for -1");

        os.write(0x100);
        byte[] after4 = sink.out.toByteArray();
        check(after4.length == 4, "expected 4 bytes, got " + after4.length);
        check(after4[3] == (byte) 0x00, "expected 0x00 for 0x100");

        os.write(0x7A);
        byte[] all = sink.out.toByteArray();
        check(all.length == 5, "expected 5 bytes total, got " + all.length);
        check(all[4] == (byte) 0x7A, "expected 0x7A last");
    }
}
