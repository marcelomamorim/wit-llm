/*
 * @test
 * @summary Verifies DatagramPacket.setLength accepts valid lengths and rejects out-of-bound, negative and overflowing lengths
 * @run main DatagramPacketWitupTest
 */

import java.net.DatagramPacket;

public class DatagramPacketWitupTest {

    public static void main(String[] args) throws Exception {
        testValidLengthUpdatesLength();
        testZeroLengthAccepted();
        testLengthGreaterThanBufferRejected();
        testNegativeLengthRejected();
        testOffsetIsIncludedInBound();
        testOverflowRejected();
        testFailedSetLengthPreservesLength();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectIllegalArgument(DatagramPacket p, int length) {
        try {
            p.setLength(length);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for length " + length);
    }

    static void testValidLengthUpdatesLength() {
        byte[] b = new byte[10];
        DatagramPacket p = new DatagramPacket(b, 0, 10);
        p.setLength(5);
        check(p.getLength() == 5, "length should be 5");
        p.setLength(10);
        check(p.getLength() == 10, "length should be 10");
    }

    static void testZeroLengthAccepted() {
        DatagramPacket p = new DatagramPacket(new byte[10], 0, 10);
        p.setLength(0);
        check(p.getLength() == 0, "length should be 0");
    }

    static void testLengthGreaterThanBufferRejected() {
        DatagramPacket p = new DatagramPacket(new byte[10], 10);
        expectIllegalArgument(p, 11);
    }

    static void testNegativeLengthRejected() {
        DatagramPacket p = new DatagramPacket(new byte[10], 10);
        expectIllegalArgument(p, -1);
    }

    static void testOffsetIsIncludedInBound() {
        byte[] b = new byte[10];
        DatagramPacket p = new DatagramPacket(b, 4, 6);
        p.setLength(6);
        check(p.getLength() == 6, "length should be 6 with offset 4");
        expectIllegalArgument(p, 7);
    }

    static void testOverflowRejected() {
        DatagramPacket p = new DatagramPacket(new byte[10], 1, 1);
        expectIllegalArgument(p, Integer.MAX_VALUE);
    }

    static void testFailedSetLengthPreservesLength() {
        DatagramPacket p = new DatagramPacket(new byte[10], 0, 4);
        expectIllegalArgument(p, 11);
        check(p.getLength() == 4, "length should remain 4 after failed setLength");
    }
}
