/*
 * @test
 * @summary Verifies observable behavior of SelectorProviderImpl.openDatagramChannel(ProtocolFamily) for null, unsupported and INET families
 * @modules java.base/sun.nio.ch
 * @run main SelectorProviderImplWitupTest
 */

import java.net.ProtocolFamily;
import java.net.StandardProtocolFamily;
import java.nio.channels.DatagramChannel;
import java.nio.channels.spi.SelectorProvider;
import sun.nio.ch.SelectorProviderImpl;

public class SelectorProviderImplWitupTest {

    interface Action {
        void run() throws Exception;
    }

    static void expectThrows(Class<? extends Throwable> type, Action action) throws Exception {
        try {
            action.run();
        } catch (Throwable t) {
            if (type.isInstance(t)) {
                return;
            }
            throw new RuntimeException("unexpected exception: " + t, t);
        }
        throw new RuntimeException("no exception thrown, expected " + type.getName());
    }

    public static void main(String[] args) throws Exception {
        SelectorProviderImpl provider = (SelectorProviderImpl) SelectorProvider.provider();

        expectThrows(NullPointerException.class, () -> provider.openDatagramChannel(null));

        ProtocolFamily custom = new ProtocolFamily() { };
        expectThrows(UnsupportedOperationException.class, () -> provider.openDatagramChannel(custom));

        DatagramChannel ch = provider.openDatagramChannel(StandardProtocolFamily.INET);
        try {
            if (ch == null) {
                throw new RuntimeException("openDatagramChannel(INET) returned null");
            }
            if (!ch.isOpen()) {
                throw new RuntimeException("channel opened with INET is not open");
            }
        } finally {
            ch.close();
        }
    }
}
