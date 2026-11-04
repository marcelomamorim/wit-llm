/* @test    @summary Test for DatagramPacket.setLength to verify exception handling and normal behavior    @run main DatagramPacketWitupTest */

import java.net.DatagramPacket;

public class DatagramPacketWitupTest {

    public static void main(String[] args) {
        testSetLengthWithValidValue();
        testSetLengthWithNegativeValue();
        testSetLengthWithExceedingBuffer();
    }

    private static void testSetLengthWithValidValue() {
        byte[] buffer = new byte[10];
        DatagramPacket packet = new DatagramPacket(buffer, 0, buffer.length);
        try {
            packet.setLength(5);
            if (packet.length != 5 || packet.bufLength != 5) {
                throw new AssertionError("Valid setLength did not set length correctly");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid length", e);
        }
    }

    private static void testSetLengthWithNegativeValue() {
        byte[] buffer = new byte[10];
        DatagramPacket packet = new DatagramPacket(buffer, 0, buffer.length);
        try {
            packet.setLength(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative length");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type for negative length", e);
        }
    }

    private static void testSetLengthWithExceedingBuffer() {
        byte[] buffer = new byte[10];
        DatagramPacket packet = new DatagramPacket(buffer, 0, buffer.length);
        try {
            packet.setLength(11);
            throw new AssertionError("Expected IllegalArgumentException for length exceeding buffer");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type for exceeding buffer", e);
        }
    }
}
