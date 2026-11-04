/*
 * @test
 * @summary Test sun.net.spi.DefaultProxySelector connectFailed method argument validation
 * @run main DefaultProxySelectorWitupTest
 */

import java.net.URI;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.io.IOException;
import sun.net.spi.DefaultProxySelector;

public class DefaultProxySelectorWitupTest {
    public static void main(String[] args) throws Exception {
        DefaultProxySelector dps = new DefaultProxySelector();

        // Test that connectFailed throws IllegalArgumentException if uri is null
        try {
            dps.connectFailed(null, new InetSocketAddress("localhost", 80), new IOException());
            throw new RuntimeException("Expected IllegalArgumentException for null uri");
        } catch (IllegalArgumentException expected) {
        }

        // Test that connectFailed throws IllegalArgumentException if SocketAddress is null
        try {
            dps.connectFailed(new URI("http://example.com"), null, new IOException());
            throw new RuntimeException("Expected IllegalArgumentException for null SocketAddress");
        } catch (IllegalArgumentException expected) {
        }

        // Test that connectFailed throws IllegalArgumentException if IOException is null
        try {
            dps.connectFailed(new URI("http://example.com"), new InetSocketAddress("localhost", 80), null);
            throw new RuntimeException("Expected IllegalArgumentException for null IOException");
        } catch (IllegalArgumentException expected) {
        }

        // Test that connectFailed does not throw when all arguments are non-null
        dps.connectFailed(new URI("http://example.com"), new InetSocketAddress("localhost", 80), new IOException());
    }
}
