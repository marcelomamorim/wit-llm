/*
 * @test
 * @summary Verifies public observable behavior of DatagramChannel.disconnect() (via DatagramChannelImpl) for connected, unconnected and closed channels
 * @run main DatagramChannelImplWitupTest
 */

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;

public class DatagramChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        try (DatagramChannel receiver = DatagramChannel.open();
             DatagramChannel sender = DatagramChannel.open()) {
            receiver.bind(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0));
            InetSocketAddress target = (InetSocketAddress) receiver.getLocalAddress();

            check(!sender.isConnected(), "fresh channel must not be connected");

            sender.connect(target);
            check(sender.isConnected(), "channel must be connected after connect");

            DatagramChannel result = sender.disconnect();
            check(result == sender, "disconnect must return the same channel");
            check(!sender.isConnected(), "channel must not be connected after disconnect");
            check(sender.getRemoteAddress() == null, "remote address must be null after disconnect");
            check(sender.getLocalAddress() != null, "local address must remain available after disconnect");

            check(sender.disconnect() == sender, "disconnect on unconnected channel must return this");
            check(!sender.isConnected(), "channel must remain unconnected");
        }

        DatagramChannel closed = DatagramChannel.open();
        closed.close();
        check(closed.disconnect() == closed, "disconnect on closed channel must return this");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
