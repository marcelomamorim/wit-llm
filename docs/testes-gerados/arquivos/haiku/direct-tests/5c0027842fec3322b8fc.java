/*
 * @test
 * @summary Verifies public connect() behavior of DatagramChannel (backed by DatagramChannelImpl) for success, already-connected and closed-channel cases
 * @run main DatagramChannelImplWitupTest
 */

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.StandardProtocolFamily;
import java.nio.channels.AlreadyConnectedException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;

public class DatagramChannelImplWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        InetAddress lo = InetAddress.getLoopbackAddress();
        DatagramChannel receiver = DatagramChannel.open(StandardProtocolFamily.INET);
        InetSocketAddress target;
        try {
            receiver.bind(new InetSocketAddress(lo, 0));
            target = (InetSocketAddress) receiver.getLocalAddress();

            try (DatagramChannel sender = DatagramChannel.open(StandardProtocolFamily.INET)) {
                check(!sender.isConnected(), "sender should be unconnected initially");

                DatagramChannel result = sender.connect(target);
                check(result == sender, "connect must return this channel");
                check(sender.isConnected(), "sender should be connected after connect()");
                check(target.equals(sender.getRemoteAddress()),
                      "remote address should equal the connect target");

                boolean thrown = false;
                try {
                    sender.connect(target);
                } catch (AlreadyConnectedException e) {
                    thrown = true;
                }
                check(thrown, "second connect() must throw AlreadyConnectedException");
                check(sender.isConnected(), "channel should remain connected after failed reconnect");
            }
        } finally {
            receiver.close();
        }

        DatagramChannel closed = DatagramChannel.open(StandardProtocolFamily.INET);
        closed.close();
        boolean closedThrown = false;
        try {
            closed.connect(target);
        } catch (ClosedChannelException e) {
            closedThrown = true;
        }
        check(closedThrown, "connect() on closed channel must throw ClosedChannelException");
    }
}
