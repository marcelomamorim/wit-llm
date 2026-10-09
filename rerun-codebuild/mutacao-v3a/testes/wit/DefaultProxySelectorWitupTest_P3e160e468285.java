/*
 * @test
 * @summary Tests the connectFailed method of DefaultProxySelector for null arguments.
 * @run main DefaultProxySelectorWitupTest_P3e160e468285
 */

import sun.net.spi.DefaultProxySelector;
import java.net.URI;
import java.net.SocketAddress;
import java.io.IOException;

public class DefaultProxySelectorWitupTest_P3e160e468285 {

    public static void main(String[] args) {
        testConnectFailedNullArguments();
    }

    public static void testConnectFailedNullArguments() {
        DefaultProxySelector selector = new DefaultProxySelector();
        URI testUri = URI.create("http://example.com");
        SocketAddress testAddress = null;
        IOException testException = null;
        try {
            selector.connectFailed(testUri, null, new IOException());
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: connectFailed with null SocketAddress throws IllegalArgumentException");
        }
        try {
            selector.connectFailed(null, testAddress, new IOException());
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: connectFailed with null URI throws IllegalArgumentException");
        }
        try {
            selector.connectFailed(testUri, testAddress, null);
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: connectFailed with null IOException throws IllegalArgumentException");
        }
    }
}
