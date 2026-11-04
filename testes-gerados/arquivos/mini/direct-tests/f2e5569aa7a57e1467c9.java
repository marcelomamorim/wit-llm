/*
 * @test
 * @summary Test java.net.Socket constructor with Proxy argument
 * @run main SocketWitupTest
 */

import java.net.Socket;
import java.net.Proxy;
import java.net.InetSocketAddress;
import java.net.Proxy.Type;
import java.security.SecurityManager;

public class SocketWitupTest {
    public static void main(String[] args) throws Exception {
        testNullProxyThrows();
        testNoProxyCreatesPlainSocket();
        testInvalidProxyTypeThrows();
        testSocksProxyCreatesSocksSocketImpl();
        testHttpProxyCreatesHttpConnectSocketImpl();
    }

    private static void testNullProxyThrows() {
        try {
            new Socket(null);
            throw new RuntimeException("Expected IllegalArgumentException for null proxy");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testNoProxyCreatesPlainSocket() {
        Socket s = new Socket(Proxy.NO_PROXY);
        if (s == null) {
            throw new RuntimeException("Socket creation with NO_PROXY returned null");
        }
        // We cannot access impl directly, but socket should be created without exception
    }

    private static void testInvalidProxyTypeThrows() {
        Proxy invalidProxy = new Proxy(Type.DIRECT, new InetSocketAddress("localhost", 80));
        try {
            new Socket(invalidProxy);
            throw new RuntimeException("Expected IllegalArgumentException for invalid proxy type");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testSocksProxyCreatesSocksSocketImpl() throws Exception {
        Proxy socksProxy = new Proxy(Type.SOCKS, new InetSocketAddress("127.0.0.1", 1080));
        Socket s = new Socket(socksProxy);
        if (s == null) {
            throw new RuntimeException("Socket creation with SOCKS proxy returned null");
        }
        // No direct access to impl, but no exception means success
    }

    private static void testHttpProxyCreatesHttpConnectSocketImpl() throws Exception {
        Proxy httpProxy = new Proxy(Type.HTTP, new InetSocketAddress("127.0.0.1", 8080));
        Socket s = new Socket(httpProxy);
        if (s == null) {
            throw new RuntimeException("Socket creation with HTTP proxy returned null");
        }
        // No direct access to impl, but no exception means success
    }
}
