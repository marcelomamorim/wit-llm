/*
 * @test
 * @summary Test openDatagramChannel method of SelectorProviderImpl
 * @run main SelectorProviderImplWitupTest
 */

import java.io.IOException;
import java.nio.channels.DatagramChannel;
import sun.nio.ch.SelectorProviderImpl;

public class SelectorProviderImplWitupTest {

    // Minimal concrete subclass to test abstract SelectorProviderImpl
    static class TestSelectorProviderImpl extends SelectorProviderImpl {
        @Override
        public java.nio.channels.spi.AbstractSelector openSelector() throws IOException {
            throw new UnsupportedOperationException("Not implemented");
        }
    }

    public static void main(String[] args) throws Exception {
        SelectorProviderImpl provider = new TestSelectorProviderImpl();

        // Test openDatagramChannel returns non-null DatagramChannel
        DatagramChannel dc = provider.openDatagramChannel();
        if (dc == null) {
            throw new AssertionError("openDatagramChannel returned null");
        }

        // Test that openDatagramChannel does not throw IOException unexpectedly
        try {
            provider.openDatagramChannel();
        } catch (IOException e) {
            // IOException is declared, so allowed, but fail if unexpected
            throw new AssertionError("openDatagramChannel threw IOException unexpectedly", e);
        }

        System.out.println("Test passed");
    }
}
