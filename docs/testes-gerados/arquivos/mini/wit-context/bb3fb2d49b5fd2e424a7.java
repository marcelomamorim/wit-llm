/*
 * @test
 * @summary Test sun.nio.ch.AsynchronousSocketChannelImpl.getRemoteAddress() behavior
 * @run main AsynchronousSocketChannelImplWitupTest
 */

import java.nio.channels.ClosedChannelException;
import java.net.SocketAddress;
import java.io.IOException;
import sun.nio.ch.AsynchronousSocketChannelImpl;
import sun.nio.ch.AsynchronousChannelGroupImpl;
import java.io.FileDescriptor;
import java.net.InetSocketAddress;

public class AsynchronousSocketChannelImplWitupTest {

    // Minimal subclass to instantiate abstract AsynchronousSocketChannelImpl
    static class TestChannel extends AsynchronousSocketChannelImpl {

        TestChannel(AsynchronousChannelGroupImpl group) throws IOException {
            super(group);
        }

        TestChannel(AsynchronousChannelGroupImpl group, FileDescriptor fd, InetSocketAddress remote) throws IOException {
            super(group, fd, remote);
        }

        // Expose method to forcibly close channel for testing
        void closeChannel() {
            closed = true;
        }
    }

    public static void main(String[] args) throws Exception {
        // Create dummy group for constructor
        AsynchronousChannelGroupImpl dummyGroup = new AsynchronousChannelGroupImpl() {
            @Override
            public void shutdown() {}
            @Override
            public boolean isShutdown() { return false; }
            @Override
            public boolean isTerminated() { return false; }
            @Override
            public boolean awaitTermination(long timeout, java.util.concurrent.TimeUnit unit) { return false; }
        };

        // Test 1: getRemoteAddress throws ClosedChannelException if closed
        TestChannel ch1 = new TestChannel(dummyGroup);
        ch1.closeChannel();
        try {
            ch1.getRemoteAddress();
            throw new RuntimeException("Expected ClosedChannelException not thrown");
        } catch (ClosedChannelException expected) {
            // expected
        }

        // Test 2: getRemoteAddress returns remoteAddress if open
        // Use constructor with fd and remote address
        FileDescriptor fd = new FileDescriptor();
        InetSocketAddress remote = new InetSocketAddress("127.0.0.1", 12345);
        TestChannel ch2 = new TestChannel(dummyGroup, fd, remote);
        SocketAddress result = ch2.getRemoteAddress();
        if (!remote.equals(result)) {
            throw new RuntimeException("getRemoteAddress returned unexpected value");
        }

        // Test 3: getRemoteAddress returns null if remoteAddress is null and open
        TestChannel ch3 = new TestChannel(dummyGroup);
        // forcibly set state to connected and remoteAddress to null
        ch3.state = ST_CONNECTED;
        ch3.remoteAddress = null;
        // channel is open by default
        SocketAddress res3 = ch3.getRemoteAddress();
        if (res3 != null) {
            throw new RuntimeException("Expected null remoteAddress");
        }
    }

    // Constants from AsynchronousSocketChannelImpl
    private static final int ST_CONNECTED = 2;
}
