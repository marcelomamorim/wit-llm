/*
 * @test
 * @summary Verify DefaultProxySelector.connectFailed rejects null arguments and accepts valid ones
 * @modules java.base/sun.net.spi
 * @run main DefaultProxySelectorWitupTest
 */

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URI;
import sun.net.spi.DefaultProxySelector;

public class DefaultProxySelectorWitupTest {

    public static void main(String[] args) throws Exception {
        DefaultProxySelector ps = new DefaultProxySelector();
        URI uri = URI.create("http://example.com/");
        SocketAddress sa = InetSocketAddress.createUnresolved("example.com", 80);
        IOException ioe = new IOException("boom");

        expectIAE("uri null", () -> ps.connectFailed(null, sa, ioe));
        expectIAE("sa null", () -> ps.connectFailed(uri, null, ioe));
        expectIAE("ioe null", () -> ps.connectFailed(uri, sa, null));
        expectIAE("all null", () -> ps.connectFailed(null, null, null));

        ps.connectFailed(uri, sa, ioe);
    }

    static void expectIAE(String name, Runnable r) {
        try {
            r.run();
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for: " + name);
    }
}
