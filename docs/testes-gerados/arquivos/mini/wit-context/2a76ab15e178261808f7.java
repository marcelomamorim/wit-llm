/*
 * @test
 * @summary Test java.net.DatagramPacket.setData(byte[], int, int) for valid and invalid inputs
 * @run main java.net.DatagramPacketWitupTest
 */

import java.net.DatagramPacket;

public class DatagramPacketWitupTest {
    public static void main(String[] args) {
        testValidSetData();
        testInvalidSetData();
    }

    private static void testValidSetData() {
        byte[] buffer = new byte[10];
        DatagramPacket packet = new DatagramPacket(buffer, 0, 10);

        // Test setting data with valid offset and length
        byte[] newBuffer = new byte[20];
        packet.setData(newBuffer, 5, 10);

        // Verify no exception and internal state updated by checking length indirectly
        if (packet.length != 10) {
            throw new RuntimeException("Failed: length not set correctly");
        }
        if (packet.offset != 5) {
            throw new RuntimeException("Failed: offset not set correctly");
        }
    }

    private static void testInvalidSetData() {
        byte[] buffer = new byte[10];
        DatagramPacket packet = new DatagramPacket(buffer, 0, 10);

        // Test offset < 0
        try {
            packet.setData(buffer, -1, 5);
            throw new RuntimeException("Failed: expected IllegalArgumentException for offset < 0");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // Test length < 0
        try {
            packet.setData(buffer, 0, -1);
            throw new RuntimeException("Failed: expected IllegalArgumentException for length < 0");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // Test length + offset < 0 (overflow case)
        try {
            packet.setData(buffer, Integer.MAX_VALUE, 1);
            throw new RuntimeException("Failed: expected IllegalArgumentException for length + offset overflow");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // Test length + offset > buffer.length
        try {
            packet.setData(buffer, 5, 6); // 5 + 6 = 11 > 10
            throw new RuntimeException("Failed: expected IllegalArgumentException for length + offset > buffer.length");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
