/* @test    @summary Test getRemoteAddress() method of AsynchronousSocketChannelImpl for open and closed states    @run main AsynchronousSocketChannelImplWitupTest */

import sun.nio.ch.AsynchronousSocketChannelImpl;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.io.IOException;
import java.nio.channels.ClosedChannelException;

public class AsynchronousSocketChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetRemoteAddressOpen();
        testGetRemoteAddressClosed();
    }

    static void testGetRemoteAddressOpen() throws Exception {
        // Create a dummy implementation with a remoteAddress set
        AsynchronousSocketChannelImpl channel = new AsynchronousSocketChannelImpl(null) {
            {
                remoteAddress = new InetSocketAddress("127.0.0.1", 8080);
            }
        };
        // Ensure channel is open
        if (!channel.isOpen()) {
            throw new AssertionError("Channel should be open");
        }
        // Call getRemoteAddress and verify
        SocketAddress addr = channel.getRemoteAddress();
        if (!(addr instanceof InetSocketAddress)) {
            throw new AssertionError("Expected InetSocketAddress");
        }
        InetSocketAddress inetAddr = (InetSocketAddress) addr;
        if (!inetAddr.getAddress().getHostAddress().equals("127.0.0.1") || inetAddr.getPort() != 8080) {
            throw new AssertionError("Remote address mismatch");
        }
        System.out.println("testGetRemoteAddressOpen passed");
    }

    static void testGetRemoteAddressClosed() throws Exception {
        // Create a dummy implementation with isOpen returning false
        AsynchronousSocketChannelImpl channel = new AsynchronousSocketChannelImpl(null) {
            {
                remoteAddress = new InetSocketAddress("127.0.0.1", 8080);
            }
            @Override
            public boolean isOpen() {
                return false;
            }
        };
        try {
            channel.getRemoteAddress();
            throw new AssertionError("Expected ClosedChannelException");
        } catch (ClosedChannelException e) {
            // Expected exception
        }
        System.out.println("testGetRemoteAddressClosed passed");
    }
}
