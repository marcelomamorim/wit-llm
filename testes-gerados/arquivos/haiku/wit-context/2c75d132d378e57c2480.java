/*
 * @test
 * @summary Checks that SelectorProviderImpl.openDatagramChannel() returns new, open, unbound DatagramChannel instances bound to the same provider
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
            throw new UnsupportedOperationException("not used by openDatagramChannel");
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        TestProvider provider = new TestProvider();

        DatagramChannel first = provider.openDatagramChannel();
        try {
            check(first != null, "openDatagramChannel() returned null");
            check(first.isOpen(), "new DatagramChannel should be open");
            check(first.provider() == provider,
                  "channel provider should be the creating SelectorProviderImpl");
            check(first.socket() != null, "DatagramChannel.socket() should not be null");
            check(!first.socket().isBound(), "new DatagramChannel should be unbound");

            DatagramChannel second = provider.openDatagramChannel();
            try {
                check(second != null, "second openDatagramChannel() returned null");
                check(second != first, "each call should return a distinct channel");
                check(second.isOpen(), "second DatagramChannel should be open");
            } finally {
                second.close();
            }
        } finally {
            first.close();
        }
    }
}
