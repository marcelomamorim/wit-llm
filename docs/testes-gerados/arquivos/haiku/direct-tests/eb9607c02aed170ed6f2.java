/*
 * @test
 * @summary Verifies that SelectorProviderImpl.openDatagramChannel(ProtocolFamily) returns an open, bindable DatagramChannel
 * @modules java.base/sun.nio.ch
 * @run main SelectorProviderImplWitupTest
 */

import java.net.InetSocketAddress;
import java.net.ProtocolFamily;
import java.net.StandardProtocolFamily;
import java.nio.channels.DatagramChannel;
import java.nio.channels.spi.SelectorProvider;
import sun.nio.ch.SelectorProviderImpl;

public class SelectorProviderImplWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        SelectorProvider sp = SelectorProvider.provider();
        check(sp instanceof SelectorProviderImpl,
              "default provider is not a SelectorProviderImpl: " + sp.getClass());
        SelectorProviderImpl impl = (SelectorProviderImpl) sp;

        DatagramChannel dc = impl.openDatagramChannel(StandardProtocolFamily.INET);
        try {
            check(dc != null, "openDatagramChannel(INET) returned null");
            check(dc.isOpen(), "newly opened channel should be open");

            DatagramChannel bound = dc.bind(new InetSocketAddress("127.0.0.1", 0));
            check(bound == dc, "bind should return the same channel");
            java.net.SocketAddress local = dc.getLocalAddress();
            check(local instanceof InetSocketAddress, "local address should be an InetSocketAddress");
            check(((InetSocketAddress) local).getPort() > 0, "ephemeral port should be assigned");
        } finally {
            dc.close();
        }
        check(!dc.isOpen(), "channel should be closed after close()");

        DatagramChannel dc2 = impl.openDatagramChannel(StandardProtocolFamily.INET);
        try {
            check(dc2 != dc, "each call should return a new channel instance");
            check(dc2.isOpen(), "second channel should be open");
        } finally {
            dc2.close();
        }
    }
}
