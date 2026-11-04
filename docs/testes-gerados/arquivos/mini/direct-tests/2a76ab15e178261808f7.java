/*
 * @test
 * @summary Test java.net.DatagramPacket.setData(byte[], int, int) for valid and invalid inputs
 * @run main DatagramPacketWitupTest
 */

import java.net.DatagramPacket;

public class DatagramPacketWitupTest {
    public static void main(String[] args) {
        testValidSetData();
        testSetDataWithNegativeLength();
        testSetDataWithNegativeOffset();
        testSetDataWithLengthPlusOffsetOverflow();
        testSetDataWithLengthPlusOffsetExceedingBuffer();
    }

    private static void testValidSetData() {
        byte[] buffer = new byte[10];
        DatagramPacket packet = new DatagramPacket(new byte[10], 10);
        packet.setData(buffer, 2, 5);
        if (packet.buf != buffer) {
            throw new RuntimeException("buf field not set correctly");
        }
        if (packet.offset != 2) {
            throw new RuntimeException("offset field not set correctly");
        }
        if (packet.length != 5) {
            throw new RuntimeException("length field not set correctly");
        }
        if (packet.bufLength != 5) {
            throw new RuntimeException("bufLength field not set correctly");
        }
    }

    private static void testSetDataWithNegativeLength() {
        byte[] buffer = new byte[10];
        DatagramPacket packet = new DatagramPacket(new byte[10], 10);
        try {
            packet.setData(buffer, 0, -1);
            throw new RuntimeException("Expected IllegalArgumentException for negative length");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testSetDataWithNegativeOffset() {
        byte[] buffer = new byte[10];
        DatagramPacket packet = new DatagramPacket(new byte[10], 10);
        try {
            packet.setData(buffer, -1, 5);
            throw new RuntimeException("Expected IllegalArgumentException for negative offset");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testSetDataWithLengthPlusOffsetOverflow() {
        byte[] buffer = new byte[10];
        DatagramPacket packet = new DatagramPacket(new byte[10], 10);
        try {
            // length + offset < 0 due to integer overflow
            packet.setData(buffer, Integer.MAX_VALUE, Integer.MAX_VALUE);
            throw new RuntimeException("Expected IllegalArgumentException for length+offset overflow");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testSetDataWithLengthPlusOffsetExceedingBuffer() {
        byte[] buffer = new byte[10];
        DatagramPacket packet = new DatagramPacket(new byte[10], 10);
        try {
            // length + offset > buffer.length
            packet.setData(buffer, 5, 6);
            throw new RuntimeException("Expected IllegalArgumentException for length+offset exceeding buffer length");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
