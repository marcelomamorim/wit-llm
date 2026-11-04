/*
 * @test
 * @summary Verifies that SelectorProviderImpl.openDatagramChannel() returns new open DatagramChannel instances bound to the provider
 * @modules java.base/sun.nio.ch
 * @run main SelectorProviderImplWitupTest
 */

import java.io.IOException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.spi.AbstractSelector;
import sun.nio.ch.SelectorProviderImpl;

public class SelectorProviderImplWitupTest {

    static class TestProvider extends SelectorProviderImpl {
        @Override
        public AbstractSelector openSelector() throws IOException {
            throw new UnsupportedOperationException("not used by this test");
        }
    }

    public static void main(String[] args) throws Exception {
        TestProvider provider = new TestProvider();

        DatagramChannel dc = provider.openDatagramChannel();
        try {
            if (dc == null) {
                throw new RuntimeException("openDatagramChannel returned null");
            }
            if (!dc.isOpen()) {
                throw new RuntimeException("new DatagramChannel is not open");
            }
            if (dc.provider() != provider) {
                throw new RuntimeException("DatagramChannel provider does not match the creating provider");
            }
            if (!dc.isBlocking()) {
                throw new RuntimeException("new DatagramChannel should be in blocking mode by default");
            }
            if (dc.isRegistered()) {
                throw new RuntimeException("new DatagramChannel should not be registered with a selector");
            }

            DatagramChannel dc2 = provider.openDatagramChannel();
            try {
                if (dc2 == dc) {
                    throw new RuntimeException("openDatagramChannel returned the same instance twice");
                }
                if (!dc2.isOpen()) {
                    throw new RuntimeException("second DatagramChannel is not open");
                }
                if (dc2.provider() != provider) {
                    throw new RuntimeException("second DatagramChannel provider mismatch");
                }
            } finally {
                dc2.close();
            }
        } finally {
            dc.close();
        }

        if (dc.isOpen()) {
            throw new RuntimeException("DatagramChannel should be closed after close()");
        }
    }
}
