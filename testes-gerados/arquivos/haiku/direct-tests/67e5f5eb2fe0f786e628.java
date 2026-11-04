/*
 * @test
 * @summary connectFailed must reject null arguments and accept non-null arguments without error
 * @modules java.base/sun.net.spi
 * @run main DefaultProxySelectorWitupTest
 */

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URI;
import sun.net.spi.DefaultProxySelector;

public class DefaultProxySelectorWitupTest {

    interface Action {
        void run() throws Exception;
    }

    static void expectIllegalArgument(Action action) {
        try {
            action.run();
        } catch (IllegalArgumentException e) {
            return;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception: " + e, e);
        }
        throw new RuntimeException("Expected IllegalArgumentException was not thrown");
    }

    public static void main(String[] args) throws Exception {
        DefaultProxySelector ps = new DefaultProxySelector();
        URI uri = URI.create("http://example.com/");
        SocketAddress sa = InetSocketAddress.createUnresolved("example.com", 80);
        IOException ioe = new IOException("boom");

        ps.connectFailed(uri, sa, ioe);

        expectIllegalArgument(() -> ps.connectFailed(null, sa, ioe));
        expectIllegalArgument(() -> ps.connectFailed(uri, null, ioe));
        expectIllegalArgument(() -> ps.connectFailed(uri, sa, null));
        expectIllegalArgument(() -> ps.connectFailed(null, null, null));
    }
}
