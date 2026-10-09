/* @test    @summary Test setting valid and invalid port values in DatagramPacket.setPort()    @run main DatagramPacketWitupTest_P45e931e69059 */

import java.net.DatagramPacket;

public class DatagramPacketWitupTest_P45e931e69059 {

    public static void main(String[] args) {
        testSetPortValid();
        testSetPortNegative();
        testSetPortTooLarge();
    }

    static void testSetPortValid() {
        DatagramPacket packet = new DatagramPacket(new byte[10], 10);
        try {
            packet.setPort(0);
            System.out.println("setPort(0) passed");
        } catch (Exception e) {
            System.err.println("setPort(0) failed: " + e);
        }
        try {
            packet.setPort(65535);
            System.out.println("setPort(65535) passed");
        } catch (Exception e) {
            System.err.println("setPort(65535) failed: " + e);
        }
    }

    static void testSetPortNegative() {
        DatagramPacket packet = new DatagramPacket(new byte[10], 10);
        try {
            packet.setPort(-1);
            System.err.println("setPort(-1) did not throw");
        } catch (IllegalArgumentException e) {
            System.out.println("setPort(-1) correctly threw IllegalArgumentException");
        } catch (Exception e) {
            System.err.println("setPort(-1) threw unexpected exception: " + e);
        }
    }

    static void testSetPortTooLarge() {
        DatagramPacket packet = new DatagramPacket(new byte[10], 10);
        try {
            packet.setPort(65536);
            System.err.println("setPort(65536) did not throw");
        } catch (IllegalArgumentException e) {
            System.out.println("setPort(65536) correctly threw IllegalArgumentException");
        } catch (Exception e) {
            System.err.println("setPort(65536) threw unexpected exception: " + e);
        }
    }
}
