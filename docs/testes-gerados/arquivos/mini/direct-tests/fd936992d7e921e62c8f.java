/*
 * @test
 * @summary Test DatagramPacket constructor DatagramPacket(byte[], int) for correct offset and length handling
 * @run main DatagramPacketWitupTest
 */

import java.net.DatagramPacket;

public class DatagramPacketWitupTest {
    public static void main(String[] args) {
        testValidLength();
        testZeroLength();
        testLengthEqualsBufferLength();
    }

    private static void testValidLength() {
        byte[] buffer = new byte[10];
        int length = 5;
        DatagramPacket packet = new DatagramPacket(buffer, length);
        if (packet.length != length) {
            throw new RuntimeException("Length mismatch: expected " + length + " but got " + packet.length);
        }
        if (packet.offset != 0) {
            throw new RuntimeException("Offset mismatch: expected 0 but got " + packet.offset);
        }
        if (packet.buf != buffer) {
            throw new RuntimeException("Buffer mismatch: packet.buf is not the same as input buffer");
        }
    }

    private static void testZeroLength() {
        byte[] buffer = new byte[10];
        int length = 0;
        DatagramPacket packet = new DatagramPacket(buffer, length);
        if (packet.length != 0) {
            throw new RuntimeException("Length mismatch for zero length: expected 0 but got " + packet.length);
        }
        if (packet.offset != 0) {
            throw new RuntimeException("Offset mismatch for zero length: expected 0 but got " + packet.offset);
        }
    }

    private static void testLengthEqualsBufferLength() {
        byte[] buffer = new byte[10];
        int length = buffer.length;
        DatagramPacket packet = new DatagramPacket(buffer, length);
        if (packet.length != length) {
            throw new RuntimeException("Length mismatch when length equals buffer length: expected " + length + " but got " + packet.length);
        }
        if (packet.offset != 0) {
            throw new RuntimeException("Offset mismatch when length equals buffer length: expected 0 but got " + packet.offset);
        }
    }
}
