/*
 * @test
 * @summary Test java.net.DatagramPacket.setLength(int) method for valid and invalid inputs
 * @run main java.net.DatagramPacketWitupTest
 */

import java.net.DatagramPacket;

public class DatagramPacketWitupTest {
    public static void main(String[] args) {
        testSetLengthValid();
        testSetLengthInvalidNegative();
        testSetLengthInvalidTooLarge();
        testSetLengthInvalidOverflow();
    }

    private static void testSetLengthValid() {
        byte[] buffer = new byte[10];
        DatagramPacket packet = new DatagramPacket(buffer, 2, 5);
        packet.setLength(3);
        if (packet.length != 3) {
            throw new RuntimeException("Failed: length not set correctly");
        }
        if (packet.bufLength != 3) {
            throw new RuntimeException("Failed: bufLength not set correctly");
        }
    }

    private static void testSetLengthInvalidNegative() {
        byte[] buffer = new byte[10];
        DatagramPacket packet = new DatagramPacket(buffer, 0, 5);
        try {
            packet.setLength(-1);
            throw new RuntimeException("Failed: expected IllegalArgumentException for negative length");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testSetLengthInvalidTooLarge() {
        byte[] buffer = new byte[10];
        DatagramPacket packet = new DatagramPacket(buffer, 1, 5);
        try {
            packet.setLength(10); // 10 + offset(1) = 11 > buffer.length(10)
            throw new RuntimeException("Failed: expected IllegalArgumentException for length too large");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testSetLengthInvalidOverflow() {
        byte[] buffer = new byte[10];
        DatagramPacket packet = new DatagramPacket(buffer, 0, 5);
        try {
            packet.setLength(Integer.MAX_VALUE);
            throw new RuntimeException("Failed: expected IllegalArgumentException for length overflow");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
