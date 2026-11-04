/*
 * @test
 * @summary Test connectFailed method for null arguments.
 * @run main DefaultProxySelectorWitupTest
 */

import sun.net.spi.DefaultProxySelector;
import java.net.URI;
import java.net.SocketAddress;
import java.io.IOException;

public class DefaultProxySelectorWitupTest {

    public static void main(String[] args) {
        testConnectFailedWithNullArguments();
    }

    public static void testConnectFailedWithNullArguments() {
        DefaultProxySelector dps = new DefaultProxySelector();
        try {
            dps.connectFailed(null, new SocketAddress() {}, new IOException());
            throw new AssertionError("Expected IllegalArgumentException for null URI");
        } catch (IllegalArgumentException e) {
            // Expected
        }
        try {
            dps.connectFailed(URI.create("http://example.com"), null, new IOException());
            throw new AssertionError("Expected IllegalArgumentException for null SocketAddress");
        } catch (IllegalArgumentException e) {
            // Expected
        }
        try {
            dps.connectFailed(URI.create("http://example.com"), new SocketAddress() {}, null);
            throw new AssertionError("Expected IllegalArgumentException for null IOException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }
}
