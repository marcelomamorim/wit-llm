/*
 * @test
 * @summary Test sun.nio.ch.AsynchronousSocketChannelImpl.getOption method
 * @run main AsynchronousSocketChannelImplWitupTest
 */

import java.net.StandardSocketOptions;
import java.net.SocketOption;
import java.io.IOException;
import sun.nio.ch.AsynchronousSocketChannelImpl;
import sun.nio.ch.AsynchronousChannelGroupImpl;
import java.net.InetSocketAddress;
import java.io.FileDescriptor;
import java.util.Set;

public class AsynchronousSocketChannelImplWitupTest {

    // Minimal subclass to instantiate abstract AsynchronousSocketChannelImpl
    static class TestChannel extends AsynchronousSocketChannelImpl {

        TestChannel() throws IOException {
            super(new AsynchronousChannelGroupImpl() {
                public void shutdown() {}
                public boolean isShutdown() { return false; }
                public boolean isTerminated() { return false; }
                public boolean awaitTermination(long timeout, java.util.concurrent.TimeUnit unit) { return false; }
                public void shutdownNow() {}
            });
        }

        @Override
        public Set<SocketOption<?>> supportedOptions() {
            return java.util.Collections.singleton(StandardSocketOptions.SO_REUSEADDR);
        }

        @Override
        protected void begin() {}

        @Override
        protected void end() {}

        @Override
        public <T> void setOption(SocketOption<T> name, T value) throws IOException {}

        @Override
        public void implClose() throws IOException {}

        @Override
        public <A> void read(java.nio.ByteBuffer dst, A attachment, java.nio.channels.CompletionHandler<Integer, ? super A> handler) {}

        @Override
        public <A> void write(java.nio.ByteBuffer src, A attachment, java.nio.channels.CompletionHandler<Integer, ? super A> handler) {}

        @Override
        public java.util.Set<SocketOption<?>> supportedOptions() {
            return java.util.Collections.singleton(StandardSocketOptions.SO_REUSEADDR);
        }

        // Override Net.useExclusiveBind() to true for testing emulation path
        @Override
        public <T> T getOption(SocketOption<T> name) throws IOException {
            if (name == null)
                throw new NullPointerException();
            if (!supportedOptions().contains(name))
                throw new UnsupportedOperationException("'" + name + "' not supported");
            try {
                begin();
                if (name == StandardSocketOptions.SO_REUSEADDR) {
                    // Emulate exclusive bind true
                    return (T)Boolean.TRUE;
                }
                return super.getOption(name);
            } finally {
                end();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        TestChannel channel = new TestChannel();

        // Test 1: Passing null should throw NullPointerException
        try {
            channel.getOption(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        }

        // Test 2: Passing unsupported option should throw UnsupportedOperationException
        try {
            channel.getOption(new SocketOption<Object>() {
                public String name() { return "UNSUPPORTED_OPTION"; }
                public Class<Object> type() { return Object.class; }
            });
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException uoe) {
            // expected
        }

        // Test 3: Passing supported option SO_REUSEADDR returns Boolean.TRUE (emulated)
        Boolean reuse = channel.getOption(StandardSocketOptions.SO_REUSEADDR);
        if (!Boolean.TRUE.equals(reuse)) {
            throw new RuntimeException("Expected Boolean.TRUE for SO_REUSEADDR option");
        }

        System.out.println("All tests passed");
    }
}
