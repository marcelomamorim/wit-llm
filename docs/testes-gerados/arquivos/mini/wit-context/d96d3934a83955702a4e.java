/*
 * @test
 * @summary Test sun.nio.ch.DatagramChannelImpl.bind(SocketAddress) for expected exceptions and normal behavior
 * @run main DatagramChannelImplWitupTest
 */

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.AlreadyBoundException;
import java.nio.channels.spi.SelectorProvider;
import sun.nio.ch.DatagramChannelImpl;

public class DatagramChannelImplWitupTest {
    public static void main(String[] args) throws Exception {
        SelectorProvider sp = SelectorProvider.provider();
        DatagramChannelImpl channel = new DatagramChannelImpl(sp);

        // Test bind with null address - should bind to wildcard address without exception
        try {
            channel.bind(null);
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException when binding null", e);
        }

        // Test bind when already bound - should throw AlreadyBoundException
        boolean alreadyBoundThrown = false;
        try {
            // Bind again to provoke AlreadyBoundException
            channel.bind(new InetSocketAddress(0));
        } catch (AlreadyBoundException e) {
            alreadyBoundThrown = true;
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException instead of AlreadyBoundException", e);
        }

        if (!alreadyBoundThrown) {
            throw new RuntimeException("Expected AlreadyBoundException was not thrown on second bind");
        }

        // No other exceptions expected from bind
        System.out.println("DatagramChannelImpl.bind tests passed.");
    }
}
