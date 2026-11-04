/*
 * @test
 * @summary Witup-derived checks for Socket(Proxy): null proxy rejected, NO_PROXY and SOCKS proxy yield unconnected sockets
 * @run main SocketWitupTest
 */

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;

public class SocketWitupTest {

    public static void main(String[] args) throws Exception {
        testNullProxyThrowsIllegalArgument();
        testNoProxyCreatesUnconnectedSocket();
        testSocksProxyCreatesUnconnectedSocket();
    }

    static void testNullProxyThrowsIllegalArgument() {
        try {
            new Socket((Proxy) null);
            throw new AssertionError("Expected IllegalArgumentException for null proxy");
        } catch (IllegalArgumentException expected) {
        }
    }

    static void testNoProxyCreatesUnconnectedSocket() throws Exception {
        try (Socket s = new Socket(Proxy.NO_PROXY)) {
            if (s.isConnected()) {
                throw new AssertionError("Socket(NO_PROXY) must not be connected");
            }
            if (s.isBound()) {
                throw new AssertionError("Socket(NO_PROXY) must not be bound");
            }
            if (s.isClosed()) {
                throw new AssertionError("Socket(NO_PROXY) must not be closed");
            }
        }
    }

    static void testSocksProxyCreatesUnconnectedSocket() throws Exception {
        Proxy proxy = new Proxy(Proxy.Type.SOCKS,
                InetSocketAddress.createUnresolved("localhost", 1080));
        try (Socket s = new Socket(proxy)) {
            if (s.isConnected()) {
                throw new AssertionError("Socket(SOCKS proxy) must not connect at construction");
            }
            if (s.isClosed()) {
                throw new AssertionError("Socket(SOCKS proxy) must not be closed");
            }
        }
    }
}
