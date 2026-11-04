/* @test    @summary Test Socket constructor with Proxy argument    @run main SocketWitupTest */

import java.net.Socket;
import java.net.Proxy;
import java.net.InetSocketAddress;
import java.security.Security;

public class SocketWitupTest {

    public static void main(String[] args) {
        testSocketWithNoProxy();
        testSocketWithSocksProxy();
        testSocketWithHttpProxy();
        testSocketWithInvalidProxy();
        testSocketWithNullProxy();
    }

    static void testSocketWithNoProxy() {
        try {
            Proxy proxy = Proxy.NO_PROXY;
            Socket socket = new Socket(proxy);
            assert socket != null : "Socket with NO_PROXY should not be null";
        } catch (Exception e) {
            throw new AssertionError("Failed to create socket with NO_PROXY", e);
        }
    }

    static void testSocketWithSocksProxy() {
        try {
            Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 1080));
            Socket socket = new Socket(proxy);
            assert socket != null : "Socket with SOCKS proxy should not be null";
        } catch (Exception e) {
            throw new AssertionError("Failed to create socket with SOCKS proxy", e);
        }
    }

    static void testSocketWithHttpProxy() {
        try {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8080));
            Socket socket = new Socket(proxy);
            assert socket != null : "Socket with HTTP proxy should not be null";
        } catch (Exception e) {
            throw new AssertionError("Failed to create socket with HTTP proxy", e);
        }
    }

    static void testSocketWithInvalidProxy() {
        try {
            Proxy proxy = new Proxy(null, null);
            new Socket(proxy);
            throw new AssertionError("Creating socket with invalid proxy should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for invalid proxy", e);
        }
    }

    static void testSocketWithNullProxy() {
        try {
            new Socket((Proxy) null);
            throw new AssertionError("Creating socket with null proxy should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for null proxy", e);
        }
    }
}
