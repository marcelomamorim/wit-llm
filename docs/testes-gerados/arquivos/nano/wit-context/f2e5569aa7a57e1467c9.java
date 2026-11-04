/*
 * @test
 * @summary Tests the Socket constructor with Proxy parameter for various inputs.
 * @run main SocketWitupTest
 */

import java.net.Socket;
import java.net.Proxy;
import java.net.InetSocketAddress;
import java.security.Security;

public class SocketWitupTest {

    public static void main(String[] args) {
        testSocketConstructorWithNullProxy();
        testSocketConstructorWithInvalidProxyType();
        testSocketConstructorWithValidProxy();
    }

    private static void testSocketConstructorWithNullProxy() {
        try {
            new Socket((Proxy) null);
            System.out.println("FAIL: Expected IllegalArgumentException for null proxy");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for null proxy");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    private static void testSocketConstructorWithInvalidProxyType() {
        try {
            Proxy invalidProxy = new Proxy(Proxy.Type.DIRECT, new InetSocketAddress("localhost", 80));
            new Socket(invalidProxy);
            System.out.println("FAIL: Expected IllegalArgumentException for invalid proxy type");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for invalid proxy type");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    private static void testSocketConstructorWithValidProxy() {
        try {
            Proxy proxy = Proxy.NO_PROXY;
            Socket socket = new Socket(proxy);
            if (socket != null) {
                System.out.println("PASS: Successfully created socket with Proxy.NO_PROXY");
            } else {
                System.out.println("FAIL: Socket creation returned null");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception during valid proxy socket creation: " + e);
        }
    }
}
