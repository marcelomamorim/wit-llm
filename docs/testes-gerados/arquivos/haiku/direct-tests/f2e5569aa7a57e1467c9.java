/*
 * @test
 * @summary Socket(Proxy) rejects null/DIRECT proxies and accepts Proxy.NO_PROXY
 * @run main SocketWitupTest
 */

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;

public class SocketWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) throws Exception {
        testNullProxyThrowsIAE();
        testNoProxyCreatesUnconnectedSocket();
        testDirectProxyThrowsIAE();
    }

    static void testNullProxyThrowsIAE() {
        boolean thrown = false;
        try {
            Socket s = new Socket((Proxy) null);
            s.close();
        } catch (IllegalArgumentException e) {
            thrown = true;
        } catch (java.io.IOException e) {
            throw new RuntimeException("unexpected IOException", e);
        }
        check(thrown, "null proxy must throw IllegalArgumentException");
    }

    static void testNoProxyCreatesUnconnectedSocket() throws Exception {
        Socket s = new Socket(Proxy.NO_PROXY);
        try {
            check(!s.isConnected(), "socket with NO_PROXY must not be connected");
            check(!s.isClosed(), "socket with NO_PROXY must not be closed");
            check(s.getPort() == 0, "unconnected socket must report port 0");
        } finally {
            s.close();
        }
        check(s.isClosed(), "socket must be closed after close()");
    }

    static void testDirectProxyThrowsIAE() {
        Proxy direct = new Proxy(Proxy.Type.DIRECT,
                new InetSocketAddress(InetAddress.getLoopbackAddress(), 1080));
        boolean thrown = false;
        try {
            Socket s = new Socket(direct);
            s.close();
        } catch (IllegalArgumentException e) {
            thrown = true;
        } catch (java.io.IOException e) {
            throw new RuntimeException("unexpected IOException", e);
        }
        check(thrown, "DIRECT proxy other than NO_PROXY must throw IllegalArgumentException");
    }
}
