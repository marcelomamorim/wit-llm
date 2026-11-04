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
import java.io.FileDescriptor;
import java.net.InetSocketAddress;
import java.util.Set;
import java.util.HashSet;

public class AsynchronousSocketChannelImplWitupTest {

    // Minimal subclass to allow instantiation for testing
    static class TestChannel extends AsynchronousSocketChannelImpl {
        private final Set<SocketOption<?>> options;

        TestChannel() throws IOException {
            super(new AsynchronousChannelGroupImpl() {
                public void shutdown() {}
                public boolean isShutdown() { return false; }
                public boolean isTerminated() { return false; }
                public boolean awaitTermination(long timeout, java.util.concurrent.TimeUnit unit) { return false; }
            });
            options = new HashSet<>();
            options.add(StandardSocketOptions.SO_REUSEADDR);
        }

        @Override
        public Set<SocketOption<?>> supportedOptions() {
            return options;
        }

        @Override
        public <T> T getOption(SocketOption<T> name) throws IOException {
            return super.getOption(name);
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
        SocketOption<Object> unsupportedOption = new SocketOption<Object>() {
            public String name() { return "UNSUPPORTED_OPTION"; }
            public Class<Object> type() { return Object.class; }
        };

        try {
            channel.getOption(unsupportedOption);
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException uoe) {
            // expected
        }

        // Test 3: Passing supported option SO_REUSEADDR returns Boolean (default false)
        Boolean reuseAddr = channel.getOption(StandardSocketOptions.SO_REUSEADDR);
        if (reuseAddr == null) {
            throw new RuntimeException("Expected non-null Boolean for SO_REUSEADDR");
        }

        // Test 4: Passing supported option (simulate Net.getSocketOption) - since Net.getSocketOption is native,
        // and we cannot mock it, we test only SO_REUSEADDR path which is emulated.

        System.out.println("All tests passed");
    }
}
