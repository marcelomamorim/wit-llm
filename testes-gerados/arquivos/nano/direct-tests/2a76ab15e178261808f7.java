/* @test    @summary Test DatagramPacket.setData(byte[] buf, int offset, int length) for valid inputs    @run main DatagramPacketWitupTest */

import java.net.DatagramPacket;
import java.util.Arrays;

public class DatagramPacketWitupTest {

    public static void main(String[] args) {
        testSetDataValid();
        testSetDataNullBuffer();
        testSetDataNegativeOffset();
        testSetDataNegativeLength();
        testSetDataOffsetPlusLengthExceedsBuffer();
    }

    static void testSetDataValid() {
        byte[] buffer = {1, 2, 3, 4, 5};
        DatagramPacket packet = new DatagramPacket(buffer, 0, buffer.length);
        byte[] newData = {10, 20, 30};
        packet.setData(newData, 0, newData.length);
        assert Arrays.equals(packet.buf, newData) : "Buffer not updated correctly";
        assert packet.offset == 0 : "Offset not set correctly";
        assert packet.length == newData.length : "Length not set correctly";
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
        byte[] buffer = {1, 2, 3};
        DatagramPacket packet = new DatagramPacket(buffer, 0, buffer.length);
        try {
            packet.setData(buffer, -1, 1);
            assert false : "Expected IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static void testSetDataNegativeLength() {
        byte[] buffer = {1, 2, 3};
        DatagramPacket packet = new DatagramPacket(buffer, 0, buffer.length);
        try {
            packet.setData(buffer, 0, -1);
            assert false : "Expected IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            // Expected exception
        }

    }

    static void testSetDataOffsetPlusLengthExceedsBuffer() {
        byte[] buffer = {1, 2, 3};
        DatagramPacket packet = new DatagramPacket(buffer, 0, buffer.length);
        try {
            packet.setData(buffer, 2, 2);
            assert false : "Expected IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
