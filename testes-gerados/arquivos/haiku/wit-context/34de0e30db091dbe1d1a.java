/*
 * @test
 * @summary Verifies that the OutputStream returned by Channels.newOutputStream writes the low-order byte of write(int) to the channel
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
        final ByteArrayOutputStream data = new ByteArrayOutputStream();
        private boolean open = true;

        @Override
        public int write(ByteBuffer src) throws IOException {
            int n = src.remaining();
            byte[] b = new byte[n];
            src.get(b);
            data.write(b, 0, n);
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
        RecordingChannel ch = new RecordingChannel();
        OutputStream os = Channels.newOutputStream(ch);

        os.write(65);
        os.write(0x1FF);
        os.write(256);

        byte[] written = ch.data.toByteArray();
        byte[] expected = new byte[] {65, (byte) 0xFF, 0};
        if (written.length != expected.length) {
            throw new RuntimeException("unexpected length: " + written.length);
        }
        for (int i = 0; i < expected.length; i++) {
            if (written[i] != expected[i]) {
                throw new RuntimeException("mismatch at " + i + ": " + written[i]);
            }
        }
    }
}
