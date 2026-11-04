/*
 * @test
 * @summary Test disconnect() method of DatagramChannelImpl for open, connected, and already disconnected states.
 * @run main DatagramChannelImplWitupTest
 */

import java.io.IOException;
import java.net.StandardProtocolFamily;
import sun.nio.ch.DatagramChannelImpl;

public class DatagramChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testDisconnectOnOpenConnected();
        testDisconnectOnOpenUnconnected();
        testDisconnectOnClosedChannel();
    }

    private static void testDisconnectOnOpenConnected() throws Exception {
        // Create a DatagramChannelImpl with IPv4 family
        DatagramChannelImpl channel = createChannel(StandardProtocolFamily.INET);
        try {
            // Simulate connecting the channel
            simulateConnect(channel);
            // Call disconnect
            channel.disconnect();
            // Verify state is unconnected
            if (channel.state != 0) { // ST_UNCONNECTED = 0
                throw new AssertionError("Expected state to be unconnected after disconnect, but was: " + channel.state);
            }
        } finally {
            channel.close();
        }
    }

    private static void testDisconnectOnOpenUnconnected() throws Exception {
        DatagramChannelImpl channel = createChannel(StandardProtocolFamily.INET);
        try {
            // Channel is initially unconnected
            // Call disconnect
            channel.disconnect();
            // Verify state remains unconnected
            if (channel.state != 0) { // ST_UNCONNECTED = 0
                throw new AssertionError("Expected state to be unconnected, but was: " + channel.state);
            }
        } finally {
            channel.close();
        }
    }

    private static void testDisconnectOnClosedChannel() throws Exception {
        DatagramChannelImpl channel = createChannel(StandardProtocolFamily.INET);
        channel.close();
        try {
            channel.disconnect();
            throw new AssertionError("Expected IOException when disconnecting closed channel");
        } catch (IOException e) {
            // Expected exception
        }
    }

    private static DatagramChannelImpl createChannel(StandardProtocolFamily family) throws IOException {
        // Use reflection to invoke the constructor
        try {
            return new DatagramChannelImpl(SelectorProvider.provider(), family);
        } catch (Exception e) {
            throw new IOException("Failed to create DatagramChannelImpl", e);
        }
    }

    private static void simulateConnect(DatagramChannelImpl channel) {
        // Set internal state to connected
        channel.state = 1; // ST_CONNECTED
        // Set remoteAddress to a dummy address
        channel.remoteAddress = java.net.InetAddress.getLoopbackAddress();
    }
}
