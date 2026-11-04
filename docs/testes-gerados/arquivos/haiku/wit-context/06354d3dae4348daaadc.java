/*
 * @test
 * @summary Verify DatagramChannel read(ByteBuffer) rejects null, reports closed channels and returns 0 for an empty non-blocking read
 * @run main DatagramChannelImplWitupTest
 */

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;

public class DatagramChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testNullBuffer();
        testClosedChannel();
        testNonBlockingEmptyRead();
    }

    static void testNullBuffer() throws Exception {
        try (DatagramChannel dc = DatagramChannel.open()) {
            try {
                dc.read((ByteBuffer) null);
                throw new AssertionError("expected NullPointerException for null buffer");
            } catch (NullPointerException expected) {
            }
        }
    }

    static void testClosedChannel() throws Exception {
        DatagramChannel dc = DatagramChannel.open();
        dc.close();
        try {
            dc.read(ByteBuffer.allocate(8));
            throw new AssertionError("expected ClosedChannelException");
        } catch (ClosedChannelException expected) {
        }
    }

    static void testNonBlockingEmptyRead() throws Exception {
        try (DatagramChannel dc = DatagramChannel.open()) {
            dc.bind(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0));
            dc.configureBlocking(false);
            int n = dc.read(ByteBuffer.allocate(16));
            if (n != 0) {
                throw new AssertionError("expected 0 for empty non-blocking read but got " + n);
            }
        }
    }
}
