/*
 * @test
 * @summary Verifies that Channels.newOutputStream write(int) forwards single bytes to the underlying WritableByteChannel and reports zero-byte writes.
 * @run main ChannelsWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;

public class ChannelsWitupTest {

    static class RecordingChannel implements WritableByteChannel {
        private final ByteArrayOutputStream out = new ByteArrayOutputStream();
        private boolean open = true;

        @Override
        public int write(ByteBuffer src) throws IOException {
            int n = src.remaining();
            byte[] tmp = new byte[n];
            src.get(tmp);
            out.write(tmp, 0, n);
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
            return out.toByteArray();
        }
    }

    static class ZeroWriteChannel implements WritableByteChannel {
        @Override
        public int write(ByteBuffer src) throws IOException {
            return 0;
        }

        @Override
        public boolean isOpen() {
            return true;
        }

        @Override
        public void close() {
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testSingleBytesAreForwarded() throws IOException {
        RecordingChannel ch = new RecordingChannel();
        OutputStream os = Channels.newOutputStream(ch);
        os.write(65);
        os.write(66 + 256);
        os.write(-1);
        byte[] got = ch.bytes();
        check(got.length == 3, "expected 3 bytes but got " + got.length);
        check(got[0] == 65, "byte 0 mismatch: " + got[0]);
        check(got[1] == 66, "byte 1 mismatch (low 8 bits expected): " + got[1]);
        check(got[2] == (byte) 0xFF, "byte 2 mismatch: " + got[2]);
    }

    static void testZeroByteWriteIsRejected() throws IOException {
        OutputStream os = Channels.newOutputStream(new ZeroWriteChannel());
        boolean thrown = false;
        try {
            os.write(1);
        } catch (RuntimeException e) {
            thrown = true;
            check("no bytes written".equals(e.getMessage()),
                  "unexpected message: " + e.getMessage());
        }
        check(thrown, "expected RuntimeException for zero-byte write");
    }

    public static void main(String[] args) throws Exception {
        testSingleBytesAreForwarded();
        testZeroByteWriteIsRejected();
    }
}
