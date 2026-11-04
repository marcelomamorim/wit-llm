/*
 * @test
 * @summary DatagramChannel.bind (implemented by sun.nio.ch.DatagramChannelImpl) binds once, returns this, and rejects rebinding or use after close
 * @run main DatagramChannelImplWitupTest
 */

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.AlreadyBoundException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;

public class DatagramChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testBindReturnsSameChannelAndSetsLocalAddress();
        testSecondBindThrowsAlreadyBound();
        testBindAfterCloseThrowsClosedChannel();
    }

    static void testBindReturnsSameChannelAndSetsLocalAddress() throws Exception {
        try (DatagramChannel dc = DatagramChannel.open()) {
            InetSocketAddress req = new InetSocketAddress(InetAddress.getLoopbackAddress(), 0);
            DatagramChannel ret = dc.bind(req);
            if (ret != dc) {
                throw new RuntimeException("bind must return this channel");
            }
            SocketAddress local = dc.getLocalAddress();
            if (local == null) {
                throw new RuntimeException("local address null after bind");
            }
            if (!(local instanceof InetSocketAddress)) {
                throw new RuntimeException("unexpected local address type: " + local);
            }
            InetSocketAddress isa = (InetSocketAddress) local;
            if (isa.getPort() == 0) {
                throw new RuntimeException("ephemeral port not assigned");
            }
        }
    }

    static void testSecondBindThrowsAlreadyBound() throws Exception {
        try (DatagramChannel dc = DatagramChannel.open()) {
            dc.bind(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0));
            SocketAddress first = dc.getLocalAddress();
            try {
                dc.bind(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0));
                throw new RuntimeException("expected AlreadyBoundException");
            } catch (AlreadyBoundException expected) {
                // expected
            }
            if (!first.equals(dc.getLocalAddress())) {
                throw new RuntimeException("local address changed after failed rebind");
            }
        }
    }

    static void testBindAfterCloseThrowsClosedChannel() throws Exception {
        DatagramChannel dc = DatagramChannel.open();
        dc.close();
        try {
            dc.bind(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0));
            throw new RuntimeException("expected ClosedChannelException");
        } catch (ClosedChannelException expected) {
            // expected
        }
    }
}
