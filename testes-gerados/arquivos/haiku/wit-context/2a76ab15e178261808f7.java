/*
 * @test
 * @summary Witup-generated checks for DatagramPacket.setData argument validation
 * @run main DatagramPacketWitupTest
 */

public class DatagramPacketWitupTest {

    public static void main(String[] args) {
        testValidRange();
        testBoundaryAtEnd();
        testNegativeLength();
        testNegativeOffset();
        testOffsetPlusLengthBeyondBuffer();
        testIntegerOverflow();
        testFailureKeepsPreviousState();
    }

    static void testValidRange() {
        byte[] buf = new byte[10];
        DatagramPacket p = new DatagramPacket(new byte[4], 0, 4);
        p.setData(buf, 2, 3);
        check(p.getData() == buf, "data array not stored");
        check(p.getOffset() == 2, "offset not stored");
        check(p.getLength() == 3, "length not stored");
    }

    static void testBoundaryAtEnd() {
        byte[] buf = new byte[10];
        DatagramPacket p = new DatagramPacket(new byte[4], 0, 4);
        p.setData(buf, 4, 6);
        check(p.getOffset() == 4, "offset at boundary not stored");
        check(p.getLength() == 6, "length at boundary not stored");
        p.setData(buf, 10, 0);
        check(p.getOffset() == 10, "zero-length at end not stored");
        check(p.getLength() == 0, "zero length not stored");
    }

    static void testNegativeLength() {
        expectIAE(new byte[10], 0, -1);
    }

    static void testNegativeOffset() {
        expectIAE(new byte[10], -1, 2);
    }

    static void testOffsetPlusLengthBeyondBuffer() {
        expectIAE(new byte[10], 5, 6);
        expectIAE(new byte[10], 0, 11);
    }

    static void testIntegerOverflow() {
        expectIAE(new byte[10], Integer.MAX_VALUE, 1);
    }

    static void testFailureKeepsPreviousState() {
        byte[] first = new byte[8];
        byte[] second = new byte[3];
        DatagramPacket p = new DatagramPacket(first, 1, 4);
        try {
            p.setData(second, 2, 5);
            throw new RuntimeException("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }
        check(p.getData() == first, "data changed after failed setData");
        check(p.getOffset() == 1, "offset changed after failed setData");
        check(p.getLength() == 4, "length changed after failed setData");
    }

    static void expectIAE(byte[] buf, int offset, int length) {
        DatagramPacket p = new DatagramPacket(new byte[4], 0, 4);
        try {
            p.setData(buf, offset, length);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for offset="
                + offset + " length=" + length);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
