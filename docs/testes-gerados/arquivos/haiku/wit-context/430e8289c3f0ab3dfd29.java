/*
 * @test
 * @summary Verify DatagramPacket.setLength(int) accepts valid lengths and rejects lengths that exceed the buffer bounds or are negative
 * @run main DatagramPacketWitupTest
 */

import java.net.DatagramPacket;

public class DatagramPacketWitupTest {

    public static void main(String[] args) {
        testValidLengthNoOffset();
        testValidLengthWithOffset();
        testLengthExceedsBufferNoOffset();
        testLengthExceedsBufferWithOffset();
        testNegativeLength();
        testOverflowingLength();
        testFailedSetLengthKeepsPreviousLength();
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static void expectIllegalArgument(DatagramPacket p, int length) {
        try {
            p.setLength(length);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for length " + length);
    }

    private static void testValidLengthNoOffset() {
        byte[] buf = new byte[10];
        DatagramPacket p = new DatagramPacket(buf, 0, 10);
        p.setLength(10);
        check(p.getLength() == 10, "length should be 10");
        p.setLength(0);
        check(p.getLength() == 0, "length should be 0");
    }

    private static void testValidLengthWithOffset() {
        byte[] buf = new byte[10];
        DatagramPacket p = new DatagramPacket(buf, 2, 8);
        p.setLength(8);
        check(p.getLength() == 8, "length should be 8 with offset 2");
    }

    private static void testLengthExceedsBufferNoOffset() {
        byte[] buf = new byte[10];
        DatagramPacket p = new DatagramPacket(buf, 0, 10);
        expectIllegalArgument(p, 11);
    }

    private static void testLengthExceedsBufferWithOffset() {
        byte[] buf = new byte[10];
        DatagramPacket p = new DatagramPacket(buf, 2, 8);
        expectIllegalArgument(p, 9);
    }

    private static void testNegativeLength() {
        byte[] buf = new byte[10];
        DatagramPacket p = new DatagramPacket(buf, 0, 10);
        expectIllegalArgument(p, -1);
    }

    private static void testOverflowingLength() {
        byte[] buf = new byte[10];
        DatagramPacket p = new DatagramPacket(buf, 2, 8);
        expectIllegalArgument(p, Integer.MAX_VALUE);
    }

    private static void testFailedSetLengthKeepsPreviousLength() {
        byte[] buf = new byte[10];
        DatagramPacket p = new DatagramPacket(buf, 0, 6);
        expectIllegalArgument(p, 11);
        check(p.getLength() == 6, "length should remain 6 after rejected setLength");
    }
}
