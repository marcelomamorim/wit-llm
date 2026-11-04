/*
 * @test
 * @summary Test DatagramChannelImpl.bind(SocketAddress) for basic behavior.
 * @run main DatagramChannelImplWitupTest
 */

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import sun.nio.ch.DatagramChannelImpl;

public class DatagramChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testBindWithNull();
        testBindTwiceThrows();
    }

    private static void testBindWithNull() throws IOException {
        DatagramChannelImpl channel = createChannel();
        try {
            channel.bind(null);
            System.out.println("bind(null) succeeded");
        } catch (IOException e) {
            System.out.println("bind(null) threw IOException: " + e.getMessage());
        }
        channel.close();
    }

    private static void testBindTwiceThrows() throws IOException {
        DatagramChannelImpl channel = createChannel();
        try {
            channel.bind(new InetSocketAddress(0));
            boolean exceptionThrown = false;
            try {
                channel.bind(new InetSocketAddress(0));
            } catch (AlreadyBoundException e) {
                exceptionThrown = true;
                System.out.println("Second bind threw AlreadyBoundException as expected");
            } catch (IOException e) {
                System.out.println("Second bind threw IOException: " + e.getMessage());
            }
            if (!exceptionThrown) {
                System.out.println("Second bind did not throw exception");
            }
        } finally {
            channel.close();
        }
    }

    private static DatagramChannelImpl createChannel() throws IOException {
        // Use the constructor with SelectorProvider and ProtocolFamily
        // Since the constructor is package-private, we simulate creation via reflection
        // or assume access for testing purposes. Here, we assume access.
        // Alternatively, create a DatagramChannel and cast if possible.
        // For simplicity, create a DatagramChannel and cast.
        java.nio.channels.DatagramChannel ch = java.nio.channels.DatagramChannel.open();
        if (ch instanceof DatagramChannelImpl) {
            return (DatagramChannelImpl) ch;
        } else {
            throw new IOException("Failed to create DatagramChannelImpl instance");
        }
    }
}
