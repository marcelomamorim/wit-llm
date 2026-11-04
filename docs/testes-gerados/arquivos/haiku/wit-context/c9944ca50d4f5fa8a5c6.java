/*
 * @test
 * @summary Verifies that AsynchronousSocketChannelImpl.read(ByteBuffer) rejects read-only buffers synchronously and accepts writable buffers
 * @run main AsynchronousSocketChannelImplWitupTest
 */

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

public class AsynchronousSocketChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testReadOnlyBufferRejected();
        testWritableBufferNotRejectedAsReadOnly();
    }

    static void testReadOnlyBufferRejected() throws Exception {
        AsynchronousSocketChannel ch = AsynchronousSocketChannel.open();
        try {
            ByteBuffer ro = ByteBuffer.allocate(16).asReadOnlyBuffer();
            boolean thrown = false;
            try {
                ch.read(ro);
            } catch (IllegalArgumentException e) {
                thrown = true;
                if (!"Read-only buffer".equals(e.getMessage())) {
                    throw new RuntimeException("Unexpected message: " + e.getMessage());
                }
            }
            if (!thrown) {
                throw new RuntimeException("IllegalArgumentException expected for read-only buffer");
            }
        } finally {
            ch.close();
        }
    }

    static void testWritableBufferNotRejectedAsReadOnly() throws Exception {
        AsynchronousSocketChannel ch = AsynchronousSocketChannel.open();
        try {
            ByteBuffer rw = ByteBuffer.allocate(16);
            try {
                ch.read(rw);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Writable buffer wrongly rejected: " + e.getMessage());
            } catch (RuntimeException e) {
                // Other failures (e.g. not connected) are outside the read-only check under test.
            }
        } finally {
            ch.close();
        }
    }
}
