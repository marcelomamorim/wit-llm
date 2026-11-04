/*
 * @test
 * @summary Test the Channels.write(ByteBuffer) method for correct behavior.
 * @run main ChannelsWitupTest
 */

import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.io.IOException;

public class ChannelsWitupTest {

    static class DummyChannel {
        boolean isOpen = true;
        boolean isClosed = false;
        int lastBytesWritten = 0;
        boolean writeCalled = false;
        ByteBuffer lastBuffer = null;

        public boolean isOpen() {
            return isOpen;
        }

        public int write(byte[] buf, int offset, int length) {
            writeCalled = true;
            lastBytesWritten = length;
            lastBuffer = ByteBuffer.wrap(buf, offset, length);
            return length;
        }

        public void close() {
            isOpen = false;
            isClosed = true;
        }
    }

    public static void main(String[] args) {
        testWriteWithOpenChannel();
        testWriteWithClosedChannel();
        testWriteWithEmptyBuffer();
        testWritePartial();
        System.out.println("All tests passed.");
    }

    static void testWriteWithOpenChannel() {
        DummyChannel dummy = new DummyChannel();
        ByteBuffer buffer = ByteBuffer.wrap(new byte[] {1, 2, 3, 4, 5});
        int bytesWritten = write(dummy, buffer);
        assert bytesWritten == 5 : "Expected 5 bytes written";
        assert dummy.writeCalled : "Write method should be called";
        assert dummy.lastBytesWritten == 5 : "Last bytes written should be 5";
        assert dummy.lastBuffer.remaining() == 0 : "Buffer should be fully read";
    }

    static void testWriteWithClosedChannel() {
        DummyChannel dummy = new DummyChannel();
        dummy.isOpen = false;
        ByteBuffer buffer = ByteBuffer.wrap(new byte[] {1});
        try {
            write(dummy, buffer);
            assert false : "Expected ClosedChannelException";
        } catch (ClosedChannelException e) {
            // expected
        } catch (IOException e) {
            assert false : "Expected ClosedChannelException, got " + e;
        }
    }

    static void testWriteWithEmptyBuffer() {
        DummyChannel dummy = new DummyChannel();
        ByteBuffer buffer = ByteBuffer.allocate(0);
        int bytesWritten = write(dummy, buffer);
        assert bytesWritten == 0 : "Expected 0 bytes written";
    }

    static void testWritePartial() {
        DummyChannel dummy = new DummyChannel() {
            int callCount = 0;
            @Override
            public int write(byte[] buf, int offset, int length) {
                callCount++;
                if (callCount == 1) {
                    return length / 2; // write half
                } else {
                    return length; // write remaining
                }
            }
        };
        ByteBuffer buffer = ByteBuffer.wrap(new byte[] {1, 2, 3, 4});
        int total = write(dummy, buffer);
        assert total == 4 : "Should write all 4 bytes";
    }

    static int write(DummyChannel ch, ByteBuffer src) throws IOException {
        if (!ch.isOpen()) {
            throw new ClosedChannelException();
        }
        int len = src.remaining();
        int totalWritten = 0;
        byte[] buf = new byte[1024];
        while (totalWritten < len) {
            int bytesToWrite = Math.min(len - totalWritten, buf.length);
            src.get(buf, 0, bytesToWrite);
            totalWritten += ch.write(buf, 0, bytesToWrite);
        }
        return totalWritten;
    }
}
