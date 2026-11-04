/*
 * @test
 * @summary Test java.net.DatagramPacket.setLength(int) for valid and invalid inputs
 * @run main DatagramPacketWitupTest
 */

import java.net.DatagramPacket;

public class DatagramPacketWitupTest {
    public static void main(String[] args) {
        testSetLengthValid();
        testSetLengthInvalidLengthTooLarge();
        testSetLengthInvalidLengthNegative();
        testSetLengthInvalidLengthOverflow();
        System.out.println("All tests passed.");
    }

    private static void testSetLengthValid() {
        byte[] buffer = new byte[10];
        DatagramPacket packet = new DatagramPacket(buffer, 2, 5);
        // setLength with valid length within buffer bounds
        packet.setLength(5);
        if (packet.length != 5) {
            throw new RuntimeException("setLength did not set length correctly");
        }
    }

    private static void testSetLengthInvalidLengthTooLarge() {
        byte[] buffer = new byte[10];
        DatagramPacket packet = new DatagramPacket(buffer, 2, 5);
        // length + offset = 11 > buffer.length (10), should throw IllegalArgumentException
        try {
            packet.setLength(9); // 9 + 2 = 11 > 10
            throw new RuntimeException("Expected IllegalArgumentException for length too large");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testSetLengthInvalidLengthNegative() {
        byte[] buffer = new byte[10];
        DatagramPacket packet = new DatagramPacket(buffer, 0, 5);
        // negative length should throw IllegalArgumentException
        try {
            packet.setLength(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative length");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testSetLengthInvalidLengthOverflow() {
        byte[] buffer = new byte[10];
        // offset is large positive, length negative causing (length + offset) < 0
        DatagramPacket packet = new DatagramPacket(buffer, 9, 1);
        try {
            packet.setLength(-10); // -10 + 9 = -1 < 0
            throw new RuntimeException("Expected IllegalArgumentException for length + offset < 0");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
