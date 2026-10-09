/* @test    @summary Test DefaultProxySelector.connectFailed method for null arguments    @run main DefaultProxySelectorWitupTest_P3e160e468285 */

import java.net.URI;
import java.net.SocketAddress;
import java.io.IOException;
import sun.net.spi.DefaultProxySelector;

public class DefaultProxySelectorWitupTest_P3e160e468285 {

    public static void main(String[] args) {
        testConnectFailedNullArguments();
        testConnectFailedValidArguments();
    }

    static void testConnectFailedNullArguments() {
        DefaultProxySelector dps = new DefaultProxySelector();
        boolean exceptionThrown = false;
        try {
            dps.connectFailed(null, null, null);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for null arguments");
        }
        exceptionThrown = false;
        try {
            dps.connectFailed(URI.create("http://example.com"), null, new IOException());
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for null SocketAddress");
        }
        exceptionThrown = false;
        try {
            dps.connectFailed(URI.create("http://example.com"), InetSocketAddress.createUnresolved("host", 80), null);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for null IOException");
        }
    }

    static void testConnectFailedValidArguments() {
        DefaultProxySelector dps = new DefaultProxySelector();
        URI uri = URI.create("http://example.com");
        SocketAddress sa = InetSocketAddress.createUnresolved("host", 80);
        IOException ioe = new IOException("Test exception");
        // Should not throw
        dps.connectFailed(uri, sa, ioe);
    }
}
