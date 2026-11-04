/* @test    @summary Test DatagramPacket.setLength() for valid and invalid inputs    @run main DatagramPacketWitupTest */

import java.net.DatagramPacket;

public class DatagramPacketWitupTest {

    public static void main(String[] args) {
        testSetLengthValid();
        testSetLengthInvalidNegative();
        testSetLengthInvalidTooLarge();
    }

    static void testSetLengthValid() {
        byte[] buffer = new byte[10];
        DatagramPacket packet = new DatagramPacket(buffer, 0, 10);
        try {
            packet.setLength(5);
            assert packet.length == 5 : "Expected length to be 5";
            assert packet.bufLength == 5 : "Expected bufLength to be 5";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid setLength", e);
        }
    }

    static void testSetLengthInvalidNegative() {
        byte[] buffer = new byte[10];
        DatagramPacket packet = new DatagramPacket(buffer, 0, 10);
        try {
            packet.setLength(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative length");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testSetLengthInvalidTooLarge() {
        byte[] buffer = new byte[10];
        DatagramPacket packet = new DatagramPacket(buffer, 0, 10);
        try {
            packet.setLength(11);
            throw new AssertionError("Expected IllegalArgumentException for length exceeding buffer");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
