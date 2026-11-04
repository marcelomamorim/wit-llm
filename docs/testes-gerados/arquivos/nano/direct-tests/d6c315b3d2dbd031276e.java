/* @test    @summary Test AsynchronousFileChannelImpl.write(ByteBuffer, long) method for correct Future return and behavior    @run main AsynchronousFileChannelImplWitupTest */

import java.nio.ByteBuffer;
import java.util.concurrent.Future;
import java.io.IOException;
import sun.nio.ch.AsynchronousFileChannelImpl;

public class AsynchronousFileChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testWriteReturnsFuture();
        testWriteWithEmptyBuffer();
        testWriteWithNullBuffer();
    }

    private static void testWriteReturnsFuture() throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        AsynchronousFileChannelImpl channel = createChannel();
        Future<Integer> future = channel.write(buffer, 0L);
        if (future == null) {
            throw new AssertionError("write() should not return null");
        }
        Integer result = future.get();
        if (result == null || result < 0 || result > buffer.capacity()) {
            throw new AssertionError("Unexpected write result: " + result);
        }
        channel.close();
    }

    private static void testWriteWithEmptyBuffer() throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(0);
        AsynchronousFileChannelImpl channel = createChannel();
        Future<Integer> future = channel.write(buffer, 0L);
        Integer result = future.get();
        if (result != 0) {
            throw new AssertionError("Writing empty buffer should return 0, got: " + result);
        }
        channel.close();
    }

    private static void testWriteWithNullBuffer() throws Exception {
        AsynchronousFileChannelImpl channel = createChannel();
        try {
            channel.write(null, 0L);
            throw new AssertionError("Expected NullPointerException for null ByteBuffer");
        } catch (NullPointerException e) {
            // expected
        } finally {
            channel.close();
        }
    }

    private static AsynchronousFileChannelImpl createChannel() {
        // Create a dummy FileDescriptor (not used in actual I/O in this test)
        return new AsynchronousFileChannelImpl(new java.io.FileDescriptor(), true, false, java.util.concurrent.Executors.newSingleThreadExecutor()) {
            @Override
            public Future<Integer> implWrite(ByteBuffer src, long position, Object attachment, java.nio.channels.CompletionHandler<Integer, ?> handler) {
                // Simulate immediate completion
                return new java.util.concurrent.Future<Integer>() {
                    public boolean cancel(boolean mayInterruptIfRunning) { return false; }
                    public boolean isCancelled() { return false; }
                    public boolean isDone() { return true; }
                    public Integer get() { return src.remaining(); }
                    public Integer get(long timeout, java.util.concurrent.TimeUnit unit) { return src.remaining(); }
                };
            }
        };
    }
}
