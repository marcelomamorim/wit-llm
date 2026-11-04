/*
 * @test
 * @summary Black-box tests of DatagramChannelImpl.read(ByteBuffer) through the public DatagramChannel API
 * @run main DatagramChannelImplWitupTest
 */

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;

public class DatagramChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testNullBufferThrowsNPE();
        testReadOnClosedChannelThrows();
        testNonBlockingReadWithoutDataReturnsZero();
        testBlockingReadReceivesDatagram();
        System.out.println("PASSED");
    }

    static void testNullBufferThrowsNPE() throws IOException {
        try (DatagramChannel dc = DatagramChannel.open()) {
            try {
                dc.read((ByteBuffer) null);
                throw new RuntimeException("NullPointerException expected");
            } catch (NullPointerException expected) {
                // expected
            }
        }
    }

    static void testReadOnClosedChannelThrows() throws IOException {
        DatagramChannel dc = DatagramChannel.open();
        dc.close();
        try {
            dc.read(ByteBuffer.allocate(16));
            throw new RuntimeException("ClosedChannelException expected");
        } catch (ClosedChannelException expected) {
            // expected
        }
    }

    static void testNonBlockingReadWithoutDataReturnsZero() throws IOException {
        try (DatagramChannel dc = DatagramChannel.open()) {
            dc.bind(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0));
            dc.configureBlocking(false);
            ByteBuffer buf = ByteBuffer.allocate(16);
            int n = dc.read(buf);
            if (n != 0) {
                throw new RuntimeException("Expected 0 but got " + n);
            }
            if (buf.position() != 0) {
                throw new RuntimeException("Buffer position changed");
            }
        }
    }

    static void testBlockingReadReceivesDatagram() throws IOException {
        try (DatagramChannel receiver = DatagramChannel.open();
             DatagramChannel sender = DatagramChannel.open()) {
            InetSocketAddress loopback =
                new InetSocketAddress(InetAddress.getLoopbackAddress(), 0);
            receiver.bind(loopback);
            sender.bind(loopback);

            ByteBuffer out = ByteBuffer.allocate(5);
            out.put(new byte[] {1, 2, 3, 4, 5});
            out.flip();
            int sent = sender.send(out, receiver.getLocalAddress());
            if (sent != 5) {
                throw new RuntimeException("Expected 5 bytes sent but got " + sent);
            }

            ByteBuffer in = ByteBuffer.allocate(16);
            int n = receiver.read(in);
            if (n != 5) {
                throw new RuntimeException("Expected read of 5 but got " + n);
            }
            if (in.position() != 5) {
                throw new RuntimeException("Expected position 5 but got " + in.position());
            }
            in.flip();
            for (int i = 1; i <= 5; i++) {
                if (in.get() != i) {
                    throw new RuntimeException("Unexpected byte at index " + (i - 1));
                }
            }
        }
    }
}
