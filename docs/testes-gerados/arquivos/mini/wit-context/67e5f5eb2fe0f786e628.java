/*
 * @test
 * @summary Test sun.net.spi.DefaultProxySelector.connectFailed method for null argument validation
 * @run main DefaultProxySelectorWitupTest
 */

import java.net.URI;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.io.IOException;
import sun.net.spi.DefaultProxySelector;

public class DefaultProxySelectorWitupTest {
    public static void main(String[] args) {
        DefaultProxySelector selector = new DefaultProxySelector();

        // Test that connectFailed throws IllegalArgumentException if uri is null
        try {
            selector.connectFailed(null, new InetSocketAddress("localhost", 80), new IOException());
            throw new RuntimeException("Expected IllegalArgumentException for null uri");
        } catch (IllegalArgumentException expected) {
        }

        // Test that connectFailed throws IllegalArgumentException if SocketAddress is null
        try {
            selector.connectFailed(URI.create("http://example.com"), null, new IOException());
            throw new RuntimeException("Expected IllegalArgumentException for null SocketAddress");
        } catch (IllegalArgumentException expected) {
        }

        // Test that connectFailed throws IllegalArgumentException if IOException is null
        try {
            selector.connectFailed(URI.create("http://example.com"), new InetSocketAddress("localhost", 80), null);
            throw new RuntimeException("Expected IllegalArgumentException for null IOException");
        } catch (IllegalArgumentException expected) {
        }

        // Test that connectFailed does not throw for all non-null arguments
        try {
            selector.connectFailed(URI.create("http://example.com"), new InetSocketAddress("localhost", 80), new IOException());
        } catch (Exception e) {
            throw new RuntimeException("Did not expect exception for valid arguments", e);
        }
    }
}
