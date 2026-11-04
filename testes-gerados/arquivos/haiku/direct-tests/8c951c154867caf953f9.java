/*
 * @test
 * @summary DatagramSocketAdaptor.connect(SocketAddress) rejects null and connects the underlying channel
 * @run main DatagramSocketAdaptorWitupTest
 */

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;

public class DatagramSocketAdaptorWitupTest {

    public static void main(String[] args) throws Exception {
        testNullAddressRejected();
        testConnectSetsConnectedState();
    }

    static void testNullAddressRejected() throws Exception {
        DatagramChannel dc = DatagramChannel.open();
        try {
            DatagramSocket s = dc.socket();
            try {
                s.connect((SocketAddress) null);
                throw new RuntimeException("Expected NullPointerException for null address");
            } catch (NullPointerException expected) {
                // expected: Objects.requireNonNull in connect(SocketAddress)
            }
            if (dc.isConnected()) {
                throw new RuntimeException("Channel must not be connected after rejected connect");
            }
        } finally {
            dc.close();
        }
    }

    static void testConnectSetsConnectedState() throws Exception {
        DatagramChannel dc = DatagramChannel.open();
        try {
            DatagramSocket s = dc.socket();
            SocketAddress remote = new InetSocketAddress(InetAddress.getLoopbackAddress(), 9);
            s.connect(remote);
            if (!dc.isConnected()) {
                throw new RuntimeException("Channel should be connected");
            }
            if (!s.isConnected()) {
                throw new RuntimeException("Socket adaptor should report connected");
            }
        } finally {
            dc.close();
        }
    }
}
