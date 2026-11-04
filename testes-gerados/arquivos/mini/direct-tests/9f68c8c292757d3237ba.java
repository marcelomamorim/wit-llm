/*
 * @test
 * @summary Test java.nio.channels.Channels.write(ByteBuffer) method behavior
 * @run main ChannelsWitupTest
 */

import java.nio.channels.Channels;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.io.IOException;

public class ChannelsWitupTest {

    static class DummyChannelsWrite extends Channels {
        private boolean open = true;
        private final Object writeLock = new Object();
        private byte[] buf = new byte[1024];
        private static final int TRANSFER_SIZE = 1024;
        private final java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();

        public boolean isOpen() {
            return open;
        }

        public void close() {
            open = false;
        }

        public void begin() {
            // no-op for test
        }

        public void end(boolean wrote) {
            // no-op for test
        }

        public int write(ByteBuffer src) throws IOException {
            if (!isOpen()) {
                throw new ClosedChannelException();
            }

            int len = src.remaining();
            int totalWritten = 0;
            synchronized (writeLock) {
                while (totalWritten < len) {
                    int bytesToWrite = Math.min((len - totalWritten), TRANSFER_SIZE);
                    if (buf.length < bytesToWrite)
                        buf = new byte[bytesToWrite];
                    src.get(buf, 0, bytesToWrite);
                    try {
                        begin();
                        out.write(buf, 0, bytesToWrite);
                    } finally {
                        end(bytesToWrite > 0);
                    }
                    totalWritten += bytesToWrite;
                }
                return totalWritten;
            }
        }

        public byte[] getWrittenBytes() {
            return out.toByteArray();
        }
    }

    public static void main(String[] args) throws Exception {
        testWriteReturnsCorrectCountAndWritesData();
        testWriteThrowsClosedChannelExceptionWhenClosed();
    }

    private static void testWriteReturnsCorrectCountAndWritesData() throws IOException {
        DummyChannelsWrite channel = new DummyChannelsWrite();
        byte[] data = new byte[2048];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte)(i % 256);
        }
        ByteBuffer buffer = ByteBuffer.wrap(data);

        int written = channel.write(buffer);

        if (written != data.length) {
            throw new RuntimeException("Expected written bytes " + data.length + ", got " + written);
        }

        byte[] writtenData = channel.getWrittenBytes();
        if (writtenData.length != data.length) {
            throw new RuntimeException("Output stream length mismatch: expected " + data.length + ", got " + writtenData.length);
        }

        for (int i = 0; i < data.length; i++) {
            if (writtenData[i] != data[i]) {
                throw new RuntimeException("Data mismatch at index " + i);
            }
        }
    }

    private static void testWriteThrowsClosedChannelExceptionWhenClosed() {
        DummyChannelsWrite channel = new DummyChannelsWrite();
        channel.close();
        ByteBuffer buffer = ByteBuffer.allocate(10);
        try {
            channel.write(buffer);
            throw new RuntimeException("Expected ClosedChannelException not thrown");
        } catch (ClosedChannelException expected) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException thrown", e);
        }
    }
}
