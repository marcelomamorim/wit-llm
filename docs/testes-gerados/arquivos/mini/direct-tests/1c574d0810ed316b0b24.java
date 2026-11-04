/*
 * @test
 * @summary Test java.net.DatagramPacket.setPort(int) for valid and invalid ports
 * @run main DatagramPacketWitupTest
 */

import java.net.DatagramPacket;

public class DatagramPacketWitupTest {
    public static void main(String[] args) {
        testValidPorts();
        testInvalidPorts();
    }

    private static void testValidPorts() {
        byte[] buf = new byte[10];
        DatagramPacket packet = new DatagramPacket(buf, 10);

        // Test lower bound port 0
        packet.setPort(0);
        if (packet.port != 0) {
            throw new RuntimeException("Failed to set port to 0");
        }

        // Test upper bound port 65535
        packet.setPort(65535);
        if (packet.port != 65535) {
            throw new RuntimeException("Failed to set port to 65535");
        }

        // Test a typical valid port
        packet.setPort(12345);
        if (packet.port != 12345) {
            throw new RuntimeException("Failed to set port to 12345");
        }
    }

    private static void testInvalidPorts() {
        byte[] buf = new byte[10];
        DatagramPacket packet = new DatagramPacket(buf, 10);

        // Test negative port
        try {
            packet.setPort(-1);
            throw new RuntimeException("Expected IllegalArgumentException for port -1");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // Test port greater than 65535
        try {
            packet.setPort(65536);
            throw new RuntimeException("Expected IllegalArgumentException for port 65536");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
