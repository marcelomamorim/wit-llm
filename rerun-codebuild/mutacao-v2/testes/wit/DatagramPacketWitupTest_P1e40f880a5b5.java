/*
 * @test
 * @summary Test java.net.DatagramPacket.setPort(int) for valid and invalid port values
 * @run main DatagramPacketWitupTest_P1e40f880a5b5
 */

import java.net.DatagramPacket;

public class DatagramPacketWitupTest_P1e40f880a5b5 {
    public static void main(String[] args) {
        testSetPortValid();
        testSetPortInvalidLow();
        testSetPortInvalidHigh();
    }

    private static void testSetPortValid() {
        byte[] buf = new byte[10];
        DatagramPacket packet = new DatagramPacket(buf, 0, 10);
        // Test valid port values including boundary values
        int[] validPorts = {0, 1, 80, 65535};
        for (int port : validPorts) {
            packet.setPort(port);
            if (getPort(packet) != port) {
                throw new RuntimeException("setPort failed to set port correctly for port: " + port);
            }
        }
    }

    private static void testSetPortInvalidLow() {
        byte[] buf = new byte[10];
        DatagramPacket packet = new DatagramPacket(buf, 0, 10);
        try {
            packet.setPort(-1);
            throw new RuntimeException("setPort did not throw IllegalArgumentException for port -1");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    private static void testSetPortInvalidHigh() {
        byte[] buf = new byte[10];
        DatagramPacket packet = new DatagramPacket(buf, 0, 10);
        try {
            packet.setPort(65536);
            throw new RuntimeException("setPort did not throw IllegalArgumentException for port 65536");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    // Helper method to get the port value via public API
    private static int getPort(DatagramPacket packet) {
        // DatagramPacket has a public getPort() method
        return packet.getPort();
    }
}
