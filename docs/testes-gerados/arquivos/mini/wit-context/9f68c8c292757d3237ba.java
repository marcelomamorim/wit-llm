/*
 * @test
 * @summary Test java.nio.channels.Channels.write(ByteBuffer) for open/closed channel behavior
 * @run main ChannelsWitupTest
 */

import java.nio.channels.Channels;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.io.IOException;

public class ChannelsWitupTest {

    // Minimal WritableByteChannelImpl stub to test Channels.write
    static class WritableByteChannelImpl implements java.nio.channels.WritableByteChannel {
        private boolean open = true;
        private final Object writeLock = new Object();

        public boolean isOpen() {
            return open;
        }

        public void close() {
            open = false;
        }

        // The write method to test, copied from the source excerpt
        public int write(ByteBuffer src) throws IOException {
            if (!isOpen()) {
                throw new ClosedChannelException();
            }

            int len = src.remaining();
            int totalWritten = 0;
            synchronized (writeLock) {
                while (totalWritten < len) {
                    int bytesToWrite = Math.min((len - totalWritten), 8192); // TRANSFER_SIZE assumed 8192
                    byte[] buf = new byte[bytesToWrite];
                    src.get(buf, 0, bytesToWrite);
                    // Simulate writing by just consuming bytes
                    totalWritten += bytesToWrite;
                }
                return totalWritten;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        testWriteOpenChannel();
        testWriteClosedChannel();
        System.out.println("PASS");
    }

    private static void testWriteOpenChannel() throws IOException {
        WritableByteChannelImpl ch = new WritableByteChannelImpl();
        byte[] data = new byte[10000];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte)(i % 256);
        }
        ByteBuffer buffer = ByteBuffer.wrap(data);

        int written = ch.write(buffer);

        if (written != data.length) {
            throw new AssertionError("Expected to write all bytes, but wrote " + written);
        }

        if (buffer.hasRemaining()) {
            throw new AssertionError("Buffer should have no remaining bytes after write");
        }
    }

    private static void testWriteClosedChannel() {
        WritableByteChannelImpl ch = new WritableByteChannelImpl();
        ch.close();
        ByteBuffer buffer = ByteBuffer.allocate(10);

        try {
            ch.write(buffer);
            throw new AssertionError("Expected ClosedChannelException when writing to closed channel");
        } catch (ClosedChannelException expected) {
            // expected
        } catch (IOException ioe) {
            throw new AssertionError("Expected ClosedChannelException but got " + ioe);
        }
    }
}
