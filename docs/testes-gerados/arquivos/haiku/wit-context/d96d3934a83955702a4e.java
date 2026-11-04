/*
 * @test
 * @summary Witup-derived checks for DatagramChannelImpl.bind through the public DatagramChannel API
 * @run main DatagramChannelImplWitupTest
 */

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.StandardProtocolFamily;
import java.nio.channels.AlreadyBoundException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;

public class DatagramChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testBindReturnsSameChannel();
        testBindTwiceThrowsAlreadyBound();
        testBindOnClosedChannelThrowsClosedChannel();
    }

    static void testBindReturnsSameChannel() throws IOException {
        DatagramChannel dc = DatagramChannel.open(StandardProtocolFamily.INET);
        try {
            InetSocketAddress local = new InetSocketAddress(InetAddress.getLoopbackAddress(), 0);
            DatagramChannel result = dc.bind(local);
            if (result != dc) {
                throw new RuntimeException("bind must return the same channel");
            }
            if (dc.getLocalAddress() == null) {
                throw new RuntimeException("local address not set after bind");
            }
        } finally {
            dc.close();
        }
    }

    static void testBindTwiceThrowsAlreadyBound() throws IOException {
        DatagramChannel dc = DatagramChannel.open(StandardProtocolFamily.INET);
        try {
            InetSocketAddress local = new InetSocketAddress(InetAddress.getLoopbackAddress(), 0);
            dc.bind(local);
            try {
                dc.bind(local);
                throw new RuntimeException("expected AlreadyBoundException");
            } catch (AlreadyBoundException expected) {
                // expected
            }
        } finally {
            dc.close();
        }
    }

    static void testBindOnClosedChannelThrowsClosedChannel() throws IOException {
        DatagramChannel dc = DatagramChannel.open(StandardProtocolFamily.INET);
        dc.close();
        InetSocketAddress local = new InetSocketAddress(InetAddress.getLoopbackAddress(), 0);
        try {
            dc.bind(local);
            throw new RuntimeException("expected ClosedChannelException");
        } catch (ClosedChannelException expected) {
            // expected
        }
    }
}
