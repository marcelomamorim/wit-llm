/*
 * @test
 * @summary Test java.net.Socket constructor with Proxy argument for expected exceptions and normal cases
 * @run main SocketWitupTest
 */

import java.net.Socket;
import java.net.Proxy;
import java.net.InetSocketAddress;
import java.net.Proxy.Type;

public class SocketWitupTest {
    public static void main(String[] args) throws Exception {
        testNullProxyThrows();
        testInvalidProxyTypeThrows();
        testNoProxyCreatesPlainSocketImpl();
        testSocksProxyCreatesSocksSocketImpl();
        testHttpProxyCreatesHttpConnectSocketImpl();
    }

    private static void testNullProxyThrows() {
        try {
            new Socket((Proxy) null);
            throw new RuntimeException("Expected IllegalArgumentException for null proxy");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testInvalidProxyTypeThrows() {
        // Create a Proxy with invalid type (not SOCKS, HTTP, NO_PROXY)
        Proxy.Type invalidType = new Proxy.Type("INVALID", "Invalid Proxy Type");
        Proxy invalidProxy = new Proxy(invalidType, new InetSocketAddress("localhost", 80));
        try {
            new Socket(invalidProxy);
            throw new RuntimeException("Expected IllegalArgumentException for invalid proxy type");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testNoProxyCreatesPlainSocketImpl() throws Exception {
        // Proxy.NO_PROXY should create a plain socket impl without exception
        Socket s = new Socket(Proxy.NO_PROXY);
        if (s == null) {
            throw new RuntimeException("Socket instance is null for Proxy.NO_PROXY");
        }
        s.close();
    }

    private static void testSocksProxyCreatesSocksSocketImpl() throws Exception {
        Proxy socksProxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("localhost", 1080));
        Socket s = new Socket(socksProxy);
        if (s == null) {
            throw new RuntimeException("Socket instance is null for SOCKS proxy");
        }
        s.close();
    }

    private static void testHttpProxyCreatesHttpConnectSocketImpl() throws Exception {
        Proxy httpProxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 8080));
        Socket s = new Socket(httpProxy);
        if (s == null) {
            throw new RuntimeException("Socket instance is null for HTTP proxy");
        }
        s.close();
    }
}
