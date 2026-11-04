/*
 * @test
 * @summary Test DatagramPacket constructor with buf, offset and length for valid and invalid inputs
 * @run main DatagramPacketWitupTest
 */

import java.net.DatagramPacket;

public class DatagramPacketWitupTest {
    public static void main(String[] args) {
        testValidConstructor();
        testInvalidConstructor();
    }

    private static void testValidConstructor() {
        byte[] buffer = new byte[10];
        // Valid: offset=0, length=10
        DatagramPacket dp1 = new DatagramPacket(buffer, 0, 10);
        if (dp1 == null) throw new RuntimeException("DatagramPacket constructor returned null");

        // Valid: offset=2, length=5 (2+5=7 <= 10)
        DatagramPacket dp2 = new DatagramPacket(buffer, 2, 5);
        if (dp2 == null) throw new RuntimeException("DatagramPacket constructor returned null");
    }

    private static void testInvalidConstructor() {
        byte[] buffer = new byte[10];

        // offset < 0
        try {
            new DatagramPacket(buffer, -1, 5);
            throw new RuntimeException("Expected IllegalArgumentException for offset < 0");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // length < 0
        try {
            new DatagramPacket(buffer, 0, -1);
            throw new RuntimeException("Expected IllegalArgumentException for length < 0");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // offset + length > buffer.length
        try {
            new DatagramPacket(buffer, 6, 5); // 6+5=11 > 10
            throw new RuntimeException("Expected IllegalArgumentException for offset + length > buffer.length");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // length < -offset (e.g. offset=5, length= -6)
        try {
            new DatagramPacket(buffer, 5, -6);
            throw new RuntimeException("Expected IllegalArgumentException for length < -offset");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
