/*
 * @test
 * @summary Checks public observable behavior of SocketChannel.bind through SocketChannelImpl
 * @run main SocketChannelImplWitupTest
 */

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.AlreadyBoundException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SocketChannel;
import java.nio.channels.UnsupportedAddressTypeException;

public class SocketChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testBindNullReturnsSameChannel();
        testBindLoopbackReturnsSameChannelAndSetsLocalAddress();
        testSecondBindThrowsAlreadyBound();
        testBindAfterCloseThrowsClosedChannel();
        testUnsupportedAddressTypeThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testBindNullReturnsSameChannel() throws Exception {
        try (SocketChannel sc = SocketChannel.open()) {
            SocketChannel result = sc.bind(null);
            check(result == sc, 'bind(null) should return the same channel');
            check(sc.getLocalAddress() != null, 'bind(null) should set a local address');
        }
    }

    static void testBindLoopbackReturnsSameChannelAndSetsLocalAddress() throws Exception {
        try (SocketChannel sc = SocketChannel.open()) {
            InetSocketAddress target =
                new InetSocketAddress(InetAddress.getLoopbackAddress(), 0);
            SocketChannel result = sc.bind(target);
            check(result == sc, 'bind(addr) should return the same channel');
            SocketAddress local = sc.getLocalAddress();
            check(local instanceof InetSocketAddress, 'local address should be an InetSocketAddress');
            InetSocketAddress isa = (InetSocketAddress) local;
            check(isa.getPort() > 0, 'bound port should be assigned');
        }
    }

    static void testSecondBindThrowsAlreadyBound() throws Exception {
        try (SocketChannel sc = SocketChannel.open()) {
            sc.bind(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0));
            try {
                sc.bind(null);
                throw new RuntimeException('second bind should throw AlreadyBoundException');
            } catch (AlreadyBoundException expected) {
                // expected
            }
        }
    }

    static void testBindAfterCloseThrowsClosedChannel() throws Exception {
        SocketChannel sc = SocketChannel.open();
        sc.close();
        try {
            sc.bind(null);
            throw new RuntimeException('bind on closed channel should throw ClosedChannelException');
        } catch (ClosedChannelException expected) {
            // expected
        }
    }

    static void testUnsupportedAddressTypeThrows() throws Exception {
        try (SocketChannel sc = SocketChannel.open()) {
            SocketAddress unsupported = new SocketAddress() { };
            try {
                sc.bind(unsupported);
                throw new RuntimeException('unsupported address should throw UnsupportedAddressTypeException');
            } catch (UnsupportedAddressTypeException expected) {
                // expected
            }
        }
    }
}
