/*
 * @test
 * @summary Test sun.nio.ch.AsynchronousFileChannelImpl.write(ByteBuffer,long) method
 * @run main AsynchronousFileChannelImplWitupTest
 */

import sun.nio.ch.AsynchronousFileChannelImpl;
import java.nio.ByteBuffer;
import java.nio.channels.FileLock;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedChannelException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.Executors;
import java.io.FileDescriptor;
import java.io.IOException;

public class AsynchronousFileChannelImplWitupTest {

    static class DummyChannel extends AsynchronousFileChannelImpl {

        DummyChannel(FileDescriptor fdObj, boolean reading, boolean writing, ExecutorService executor) {
            super(fdObj, reading, writing, executor);
        }

        @Override
        protected <A> Future<FileLock> implLock(long position, long size, boolean shared, A attachment,
                                               java.nio.channels.CompletionHandler<FileLock, ? super A> handler) {
            return null; // Not tested here
        }

        @Override
        protected <A> Future<Integer> implWrite(ByteBuffer src, long position, A attachment,
                                                java.nio.channels.CompletionHandler<Integer, ? super A> handler) {
            // Simple dummy implementation: return a completed Future with number of bytes to write
            return new CompletedFuture<>(src.remaining());
        }
    }

    // Simple completed Future implementation for testing
    static class CompletedFuture<V> implements Future<V> {
        private final V value;
        CompletedFuture(V value) { this.value = value; }
        public boolean cancel(boolean mayInterruptIfRunning) { return false; }
        public boolean isCancelled() { return false; }
        public boolean isDone() { return true; }
        public V get() { return value; }
        public V get(long timeout, java.util.concurrent.TimeUnit unit) { return value; }
    }

    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        FileDescriptor fd = new FileDescriptor();
        DummyChannel channel = new DummyChannel(fd, true, true, executor);

        // Test 1: write with non-empty ByteBuffer
        ByteBuffer buf = ByteBuffer.allocate(10);
        buf.put(new byte[] {1,2,3,4,5});
        buf.flip();
        Future<Integer> f = channel.write(buf, 0L);
        int written = f.get();
        if (written != 5) {
            throw new RuntimeException("Test 1 failed: expected 5 bytes written, got " + written);
        }

        // Test 2: write with empty ByteBuffer
        ByteBuffer emptyBuf = ByteBuffer.allocate(0);
        Future<Integer> f2 = channel.write(emptyBuf, 0L);
        int written2 = f2.get();
        if (written2 != 0) {
            throw new RuntimeException("Test 2 failed: expected 0 bytes written, got " + written2);
        }

        // Test 3: write after channel closed should throw ClosedChannelException from begin()
        channel.closed = true;
        try {
            channel.write(ByteBuffer.allocate(1), 0L).get();
            throw new RuntimeException("Test 3 failed: expected ClosedChannelException");
        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof ClosedChannelException)) {
                throw new RuntimeException("Test 3 failed: expected ClosedChannelException, got " + e, e);
            }
        }

        executor.shutdown();
    }
}
