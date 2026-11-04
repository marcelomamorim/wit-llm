/*
 * @test
 * @summary Verify DatagramChannel.write(ByteBuffer) public behavior, dispatched to DatagramChannelImpl.write
 * @run main DatagramChannelImplWitupTest
 */

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;

public class DatagramChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testNullBufferThrowsNPE();
        testWriteAfterCloseThrowsClosedChannelException();
        testEmptyBufferReturnsZero();
        testWriteConnectedReturnsBytesWrittenAndAdvancesPosition();
        System.out.println("PASS");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullBufferThrowsNPE() throws Exception {
        try (DatagramChannel dc = DatagramChannel.open()) {
            try {
                dc.write((ByteBuffer) null);
                check(false, "expected NullPointerException for null buffer");
            } catch (NullPointerException expected) {
                // expected
            }
        }
    }

    static void testWriteAfterCloseThrowsClosedChannelException() throws Exception {
        DatagramChannel dc = DatagramChannel.open();
        dc.close();
        try {
            dc.write(ByteBuffer.allocate(1));
            check(false, "expected ClosedChannelException after close");
        } catch (ClosedChannelException expected) {
            // expected
        }
    }

    static void testEmptyBufferReturnsZero() throws Exception {
        try (DatagramChannel rx = DatagramChannel.open()
                .bind(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0));
             DatagramChannel tx = DatagramChannel.open()) {
            tx.connect(rx.getLocalAddress());
            ByteBuffer empty = ByteBuffer.allocate(0);
            int n = tx.write(empty);
            check(n == 0, "expected 0 bytes written for empty buffer but got " + n);
            check(empty.position() == 0, "position should not change for empty buffer");
        }
    }

    static void testWriteConnectedReturnsBytesWrittenAndAdvancesPosition() throws Exception {
        try (DatagramChannel rx = DatagramChannel.open()
                .bind(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0));
             DatagramChannel tx = DatagramChannel.open()) {
            tx.connect(rx.getLocalAddress());
            ByteBuffer src = ByteBuffer.wrap(new byte[] {1, 2, 3, 4, 5});
            int n = tx.write(src);
            check(n == 5, "expected 5 bytes written but got " + n);
            check(src.position() == 5, "buffer position should be 5 but was " + src.position());

            ByteBuffer received = ByteBuffer.allocate(16);
            rx.receive(received);
            check(received.position() == 5, "receiver should get 5 bytes but got " + received.position());
        }
    }
}
