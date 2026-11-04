/*
 * @test
 * @summary Test for sun.nio.ch.SelectorProviderImpl.openDatagramChannel()
 * @run main SelectorProviderImplWitupTest
 */

import java.io.IOException;
import java.nio.channels.DatagramChannel;
import sun.nio.ch.SelectorProviderImpl;

public class SelectorProviderImplWitupTest {

    public static void main(String[] args) throws IOException {
        testOpenDatagramChannelReturnsInstance();
        testOpenDatagramChannelThrowsIOException();
    }

    static void testOpenDatagramChannelReturnsInstance() throws IOException {
        SelectorProviderImpl provider = new SelectorProviderImpl();
        DatagramChannel channel = provider.openDatagramChannel();
        if (channel == null) {
            throw new AssertionError("Expected non-null DatagramChannel");
        }
        if (!(channel instanceof java.nio.channels.DatagramChannel)) {
            throw new AssertionError("Channel is not instance of DatagramChannel");
        }
        channel.close();
    }

    static void testOpenDatagramChannelThrowsIOException() {
        try {
            SelectorProviderImpl provider = new SelectorProviderImpl() {
                @Override
                public DatagramChannel openDatagramChannel() throws IOException {
                    throw new IOException("Simulated IOException");
                }
            };
            provider.openDatagramChannel();
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            if (!"Simulated IOException".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
    }
}
