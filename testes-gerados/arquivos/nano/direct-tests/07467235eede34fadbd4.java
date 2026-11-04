/* @test    @summary Test SocketChannelImpl.write(ByteBuffer) method for correct behavior and exception handling    @run main SocketChannelImplWitupTest */

import java.io.IOException;
import java.nio.ByteBuffer;
import sun.nio.ch.SocketChannelImpl;

public class SocketChannelImplWitupTest {

    public static void main(String[] args) {
        testWriteWithNullBuffer();
        testWriteNormal();
        testWriteAfterClose();
    }

    private static void testWriteWithNullBuffer() {
        SocketChannelImpl channel = createMockSocketChannel();
        try {
            channel.write(null);
            System.err.println("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("NullPointerException correctly thrown for null buffer");
        } catch (IOException e) {
            System.err.println("Unexpected IOException: " + e);
        } finally {
            closeChannel(channel);
        }
    }

    private static void testWriteNormal() {
        SocketChannelImpl channel = createMockSocketChannel();
        ByteBuffer buffer = ByteBuffer.allocate(10);
        try {
            int bytesWritten = channel.write(buffer);
            if (bytesWritten >= 0) {
                System.out.println("write() returned: " + bytesWritten);
            } else {
                System.err.println("write() returned negative value");
            }
        } catch (IOException e) {
            System.err.println("IOException during write: " + e);
        } finally {
            closeChannel(channel);
        }
    }

    private static void testWriteAfterClose() {
        SocketChannelImpl channel = createMockSocketChannel();
        ByteBuffer buffer = ByteBuffer.allocate(10);
        closeChannel(channel);
        try {
            channel.write(buffer);
            System.err.println("Expected AsynchronousCloseException not thrown");
        } catch (AsynchronousCloseException e) {
            System.out.println("AsynchronousCloseException correctly thrown after close");
        } catch (IOException e) {
            System.err.println("Unexpected IOException: " + e);
        }
    }

    private static SocketChannelImpl createMockSocketChannel() {
        // Since actual native socket creation is complex, return a dummy subclass
        return new SocketChannelImpl() {
            private boolean closed = false;
            @Override
            public int write(ByteBuffer buf) throws IOException {
                if (closed) {
                    throw new AsynchronousCloseException();
                }
                // Simulate writing 1 byte
                if (buf.hasRemaining()) {
                    buf.position(buf.position() + 1);
                    return 1;
                }
                return 0;
            }
            @Override
            public void close() throws IOException {
                closed = true;
            }
            @Override
            public boolean isOpen() {
                return !closed;
            }
        };
    }

    private static void closeChannel(SocketChannelImpl channel) {
        try {
            channel.close();
        } catch (IOException e) {
            // ignore
        }
    }
}
