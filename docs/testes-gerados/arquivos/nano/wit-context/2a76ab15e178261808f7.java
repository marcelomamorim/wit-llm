/*
 * @test
 * @summary Tests for java.net.DatagramPacket class focusing on setData method.
 * @run main DatagramPacketWitupTest
 */

import java.net.DatagramPacket;

public class DatagramPacketWitupTest {

    public static void main(String[] args) {
        testSetDataValidInputs();
        testSetDataNullBuffer();
        testSetDataNegativeOffset();
        testSetDataNegativeLength();
        testSetDataOffsetPlusLengthExceedsBuffer();
    }

    static void testSetDataValidInputs() {
        byte[] buf = {1, 2, 3, 4, 5};
        DatagramPacket packet = new DatagramPacket(buf, 0, 3);
        assert packet != null : "Packet should be created";
        assert packet.buf == buf : "Buffer should be assigned";
        assert packet.offset == 0 : "Offset should be 0";
        assert packet.length == 3 : "Length should be 3";
    }

    static void testSetDataNullBuffer() {
        try {
            DatagramPacket packet = new DatagramPacket(new byte[5], 0, 5);
            packet.setData(null, 0, 1);
            assert false : "Expected NullPointerException or IllegalArgumentException";
        } catch (NullPointerException | IllegalArgumentException e) {
            // Expected exception
        }
    }

    static void testSetDataNegativeOffset() {
        byte[] buf = {1, 2, 3};
        try {
            DatagramPacket packet = new DatagramPacket(buf, 0, 3);
            packet.setData(buf, -1, 2);
            assert false : "Expected IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static void testSetDataNegativeLength() {
        byte[] buf = {1, 2, 3};
        try {
            DatagramPacket packet = new DatagramPacket(buf, 0, 3);
            packet.setData(buf, 0, -1);
            assert false : "Expected IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static void testSetDataOffsetPlusLengthExceedsBuffer() {
        byte[] buf = {1, 2, 3};
        try {
            DatagramPacket packet = new DatagramPacket(buf, 0, 3);
            packet.setData(buf, 2, 2);
            assert false : "Expected IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
