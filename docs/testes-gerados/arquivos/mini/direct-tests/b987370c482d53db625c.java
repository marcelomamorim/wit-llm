/*
 * @test
 * @summary Test DatagramPacket constructor with buffer, offset and length
 * @run main DatagramPacketWitupTest
 */

import java.net.DatagramPacket;

public class DatagramPacketWitupTest {
    public static void main(String[] args) {
        testValidConstruction();
        testOffsetAndLengthBehavior();
    }

    private static void testValidConstruction() {
        byte[] buf = new byte[10];
        int offset = 2;
        int length = 5;
        DatagramPacket packet = new DatagramPacket(buf, offset, length);

        // Check that the packet's length is set correctly
        if (packet.length != length) {
            throw new RuntimeException("Length mismatch: expected " + length + " but got " + packet.length);
        }

        // Check that the packet's offset is set correctly
        if (packet.offset != offset) {
            throw new RuntimeException("Offset mismatch: expected " + offset + " but got " + packet.offset);
        }

        // Check that the buffer is the same
        if (packet.buf != buf) {
            throw new RuntimeException("Buffer mismatch: packet buffer is not the same as input buffer");
        }

        // Check that address is null
        if (packet.address != null) {
            throw new RuntimeException("Address should be null for this constructor");
        }

        // Check that port is -1
        if (packet.port != -1) {
            throw new RuntimeException("Port should be -1 for this constructor");
        }
    }

    private static void testOffsetAndLengthBehavior() {
        byte[] buf = new byte[10];

        // Test with offset 0 and length equal to buffer length
        DatagramPacket packet = new DatagramPacket(buf, 0, buf.length);
        if (packet.offset != 0 || packet.length != buf.length) {
            throw new RuntimeException("Offset or length incorrect when using full buffer");
        }

        // Test with offset at end of buffer and length 0
        packet = new DatagramPacket(buf, buf.length, 0);
        if (packet.offset != buf.length || packet.length != 0) {
            throw new RuntimeException("Offset or length incorrect when offset at buffer end and length 0");
        }
    }
}
