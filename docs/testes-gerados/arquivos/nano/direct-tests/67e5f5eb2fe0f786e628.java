/* @test    @summary Test connectFailed method of DefaultProxySelector for null arguments    @run main DefaultProxySelectorWitupTest */

import java.net.URI;
import java.net.SocketAddress;
import java.io.IOException;
import sun.net.spi.DefaultProxySelector;

public class DefaultProxySelectorWitupTest {

    public static void main(String[] args) {
        testConnectFailedWithNullURI();
        testConnectFailedWithNullSocketAddress();
        testConnectFailedWithNullIOException();
    }

    static void testConnectFailedWithNullURI() {
        try {
            DefaultProxySelector selector = new DefaultProxySelector();
            selector.connectFailed(null, new DummySocketAddress(), new IOException());
            throw new AssertionError("Expected IllegalArgumentException for null URI");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testConnectFailedWithNullSocketAddress() {
        try {
            DefaultProxySelector selector = new DefaultProxySelector();
            selector.connectFailed(URI.create("http://example.com"), null, new IOException());
            throw new AssertionError("Expected IllegalArgumentException for null SocketAddress");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testConnectFailedWithNullIOException() {
        try {
            DefaultProxySelector selector = new DefaultProxySelector();
            selector.connectFailed(URI.create("http://example.com"), new DummySocketAddress(), null);
            throw new AssertionError("Expected IllegalArgumentException for null IOException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static class DummySocketAddress extends SocketAddress {
        private static final long serialVersionUID = 1L;
    }
}
