/*
 * @test
 * @summary Verifies argument validation and state updates of DatagramPacket.setData
 * @run main DatagramPacketWitupTest
 */

import java.net.DatagramPacket;

public class DatagramPacketWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectIllegalArgument(byte[] buf, int offset, int length, String label) {
        DatagramPacket p = new DatagramPacket(new byte[4], 4);
        try {
            p.setData(buf, offset, length);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException: " + label);
    }

    static void testValidData() {
        byte[] buf = new byte[8];
        DatagramPacket p = new DatagramPacket(new byte[1], 1);
        p.setData(buf, 2, 3);
        check(p.getData() == buf, "data array should be the given buffer");
        check(p.getOffset() == 2, "offset should be 2");
        check(p.getLength() == 3, "length should be 3");
    }

    static void testExactBoundary() {
        byte[] buf = new byte[8];
        DatagramPacket p = new DatagramPacket(new byte[1], 1);
        p.setData(buf, 5, 3);
        check(p.getOffset() == 5, "offset should be 5 at exact boundary");
        check(p.getLength() == 3, "length should be 3 at exact boundary");
    }

    static void testZeroLengthAtEnd() {
        byte[] buf = new byte[8];
        DatagramPacket p = new DatagramPacket(new byte[1], 1);
        p.setData(buf, 8, 0);
        check(p.getOffset() == 8, "offset should be 8");
        check(p.getLength() == 0, "length should be 0");
    }

    static void testNegativeLength() {
        expectIllegalArgument(new byte[8], 0, -1, "negative length");
    }

    static void testNegativeOffset() {
        expectIllegalArgument(new byte[8], -1, 2, "negative offset");
    }

    static void testOffsetPlusLengthExceedsBuffer() {
        expectIllegalArgument(new byte[8], 6, 3, "offset + length > buf.length");
    }

    static void testIntegerOverflow() {
        expectIllegalArgument(new byte[8], Integer.MAX_VALUE, 1, "overflowing offset + length");
    }

    static void testNullBufferWithZeroArgs() {
        DatagramPacket p = new DatagramPacket(new byte[1], 1);
        try {
            p.setData(null, 0, 0);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null buffer");
    }

    static void testStateUnchangedAfterFailure() {
        byte[] original = new byte[4];
        DatagramPacket p = new DatagramPacket(original, 1, 2);
        try {
            p.setData(new byte[2], 0, 5);
        } catch (IllegalArgumentException expected) {
            // expected
        }
        check(p.getData() == original, "data should remain unchanged after failed setData");
        check(p.getOffset() == 1, "offset should remain unchanged after failed setData");
        check(p.getLength() == 2, "length should remain unchanged after failed setData");
    }

    public static void main(String[] args) {
        testValidData();
        testExactBoundary();
        testZeroLengthAtEnd();
        testNegativeLength();
        testNegativeOffset();
        testOffsetPlusLengthExceedsBuffer();
        testIntegerOverflow();
        testNullBufferWithZeroArgs();
        testStateUnchangedAfterFailure();
    }
}
