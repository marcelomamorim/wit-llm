/*
 * @test
 * @summary Test getLocalAddress() method for AsynchronousServerSocketChannelImpl.
 * @run main AsynchronousServerSocketChannelImplWitupTest
 */

import java.io.IOException;
import java.net.SocketAddress;
import sun.nio.ch.AsynchronousServerSocketChannelImpl;

public class AsynchronousServerSocketChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetLocalAddressWhenOpen();
        testGetLocalAddressWhenClosed();
    }

    private static void testGetLocalAddressWhenOpen() throws Exception {
        // Setup: create an instance with a mock or real localAddress
        // Since constructor is not public, assume a subclass or factory method is used.
        // For testing, we simulate the behavior by subclassing.
        AsynchronousServerSocketChannelImpl channel = new AsynchronousServerSocketChannelImpl(null) {
            @Override
            void implClose() throws IOException {
                // no-op for test
            }
        };
        // Simulate open state
        channel.localAddress = new java.net.InetSocketAddress(12345);
        // Call method
        SocketAddress addr = channel.getLocalAddress();
        if (addr == null || !(addr instanceof java.net.InetSocketAddress)) {
            throw new AssertionError("Expected InetSocketAddress, got: " + addr);
        }
        System.out.println("getLocalAddress when open: " + addr);
    }

    private static void testGetLocalAddressWhenClosed() throws Exception {
        AsynchronousServerSocketChannelImpl channel = new AsynchronousServerSocketChannelImpl(null) {
            @Override
            void implClose() throws IOException {
                // no-op for test
            }
        };
        // Simulate closed state
        // Accessing private 'closed' field via reflection or assuming package access is complex;
        // Instead, simulate by setting closed to true via reflection or subclass.
        // For simplicity, we simulate by calling close() and then forcibly setting closed.
        // But since 'closed' is private, we simulate by subclassing.
        AsynchronousServerSocketChannelImpl closedChannel = new AsynchronousServerSocketChannelImpl(null) {
            {
                // set closed to true
                // can't access directly, so override isOpen() to return false
            }
            @Override
            public boolean isOpen() {
                return false;
            }
            @Override
            void implClose() throws IOException {
                // no-op
            }
        };
        try {
            closedChannel.getLocalAddress();
            throw new AssertionError("Expected ClosedChannelException");
        } catch (java.nio.channels.ClosedChannelException e) {
            System.out.println("getLocalAddress when closed: Caught expected exception.");
        }
    }
}
