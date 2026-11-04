/*
 * @test
 * @summary Test for DatagramChannelImpl.bind(SocketAddress)
 * @run main DatagramChannelImplWitupTest
 */

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import sun.nio.ch.DatagramChannelImpl;

public class DatagramChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testBindWithNull();
        testBindWithAlreadyBound();
        testBindWithValidAddress();
    }

    private static void testBindWithNull() throws IOException {
        DatagramChannelImpl channel = createChannel();
        try {
            channel.bind(null);
            System.out.println("testBindWithNull: Passed");
        } catch (IOException e) {
            System.out.println("testBindWithNull: Failed with exception: " + e);
        }
    }

    private static void testBindWithAlreadyBound() throws IOException {
        DatagramChannelImpl channel = createChannel();
        try {
            channel.bind(new InetSocketAddress(0));
            channel.bind(new InetSocketAddress(0));
            System.out.println("testBindWithAlreadyBound: Failed - no exception thrown");
        } catch (IOException e) {
            if (e instanceof java.nio.channels.AlreadyBoundException) {
                System.out.println("testBindWithAlreadyBound: Passed");
            } else {
                System.out.println("testBindWithAlreadyBound: Failed with exception: " + e);
            }
        }
    }

    private static void testBindWithValidAddress() throws IOException {
        DatagramChannelImpl channel = createChannel();
        try {
            channel.bind(new InetSocketAddress(0));
            System.out.println("testBindWithValidAddress: Passed");
        } catch (IOException e) {
            System.out.println("testBindWithValidAddress: Failed with exception: " + e);
        }
    }

    private static DatagramChannelImpl createChannel() {
        // Using a dummy SelectorProvider; in real tests, use a proper provider if needed
        return new DatagramChannelImpl(java.nio.channels.spi.SelectorProvider.provider(), null, null);
    }
}
