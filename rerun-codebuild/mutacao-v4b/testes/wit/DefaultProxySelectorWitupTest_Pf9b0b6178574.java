/* @test
   @summary Testa o método connectFailed de DefaultProxySelector para verificar o comportamento com argumentos nulos.
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
        DefaultProxySelector dps = new DefaultProxySelector();
        URI uri = URI.create("http://example.com");
        SocketAddress sa = null;
        IOException ioe = null;
        try {
            dps.connectFailed(uri, sa, new IOException());
            throw new AssertionError("Expected IllegalArgumentException for null SocketAddress");
        } catch (IllegalArgumentException e) {
            // expected
        }
        sa = new java.net.InetSocketAddress(80);
        try {
            dps.connectFailed(null, sa, new IOException());
            throw new AssertionError("Expected IllegalArgumentException for null URI");
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            dps.connectFailed(uri, sa, null);
            throw new AssertionError("Expected IllegalArgumentException for null IOException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
