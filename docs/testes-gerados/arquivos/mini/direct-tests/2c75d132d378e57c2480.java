/*
 * @test
 * @summary Test openDatagramChannel() method of SelectorProviderImpl
 * @run main SelectorProviderImplWitupTest
 */

import java.io.IOException;
import java.nio.channels.DatagramChannel;
import sun.nio.ch.SelectorProviderImpl;

public class SelectorProviderImplWitupTest {

    public static void main(String[] args) throws IOException {
        SelectorProviderImpl provider = new SelectorProviderImpl() {
            @Override
            public java.nio.channels.spi.AbstractSelector openSelector() throws IOException {
                throw new UnsupportedOperationException("Not implemented");
            }
        };

        DatagramChannel dc = provider.openDatagramChannel();
        if (dc == null) {
            throw new AssertionError("openDatagramChannel() returned null");
        }

        // Check that the returned channel is open
        if (!dc.isOpen()) {
            throw new AssertionError("DatagramChannel returned is not open");
        }

        dc.close();
    }
}
