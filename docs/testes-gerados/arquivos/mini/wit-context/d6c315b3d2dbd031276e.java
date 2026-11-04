/*
 * @test
 * @summary Test sun.nio.ch.AsynchronousFileChannelImpl.write(ByteBuffer, long) method
 * @run main AsynchronousFileChannelImplWitupTest
 */

import sun.nio.ch.AsynchronousFileChannelImpl;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.io.FileDescriptor;
import java.util.concurrent.*;

public class AsynchronousFileChannelImplWitupTest {

    // Minimal subclass to allow instantiation of abstract class
    static class TestAsyncFileChannel extends AsynchronousFileChannelImpl {

        protected TestAsyncFileChannel(FileDescriptor fdObj, boolean reading, boolean writing, ExecutorService executor) {
            super(fdObj, reading, writing, executor);
        }

        @Override
        public <A> Future<FileLock> implLock(long position, long size, boolean shared, A attachment,
                                            CompletionHandler<FileLock, ? super A> handler) {
            // Dummy implementation for testing
            return null;
        }

        @Override
        public <A> Future<Integer> implWrite(ByteBuffer src, long position, A attachment,
                                             CompletionHandler<Integer, ? super A> handler) {
            if (handler == null) {
                throw new NullPointerException("CompletionHandler is null");
            }
            // Dummy Future to satisfy return type
            return new Future<Integer>() {
                public boolean cancel(boolean mayInterruptIfRunning) { return false; }
                public boolean isCancelled() { return false; }
                public boolean isDone() { return true; }
                public Integer get() { return 0; }
                public Integer get(long timeout, java.util.concurrent.TimeUnit unit) { return 0; }
            };
        }
    }

    public static void main(String[] args) throws Exception {
        // Setup dummy FileDescriptor and ExecutorService
        FileDescriptor fd = new FileDescriptor();
        ExecutorService executor = Executors.newSingleThreadExecutor();

        TestAsyncFileChannel channel = new TestAsyncFileChannel(fd, true, true, executor);

        // Test normal write call returns a Future
        ByteBuffer buffer = ByteBuffer.allocate(1);
        Future<Integer> future = channel.write(buffer, 0L);
        if (future == null) {
            throw new RuntimeException("write() returned null Future");
        }

        // Test that write with null CompletionHandler throws NullPointerException
        boolean npeThrown = false;
        try {
            channel.implWrite(buffer, 0L, null, null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException when CompletionHandler is null");
        }

        executor.shutdown();
    }
}
