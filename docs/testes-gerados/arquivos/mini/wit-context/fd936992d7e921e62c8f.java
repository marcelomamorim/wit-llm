/*
 * @test
 * @summary Test DatagramPacket constructor DatagramPacket(byte[], int) for valid and invalid length
 * @run main DatagramPacketWitupTest
 */

import java.net.DatagramPacket;

public class DatagramPacketWitupTest {
    public static void main(String[] args) {
        testValidLength();
        testInvalidLengthNegative();
        testInvalidLengthTooLarge();
        System.out.println("PASS");
    }

    private static void testValidLength() {
        byte[] buffer = new byte[10];
        int length = 5;
        DatagramPacket packet = new DatagramPacket(buffer, length);
        if (packet.length != length) {
            throw new RuntimeException("Expected length " + length + " but got " + packet.length);
        }
        if (packet.offset != 0) {
            throw new RuntimeException("Expected offset 0 but got " + packet.offset);
        }
        if (packet.buf != buffer) {
            throw new RuntimeException("Buffer reference mismatch");
        }
    }

    private static void testInvalidLengthNegative() {
        byte[] buffer = new byte[10];
        int length = -1;
        try {
            new DatagramPacket(buffer, length);
            throw new RuntimeException("Expected IllegalArgumentException for negative length");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testInvalidLengthTooLarge() {
        byte[] buffer = new byte[10];
        int length = 11; // larger than buffer length
        try {
            new DatagramPacket(buffer, length);
            throw new RuntimeException("Expected IllegalArgumentException for length > buffer.length");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
