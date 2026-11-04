/* @test    @summary Test getLocalAddress() method of AsynchronousSocketChannelImpl for open and closed states    @run main AsynchronousSocketChannelImplWitupTest */

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import sun.nio.ch.AsynchronousSocketChannelImpl;

public class AsynchronousSocketChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetLocalAddressWhenOpen();
        testGetLocalAddressWhenClosed();
    }

    static void testGetLocalAddressWhenOpen() throws Exception {
        // Create a dummy FileDescriptor (simulate open channel)
        FileDescriptor fd = new FileDescriptor();
        // Create a dummy local address
        InetSocketAddress localAddr = new InetSocketAddress("127.0.0.1", 0);
        // Instantiate the implementation with open state
        AsynchronousSocketChannelImpl channel = new AsynchronousSocketChannelImpl(
            new sun.nio.ch.AsynchronousChannelGroupImpl(Executors.newSingleThreadExecutor()),
            fd,
            null
        );
        // Use reflection to set localAddress (since it's protected)
        java.lang.reflect.Field localAddrField = sun.nio.ch.AsynchronousSocketChannelImpl.class.getDeclaredField("localAddress");
        localAddrField.setAccessible(true);
        localAddrField.set(channel, localAddr);
        // Call getLocalAddress
        SocketAddress result = channel.getLocalAddress();
        if (!(result instanceof InetSocketAddress)) {
            throw new AssertionError("Expected InetSocketAddress, got " + result.getClass());
        }
        InetSocketAddress inetResult = (InetSocketAddress) result;
        assert inetResult.getAddress().getHostAddress().equals("127.0.0.1") : "Unexpected address";
        System.out.println("testGetLocalAddressWhenOpen passed.");
    }

    static void testGetLocalAddressWhenClosed() throws Exception {
        // Create a dummy FileDescriptor
        FileDescriptor fd = new FileDescriptor();
        // Instantiate the implementation
        AsynchronousSocketChannelImpl channel = new AsynchronousSocketChannelImpl(
            new sun.nio.ch.AsynchronousChannelGroupImpl(Executors.newSingleThreadExecutor()),
            fd,
            null
        );
        // Mark channel as closed
        java.lang.reflect.Field closedField = sun.nio.ch.AsynchronousSocketChannelImpl.class.getDeclaredField("closed");
        closedField.setAccessible(true);
        closedField.setBoolean(channel, true);
        // Expect ClosedChannelException
        boolean exceptionThrown = false;
        try {
            channel.getLocalAddress();
        } catch (ClosedChannelException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected ClosedChannelException";
        System.out.println("testGetLocalAddressWhenClosed passed.");
    }
}
