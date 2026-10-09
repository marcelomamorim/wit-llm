/*
 * @test
 * @summary Test setting port in DatagramPacket with valid and invalid values
 * @run main DatagramPacketWitupTest_P3a2a88c663ad
 */

import java.net.DatagramPacket;
import java.lang.IllegalArgumentException;

public class DatagramPacketWitupTest_P3a2a88c663ad {

    public static void main(String[] args) {
        testSetPortValid();
        testSetPortNegative();
        testSetPortTooLarge();
    }

    static void testSetPortValid() {
        DatagramPacket packet = new DatagramPacket(new byte[10], 10);
        try {
            packet.setPort(0);
            System.out.println("testSetPortValid: port 0 set successfully");
        } catch (Exception e) {
            System.err.println("testSetPortValid: unexpected exception: " + e);
        }
        try {
            packet.setPort(65535);
            System.out.println("testSetPortValid: port 65535 set successfully");
        } catch (Exception e) {
            System.err.println("testSetPortValid: unexpected exception: " + e);
        }
    }

    static void testSetPortNegative() {
        DatagramPacket packet = new DatagramPacket(new byte[10], 10);
        try {
            packet.setPort(-1);
            System.err.println("testSetPortNegative: expected IllegalArgumentException but none thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("testSetPortNegative: caught expected IllegalArgumentException");
        } catch (Exception e) {
            System.err.println("testSetPortNegative: caught unexpected exception: " + e);
        }
    }

    static void testSetPortTooLarge() {
        DatagramPacket packet = new DatagramPacket(new byte[10], 10);
        try {
            packet.setPort(65536);
            System.err.println("testSetPortTooLarge: expected IllegalArgumentException but none thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("testSetPortTooLarge: caught expected IllegalArgumentException");
        } catch (Exception e) {
            System.err.println("testSetPortTooLarge: caught unexpected exception: " + e);
        }
    }
}
