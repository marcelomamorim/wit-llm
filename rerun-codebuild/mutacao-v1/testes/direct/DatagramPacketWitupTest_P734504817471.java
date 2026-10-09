/* @test    @summary Test setting valid and invalid port numbers in DatagramPacket    @run main DatagramPacketWitupTest_P734504817471 */

import java.net.DatagramPacket;

public class DatagramPacketWitupTest_P734504817471 {

    public static void main(String[] args) {
        testSetPortValid();
        testSetPortNegative();
        testSetPortTooLarge();
    }

    static void testSetPortValid() {
        DatagramPacket packet = new DatagramPacket(new byte[10], 10);
        try {
            packet.setPort(0);
            System.out.println("PASS: setPort(0)");
        } catch (Exception e) {
            throw new AssertionError("FAIL: setPort(0) threw exception: " + e);
        }
        try {
            packet.setPort(65535);
            System.out.println("PASS: setPort(65535)");
        } catch (Exception e) {
            throw new AssertionError("FAIL: setPort(65535) threw exception: " + e);
        }
    }

    static void testSetPortNegative() {
        DatagramPacket packet = new DatagramPacket(new byte[10], 10);
        try {
            packet.setPort(-1);
            throw new AssertionError("FAIL: setPort(-1) did not throw exception");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: setPort(-1) threw IllegalArgumentException");
        } catch (Exception e) {
            throw new AssertionError("FAIL: setPort(-1) threw wrong exception: " + e);
        }
    }

    static void testSetPortTooLarge() {
        DatagramPacket packet = new DatagramPacket(new byte[10], 10);
        try {
            packet.setPort(65536);
            throw new AssertionError("FAIL: setPort(65536) did not throw exception");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: setPort(65536) threw IllegalArgumentException");
        } catch (Exception e) {
            throw new AssertionError("FAIL: setPort(65536) threw wrong exception: " + e);
        }
    }
}
