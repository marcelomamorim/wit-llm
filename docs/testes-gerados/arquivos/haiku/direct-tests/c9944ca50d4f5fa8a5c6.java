/*
 * @test
 * @summary Verifies that AsynchronousSocketChannel.read(ByteBuffer) rejects read-only buffers with IllegalArgumentException
 * @run main AsynchronousSocketChannelImplWitupTest
 */

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

public class AsynchronousSocketChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testReadOnlyBufferRejected();
        testReadOnlyCheckPrecedesClosedState();
    }

    static void testReadOnlyBufferRejected() throws Exception {
        try (AsynchronousSocketChannel ch = AsynchronousSocketChannel.open()) {
            ByteBuffer ro = ByteBuffer.allocate(16).asReadOnlyBuffer();
            try {
                ch.read(ro);
                throw new RuntimeException("Expected IllegalArgumentException for read-only buffer");
            } catch (IllegalArgumentException e) {
                if (!"Read-only buffer".equals(e.getMessage())) {
                    throw new RuntimeException("Unexpected message: " + e.getMessage(), e);
                }
            }
        }
    }

    static void testReadOnlyCheckPrecedesClosedState() throws Exception {
        AsynchronousSocketChannel ch = AsynchronousSocketChannel.open();
        ch.close();
        ByteBuffer ro = ByteBuffer.allocate(8).asReadOnlyBuffer();
        try {
            ch.read(ro);
            throw new RuntimeException("Expected IllegalArgumentException for read-only buffer on closed channel");
        } catch (IllegalArgumentException e) {
            if (!"Read-only buffer".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage(), e);
            }
        }
    }
}
