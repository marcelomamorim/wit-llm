/* @test    @summary Test setting port with valid and invalid values in DatagramPacket    @run main DatagramPacketWitupTest_P734504817471 */

import java.net.DatagramPacket;

public class DatagramPacketWitupTest_P734504817471 {

    public static void main(String[] args) {
        testSetPortValid();
        testSetPortNegative();
        testSetPortTooLarge();
    }

    private static void testSetPortValid() {
        try {
            byte[] buf = new byte[10];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            packet.setPort(0);
            packet.setPort(65535);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid port", e);
        }
    }

    private static void testSetPortNegative() {
        try {
            byte[] buf = new byte[10];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            packet.setPort(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative port");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type for negative port", e);
        }
    }

    private static void testSetPortTooLarge() {
        try {
            byte[] buf = new byte[10];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            packet.setPort(65536);
            throw new AssertionError("Expected IllegalArgumentException for port > 65535");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type for port > 65535", e);
        }
    }
}
