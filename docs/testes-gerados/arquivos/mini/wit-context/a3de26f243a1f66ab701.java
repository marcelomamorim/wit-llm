/*
 * @test
 * @summary Test java.net.DatagramPacket.setData(byte[]) for null and valid inputs
 * @run main DatagramPacketWitupTest
 */

import java.net.DatagramPacket;

public class DatagramPacketWitupTest {
    public static void main(String[] args) {
        testSetDataNullBuffer();
        testSetDataValidBuffer();
    }

    private static void testSetDataNullBuffer() {
        DatagramPacket packet = new DatagramPacket(new byte[1], 1);
        try {
            packet.setData(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
            if (!"null packet buffer".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testSetDataValidBuffer() {
        byte[] buf = new byte[] {1, 2, 3};
        DatagramPacket packet = new DatagramPacket(new byte[10], 10);
        packet.setData(buf);
        // Verify that the internal buffer is set to the new buffer by checking length
        if (packet.length != buf.length) {
            throw new RuntimeException("Length not updated correctly after setData");
        }
        if (packet.offset != 0) {
            throw new RuntimeException("Offset not reset to 0 after setData");
        }
    }
}
