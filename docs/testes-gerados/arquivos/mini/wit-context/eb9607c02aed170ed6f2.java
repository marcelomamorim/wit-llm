/*
 * @test
 * @summary Test openDatagramChannel(ProtocolFamily) method of SelectorProviderImpl
 * @run main SelectorProviderImplWitupTest
 */

import java.io.IOException;
import java.net.ProtocolFamily;
import java.net.StandardProtocolFamily;
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

        // Test 1: Passing null should throw NullPointerException
        try {
            provider.openDatagramChannel(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected
        }

        // Test 2: Passing unsupported ProtocolFamily should throw UnsupportedOperationException
        ProtocolFamily unsupportedFamily = new ProtocolFamily() {
            public String toString() { return "UnsupportedFamily"; }
        };
        try {
            provider.openDatagramChannel(unsupportedFamily);
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected
        }

        // Test 3: Passing StandardProtocolFamily.INET should succeed
        DatagramChannel dcInet = provider.openDatagramChannel(StandardProtocolFamily.INET);
        if (dcInet == null) {
            throw new RuntimeException("openDatagramChannel(INET) returned null");
        }

        // Test 4: Passing StandardProtocolFamily.INET6 may throw UnsupportedOperationException if IPv6 not available
        try {
            DatagramChannel dcInet6 = provider.openDatagramChannel(StandardProtocolFamily.INET6);
            if (dcInet6 == null) {
                throw new RuntimeException("openDatagramChannel(INET6) returned null");
            }
        } catch (UnsupportedOperationException e) {
            // Acceptable if IPv6 not supported
        }
    }
}
