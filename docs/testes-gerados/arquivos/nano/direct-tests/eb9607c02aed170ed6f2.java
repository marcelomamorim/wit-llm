/* @test    @summary Test openDatagramChannel method of SelectorProviderImpl for ProtocolFamily parameter    @run main SelectorProviderImplWitupTest */

import java.io.IOException;
import java.net.ProtocolFamily;
import java.nio.channels.DatagramChannel;
import sun.nio.ch.SelectorProviderImpl;

public class SelectorProviderImplWitupTest {

    public static void main(String[] args) throws IOException {
        testOpenDatagramChannelWithDefaultFamily();
        testOpenDatagramChannelWithCustomFamily();
    }

    static void testOpenDatagramChannelWithDefaultFamily() throws IOException {
        SelectorProviderImpl provider = new SelectorProviderImpl();
        ProtocolFamily defaultFamily = ProtocolFamily.INET;
        DatagramChannel channel = provider.openDatagramChannel();
        assert channel != null : "DatagramChannel should not be null";
        // Since openDatagramChannel() without parameters is not present, simulate default by passing INET
        DatagramChannel channelWithFamily = provider.openDatagramChannel(defaultFamily);
        assert channelWithFamily != null : "DatagramChannel with default family should not be null";
    }

    static void testOpenDatagramChannelWithCustomFamily() throws IOException {
        SelectorProviderImpl provider = new SelectorProviderImpl();
        ProtocolFamily customFamily = ProtocolFamily.UNIX;
        DatagramChannel channel = provider.openDatagramChannel(customFamily);
        assert channel != null : "DatagramChannel with custom family should not be null";
    }
}
