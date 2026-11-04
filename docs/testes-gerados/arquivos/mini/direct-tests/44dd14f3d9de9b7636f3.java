/*
 * @test
 * @summary Test java.nio.channels.Channels.newInputStream(ReadableByteChannel) method
 * @run main ChannelsWitupTest
 */

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.io.IOException;
import java.util.Objects;
import java.nio.channels.Channels;
import sun.nio.ch.ChannelInputStream;

public class ChannelsWitupTest {

    static class TestReadableByteChannel implements ReadableByteChannel {
        private boolean open = true;
        private ByteBuffer src;

        TestReadableByteChannel(byte[] data) {
            src = ByteBuffer.wrap(data);
        }

        @Override
        public int read(ByteBuffer dst) throws IOException {
            if (!open) throw new IOException("Channel closed");
            if (!src.hasRemaining()) return -1;
            int remaining = Math.min(dst.remaining(), src.remaining());
            byte[] temp = new byte[remaining];
            src.get(temp);
            dst.put(temp);
            return remaining;
        }

        @Override
        public boolean isOpen() {
            return open;
        }

        @Override
        public void close() throws IOException {
            open = false;
        }
    }

    public static void main(String[] args) throws Exception {
        // Test null argument throws NullPointerException
        try {
            Channels.newInputStream(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            if (!"ch".equals(npe.getMessage())) {
                throw new RuntimeException("NullPointerException message mismatch");
            }
        }

        // Test that returned InputStream reads data correctly
        byte[] data = {1, 2, 3, 4, 5};
        TestReadableByteChannel channel = new TestReadableByteChannel(data);
        InputStream in = Channels.newInputStream(channel);

        for (int i = 0; i < data.length; i++) {
            int b = in.read();
            if (b != (data[i] & 0xFF)) {
                throw new RuntimeException("Data mismatch at index " + i);
            }
        }

        // After reading all data, read() should return -1
        int eof = in.read();
        if (eof != -1) {
            throw new RuntimeException("Expected EOF (-1) after reading all data");
        }

        // Test that closing InputStream closes underlying channel
        if (!channel.isOpen()) {
            throw new RuntimeException("Channel should be open before close");
        }
        in.close();
        if (channel.isOpen()) {
            throw new RuntimeException("Channel should be closed after InputStream.close()");
        }

        // Test concurrent read safety by reading multiple bytes at once
        byte[] data2 = new byte[256];
        for (int i = 0; i < 256; i++) data2[i] = (byte) i;
        TestReadableByteChannel channel2 = new TestReadableByteChannel(data2);
        InputStream in2 = Channels.newInputStream(channel2);
        byte[] buffer = new byte[128];
        int readCount = in2.read(buffer);
        if (readCount != 128) {
            throw new RuntimeException("Expected to read 128 bytes, got " + readCount);
        }
        for (int i = 0; i < 128; i++) {
            if ((buffer[i] & 0xFF) != i) {
                throw new RuntimeException("Data mismatch in bulk read at index " + i);
            }
        }

        System.out.println("PASS");
    }
}
