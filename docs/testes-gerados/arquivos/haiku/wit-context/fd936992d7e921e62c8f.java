/*
 * @test
 * @summary Witup-derived checks for DatagramPacket(byte[], int) constructor
 * @run main DatagramPacketWitupTest
 */
import java.net.DatagramPacket;

public class DatagramPacketWitupTest {

    public static void main(String[] args) {
        testNegativeLengthRejected();
        testLengthGreaterThanBufferRejected();
        testLengthEqualsBufferAccepted();
        testZeroLengthWithEmptyBuffer();
        testLengthWithinBufferAccepted();
        System.out.println("PASS");
    }

    static void testNegativeLengthRejected() {
        expectIllegalArgument(new byte[8], -1);
    }

    static void testLengthGreaterThanBufferRejected() {
        expectIllegalArgument(new byte[4], 5);
    }

    static void testLengthEqualsBufferAccepted() {
        byte[] buf = new byte[6];
        DatagramPacket p = new DatagramPacket(buf, buf.length);
        check(p.getData() == buf, "data must be the same buffer");
        check(p.getOffset() == 0, "offset must be 0");
        check(p.getLength() == buf.length, "length must equal buffer length");
    }

    static void testZeroLengthWithEmptyBuffer() {
        byte[] buf = new byte[0];
        DatagramPacket p = new DatagramPacket(buf, 0);
        check(p.getLength() == 0, "length must be 0");
        check(p.getOffset() == 0, "offset must be 0");
    }

    static void testLengthWithinBufferAccepted() {
        byte[] buf = new byte[10];
        DatagramPacket p = new DatagramPacket(buf, 3);
        check(p.getData() == buf, "data must be the same buffer");
        check(p.getLength() == 3, "length must be 3");
        check(p.getOffset() == 0, "offset must be 0");
    }

    static void expectIllegalArgument(byte[] buf, int length) {
        try {
            new DatagramPacket(buf, length);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for length " + length);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
