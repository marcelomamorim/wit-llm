/*
 * @test
 * @summary Test sun.nio.ch.AsynchronousSocketChannelImpl.getLocalAddress() behavior
 * @run main AsynchronousSocketChannelImplWitupTest
 */

import java.net.SocketAddress;
import java.nio.channels.ClosedChannelException;
import java.io.IOException;
import sun.nio.ch.AsynchronousSocketChannelImpl;
import sun.nio.ch.AsynchronousChannelGroupImpl;
import java.net.InetSocketAddress;
import java.io.FileDescriptor;
import sun.net.Net;

public class AsynchronousSocketChannelImplWitupTest {

    // Minimal subclass to instantiate abstract class
    static class TestChannel extends AsynchronousSocketChannelImpl {
        TestChannel(AsynchronousChannelGroupImpl group) throws IOException {
            super(group);
        }
        TestChannel(AsynchronousChannelGroupImpl group, FileDescriptor fd, InetSocketAddress remote) throws IOException {
            super(group, fd, remote);
        }
    }

    public static void main(String[] args) throws Exception {
        // Create dummy group for constructor
        AsynchronousChannelGroupImpl group = new DummyGroup();

        // Test 1: getLocalAddress on open channel returns revealed local address
        TestChannel ch1 = new TestChannel(group);
        ch1.state = AsynchronousSocketChannelImpl.ST_CONNECTED; // simulate connected state
        InetSocketAddress local = new InetSocketAddress("127.0.0.1", 0);
        ch1.localAddress = local;
        SocketAddress addr = ch1.getLocalAddress();
        if (!local.equals(addr)) {
            throw new RuntimeException("Returned local address does not match expected");
        }

        // Test 2: getLocalAddress on closed channel throws ClosedChannelException
        TestChannel ch2 = new TestChannel(group);
        ch2.state = AsynchronousSocketChannelImpl.ST_CONNECTED;
        ch2.localAddress = new InetSocketAddress("127.0.0.1", 0);
        ch2.closed = true; // forcibly close
        boolean threw = false;
        try {
            ch2.getLocalAddress();
        } catch (ClosedChannelException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected ClosedChannelException not thrown");
        }
    }

    // Dummy AsynchronousChannelGroupImpl to satisfy constructor
    static class DummyGroup extends AsynchronousChannelGroupImpl {
        DummyGroup() {
            super(null);
        }
        @Override
        protected void implClose() throws IOException {
            // no-op
        }
    }
}
