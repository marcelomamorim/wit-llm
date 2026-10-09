/* @test    @summary Test setting port on DatagramPacket */
/* @run main DatagramPacketWitupTest_P3a2a88c663ad */

import java.net.DatagramPacket;

public class DatagramPacketWitupTest_P3a2a88c663ad {

    public static void main(String[] args) {
        testSetPortValid();
        testSetPortNegative();
        testSetPortTooLarge();
    }

    static void testSetPortValid() {
        DatagramPacket packet = new DatagramPacket(new byte[10], 10);
        int validPort = 8080;
        packet.setPort(validPort);
        assert packet.port == validPort : "Port should be set to valid value";
    }

    static void testSetPortNegative() {
        DatagramPacket packet = new DatagramPacket(new byte[10], 10);
        boolean exceptionThrown = false;
        try {
            packet.setPort(-1);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Setting negative port should throw IllegalArgumentException";
    }

    static void testSetPortTooLarge() {
        DatagramPacket packet = new DatagramPacket(new byte[10], 10);
        boolean exceptionThrown = false;
        try {
            packet.setPort(0x10000); // 65536, out of range
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Setting port > 65535 should throw IllegalArgumentException";
    }
}
