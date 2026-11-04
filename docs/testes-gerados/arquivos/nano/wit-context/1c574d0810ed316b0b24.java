/*
 * @test
 * @summary Tests for java.net.DatagramPacket setPort method.
 * @run main DatagramPacketWitupTest
 */

import java.net.DatagramPacket;

public class DatagramPacketWitupTest {

    public static void main(String[] args) {
        testSetPortValid();
        testSetPortNegative();
        testSetPortTooLarge();
    }

    static void testSetPortValid() {
        byte[] buffer = new byte[10];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        try {
            packet.setPort(0);
            assert true : "Setting port to 0 should not throw";
            packet.setPort(65535);
            assert true : "Setting port to 65535 should not throw";
        } catch (Exception e) {
            assert false : "Unexpected exception for valid port: " + e;
        }
    }

    static void testSetPortNegative() {
        byte[] buffer = new byte[10];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        try {
            packet.setPort(-1);
            assert false : "Expected IllegalArgumentException for negative port";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("Port out of range") : "Exception message mismatch";
        } catch (Exception e) {
            assert false : "Unexpected exception type: " + e;
        }
    }

    static void testSetPortTooLarge() {
        byte[] buffer = new byte[10];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        try {
            packet.setPort(65536);
            assert false : "Expected IllegalArgumentException for port > 65535";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("Port out of range") : "Exception message mismatch";
        } catch (Exception e) {
            assert false : "Unexpected exception type: " + e;
        }
    }
}
