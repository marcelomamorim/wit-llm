/*
 * @test
 * @summary Test basic behavior of AsynchronousSocketChannelImpl.read(ByteBuffer)
 * @run main AsynchronousSocketChannelImplWitupTest
 */

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import sun.nio.ch.AsynchronousSocketChannelImpl;
import sun.nio.ch.AsynchronousChannelGroupImpl;

public class AsynchronousSocketChannelImplWitupTest {

    // Minimal subclass to allow instantiation for testing
    static class TestAsynchronousSocketChannelImpl extends AsynchronousSocketChannelImpl {

        TestAsynchronousSocketChannelImpl() throws IOException {
            super(new AsynchronousChannelGroupImpl() {
                @Override
                public void shutdown() {}
                @Override
                public boolean isShutdown() { return false; }
                @Override
                public boolean isTerminated() { return false; }
                @Override
                public boolean awaitTermination(long timeout, TimeUnit unit) { return false; }
                @Override
                public void shutdownNow() {}
            });
        }

        // Expose read method for testing
        @Override
        public Future<Integer> read(ByteBuffer dst) {
            return super.read(dst);
        }

        // Override abstract methods to avoid compilation errors
        @Override
        public <A> void read(boolean blocking, ByteBuffer dst, A attachment, long timeout, TimeUnit unit,
                             CompletionHandler<Integer, ? super A> handler) {
            // Not used in this test
        }

        @Override
        public <A> void write(boolean blocking, ByteBuffer src, A attachment, long timeout, TimeUnit unit,
                              CompletionHandler<Integer, ? super A> handler) {
            // Not used in this test
        }

        @Override
        public void implClose() throws IOException {
            // No-op for test
        }
    }

    public static void main(String[] args) throws Exception {
        testReadWithReadOnlyBuffer();
        testReadWithNormalBuffer();
    }

    private static void testReadWithReadOnlyBuffer() throws Exception {
        TestAsynchronousSocketChannelImpl channel = new TestAsynchronousSocketChannelImpl();
        ByteBuffer readOnlyBuffer = ByteBuffer.allocate(10).asReadOnlyBuffer();
        try {
            channel.read(readOnlyBuffer);
            throw new RuntimeException("Expected IllegalArgumentException for read-only buffer");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    private static void testReadWithNormalBuffer() throws Exception {
        TestAsynchronousSocketChannelImpl channel = new TestAsynchronousSocketChannelImpl();
        ByteBuffer buffer = ByteBuffer.allocate(10);
        Future<Integer> future = channel.read(buffer);
        if (future == null) {
            throw new RuntimeException("read() returned null Future");
        }
    }
}
