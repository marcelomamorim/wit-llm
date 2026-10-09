/* @test    @summary Test connectFailed method of DefaultProxySelector for null arguments    @run main DefaultProxySelectorWitupTest_P1e8b6200f80f */

import java.net.URI;
import java.net.SocketAddress;
import java.io.IOException;
import sun.net.spi.DefaultProxySelector;

public class DefaultProxySelectorWitupTest_P1e8b6200f80f {

    public static void main(String[] args) {
        testConnectFailedNullArguments();
        System.out.println("PASS");
    }

    static void testConnectFailedNullArguments() {
        DefaultProxySelector dps = new DefaultProxySelector();
        try {
            dps.connectFailed(null, null, null);
            throw new AssertionError("Expected IllegalArgumentException for null arguments");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
        try {
            dps.connectFailed(URI.create("http://example.com"), null, new IOException());
            throw new AssertionError("Expected IllegalArgumentException for null SocketAddress");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
        try {
            dps.connectFailed(URI.create("http://example.com"), InetSocketAddress.createUnresolved("localhost", 80), null);
            // Should not throw
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
