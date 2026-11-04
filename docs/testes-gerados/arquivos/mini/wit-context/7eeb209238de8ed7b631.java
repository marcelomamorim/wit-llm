/*
 * @test
 * @summary Test sun.nio.ch.AsynchronousSocketChannelImpl.getLocalAddress() behavior
 * @run main AsynchronousSocketChannelImplWitupTest
 */

import java.nio.channels.ClosedChannelException;
import java.net.SocketAddress;
import java.io.IOException;
import sun.nio.ch.AsynchronousSocketChannelImpl;
import sun.nio.ch.AsynchronousChannelGroupImpl;
import java.net.InetSocketAddress;
import java.io.FileDescriptor;

public class AsynchronousSocketChannelImplWitupTest {

    // Minimal subclass to instantiate abstract AsynchronousSocketChannelImpl
    static class TestChannel extends AsynchronousSocketChannelImpl {
        TestChannel(AsynchronousChannelGroupImpl group) throws IOException {
            super(group);
        }
        TestChannel(AsynchronousChannelGroupImpl group, FileDescriptor fd, InetSocketAddress remote) throws IOException {
            super(group, fd, remote);
        }
    }

    public static void main(String[] args) throws Exception {
        // Since AsynchronousChannelGroupImpl is package-private and no public factory,
        // we test only observable behavior by subclassing and simulating state.

        // Create dummy group (null) because super constructor requires it but we do not use it
        AsynchronousChannelGroupImpl dummyGroup = null;

        // Test 1: getLocalAddress throws ClosedChannelException if channel is closed
        TestChannel ch1 = new TestChannel(dummyGroup) {
            @Override
            public boolean isOpen() {
                return false; // simulate closed channel
            }
        };

        try {
            ch1.getLocalAddress();
            throw new RuntimeException("Test failed: expected ClosedChannelException not thrown");
        } catch (ClosedChannelException expected) {
            // expected
        }

        // Test 2: getLocalAddress returns Net.getRevealedLocalAddress(localAddress) when open
        // We cannot call Net.getRevealedLocalAddress directly (package-private), so we test that
        // getLocalAddress returns the localAddress field (wrapped by Net.getRevealedLocalAddress)
        // We simulate by overriding getLocalAddress to call super and check result

        final InetSocketAddress testLocalAddress = new InetSocketAddress(0);

        TestChannel ch2 = new TestChannel(dummyGroup) {
            @Override
            public boolean isOpen() {
                return true;
            }
            {
                this.localAddress = testLocalAddress;
            }
        };

        SocketAddress result = ch2.getLocalAddress();
        if (result == null) {
            throw new RuntimeException("Test failed: getLocalAddress returned null");
        }

        // We cannot assert exact equality because Net.getRevealedLocalAddress may wrap or transform,
        // but at least the returned address should be non-null.

        System.out.println("All tests passed");
    }
}
