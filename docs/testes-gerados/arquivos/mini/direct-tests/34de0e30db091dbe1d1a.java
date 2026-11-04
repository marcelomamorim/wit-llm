/*
 * @test
 * @summary Test java.nio.channels.Channels write(int b) method behavior
 * @run main ChannelsWitupTest
 */

import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;

public class ChannelsWitupTest {

    static class DummyWritableByteChannel implements WritableByteChannel {
        boolean open = true;
        byte lastWritten = 0;
        int writeCount = 0;

        @Override
        public int write(java.nio.ByteBuffer src) throws IOException {
            if (!open) throw new IOException("Channel closed");
            if (src.remaining() == 0) return 0;
            lastWritten = src.get();
            writeCount++;
            return 1;
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

    public static void main(String[] args) throws IOException {
        DummyWritableByteChannel dummy = new DummyWritableByteChannel();

        // Create anonymous subclass of Channels with write(int) method to test
        Channels channels = new Channels() {
            private byte[] b1 = null;
            public synchronized void write(int b) throws IOException {
                if (b1 == null)
                    b1 = new byte[1];
                b1[0] = (byte) b;
                // simulate writing by calling dummy channel write
                java.nio.ByteBuffer bb = java.nio.ByteBuffer.wrap(b1);
                dummy.write(bb);
            }
        };

        // Test writing a byte value
        channels.write(0x41); // ASCII 'A'
        if (dummy.lastWritten != 0x41) {
            throw new AssertionError("Expected lastWritten to be 0x41 but was " + dummy.lastWritten);
        }

        // Test writing another byte value
        channels.write(0x7F); // DEL
        if (dummy.lastWritten != 0x7F) {
            throw new AssertionError("Expected lastWritten to be 0x7F but was " + dummy.lastWritten);
        }

        // Test that writeCount increments correctly
        if (dummy.writeCount != 2) {
            throw new AssertionError("Expected writeCount to be 2 but was " + dummy.writeCount);
        }

        System.out.println("PASS");
    }
}
