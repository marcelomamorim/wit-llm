/*
 * @test
 * @summary Verifies observable behavior of SocketChannelImpl.bind through the public SocketChannel API
 * @run main SocketChannelImplWitupTest
 */

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.AlreadyBoundException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SocketChannel;

public class SocketChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        bindWithExplicitLoopbackAddressAssignsLocalPort();
        bindWithNullAddressReturnsSameChannelAndBindsWildcard();
        bindTwiceThrowsAlreadyBound();
        bindOnClosedChannelThrowsClosedChannel();
    }

    static void bindWithExplicitLoopbackAddressAssignsLocalPort() throws Exception {
        SocketChannel sc = SocketChannel.open();
        try {
            InetSocketAddress req = new InetSocketAddress(InetAddress.getLoopbackAddress(), 0);
            SocketChannel ret = sc.bind(req);
            if (ret != sc) {
                throw new RuntimeException("bind must return this channel");
            }
            InetSocketAddress la = (InetSocketAddress) sc.getLocalAddress();
            if (la == null || la.getPort() == 0) {
                throw new RuntimeException("local port not assigned after bind");
            }
        } finally {
            sc.close();
        }
    }

    static void bindWithNullAddressReturnsSameChannelAndBindsWildcard() throws Exception {
        SocketChannel sc = SocketChannel.open();
        try {
            SocketChannel ret = sc.bind(null);
            if (ret != sc) {
                throw new RuntimeException("bind(null) must return this channel");
            }
            InetSocketAddress la = (InetSocketAddress) sc.getLocalAddress();
            if (la == null || la.getPort() == 0) {
                throw new RuntimeException("local port not assigned after bind(null)");
            }
        } finally {
            sc.close();
        }
    }

    static void bindTwiceThrowsAlreadyBound() throws Exception {
        SocketChannel sc = SocketChannel.open();
        try {
            sc.bind(null);
            boolean thrown = false;
            try {
                sc.bind(null);
            } catch (AlreadyBoundException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("second bind must throw AlreadyBoundException");
            }
        } finally {
            sc.close();
        }
    }

    static void bindOnClosedChannelThrowsClosedChannel() throws Exception {
        SocketChannel sc = SocketChannel.open();
        sc.close();
        boolean thrown = false;
        try {
            sc.bind(null);
        } catch (ClosedChannelException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("bind on closed channel must throw ClosedChannelException");
        }
    }
}
