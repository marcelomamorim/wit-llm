/* @test    @summary Test openDatagramChannel method of SelectorProviderImpl for successful DatagramChannel creation.    @run main SelectorProviderImplWitupTest */

import java.io.IOException;
import java.nio.channels.DatagramChannel;
import sun.nio.ch.SelectorProviderImpl;

public class SelectorProviderImplWitupTest {

    public static void main(String[] args) throws IOException {
        testOpenDatagramChannelReturnsNonNull();
    }

    public static void testOpenDatagramChannelReturnsNonNull() throws IOException {
        SelectorProviderImpl provider = new SelectorProviderImpl();
        DatagramChannel channel = provider.openDatagramChannel();
        if (channel == null) {
            throw new AssertionError("Expected non-null DatagramChannel");
        }
        channel.close();
    }
}
