/*
 * @test
 * @summary WITUP-generated checks for DatagramChannelImpl.connect(SocketAddress) observable exceptions via public API
 * @run main DatagramChannelImplWitupTest
 */

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.UnsupportedAddressTypeException;

public class DatagramChannelImplWitupTest {

    private static final class UnsupportedAddress extends SocketAddress {
        private static final long serialVersionUID = 1L;
    }

    public static void main(String[] args) throws Exception {
        testConnectNullThrowsNPE();
        testConnectUnsupportedAddressTypeThrows();
        testConnectOnClosedChannelThrowsClosedChannelException();
    }

    static void testConnectNullThrowsNPE() throws IOException {
        DatagramChannel dc = DatagramChannel.open();
        try {
            dc.connect((SocketAddress) null);
            throw new RuntimeException("Expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            dc.close();
        }
    }

    static void testConnectUnsupportedAddressTypeThrows() throws IOException {
        DatagramChannel dc = DatagramChannel.open();
        try {
            dc.connect(new UnsupportedAddress());
            throw new RuntimeException("Expected UnsupportedAddressTypeException");
        } catch (UnsupportedAddressTypeException expected) {
            // expected
        } finally {
            dc.close();
        }
    }

    static void testConnectOnClosedChannelThrowsClosedChannelException() throws IOException {
        DatagramChannel dc = DatagramChannel.open();
        dc.close();
        InetSocketAddress isa = new InetSocketAddress(InetAddress.getLoopbackAddress(), 9);
        try {
            dc.connect(isa);
            throw new RuntimeException("Expected ClosedChannelException");
        } catch (ClosedChannelException expected) {
            // expected
        }
    }
}
