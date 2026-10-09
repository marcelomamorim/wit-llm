/* @test
   @summary Verifies that connectFailed method throws IllegalArgumentException for null arguments.
   @run main DefaultProxySelectorWitupTest_Pf9b0b6178574
 */
import java.net.URI;
import java.net.SocketAddress;
import java.io.IOException;
import sun.net.spi.DefaultProxySelector;

public class DefaultProxySelectorWitupTest_Pf9b0b6178574 {
    public static void main(String[] args) {
        testConnectFailedNullArguments();
    }

    private static void testConnectFailedNullArguments() {
        DefaultProxySelector selector = new DefaultProxySelector();
        URI validURI = URI.create("http://example.com");
        SocketAddress validAddress = null;
        IOException ioe = new IOException();
        try {
            selector.connectFailed(null, validAddress, ioe);
            throw new AssertionError("Expected IllegalArgumentException for null URI");
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            selector.connectFailed(validURI, null, ioe);
            throw new AssertionError("Expected IllegalArgumentException for null SocketAddress");
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            selector.connectFailed(validURI, validAddress, null);
            throw new AssertionError("Expected IllegalArgumentException for null IOException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
