/*
 * @test
 * @summary Witup-derived tests for DatagramPacket(byte[], int, int) argument validation
 * @run main DatagramPacketWitupTest
 */

import java.net.DatagramPacket;

public class DatagramPacketWitupTest {

    public static void main(String[] args) throws Exception {
        testValidConstructorExposesState();
        testBoundaryAccepted();
        testOverflowRejected();
        testNegativeLengthRejected();
        testNegativeOffsetRejected();
        testOutOfBoundsRejected();
        testTwoArgConstructorDelegates();
        testDefaultAddressAndPort();
    }

    static void testValidConstructorExposesState() {
        byte[] buf = new byte[16];
        DatagramPacket p = new DatagramPacket(buf, 4, 8);
        check(p.getData() == buf, "data array must be the same instance");
        check(p.getOffset() == 4, "offset must be 4");
        check(p.getLength() == 8, "length must be 8");
    }

    static void testBoundaryAccepted() {
        byte[] buf = new byte[10];
        DatagramPacket p = new DatagramPacket(buf, 0, 10);
        check(p.getLength() == 10, "full-buffer length must be accepted");
        DatagramPacket q = new DatagramPacket(buf, 10, 0);
        check(q.getOffset() == 10, "offset at buffer end with zero length must be accepted");
        check(q.getLength() == 0, "zero length must be accepted");
    }

    static void testOverflowRejected() {
        expectIAE(new byte[10], Integer.MAX_VALUE, 1);
    }

    static void testNegativeLengthRejected() {
        expectIAE(new byte[10], 0, -1);
    }

    static void testNegativeOffsetRejected() {
        expectIAE(new byte[10], -1, 5);
    }

    static void testOutOfBoundsRejected() {
        expectIAE(new byte[10], 5, 6);
    }

    static void testTwoArgConstructorDelegates() {
        byte[] buf = new byte[12];
        DatagramPacket p = new DatagramPacket(buf, 4);
        check(p.getData() == buf, "data array must be the same instance");
        check(p.getOffset() == 0, "offset must default to 0");
        check(p.getLength() == 4, "length must be 4");
    }

    static void testDefaultAddressAndPort() {
        DatagramPacket p = new DatagramPacket(new byte[8], 0, 8);
        check(p.getAddress() == null, "address must be null for receive packet");
        check(p.getPort() == -1, "port must be -1 for receive packet");
    }

    static void expectIAE(byte[] buf, int offset, int length) {
        try {
            new DatagramPacket(buf, offset, length);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException expected for offset=" + offset + " length=" + length);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
