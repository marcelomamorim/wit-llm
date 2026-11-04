/*
 * @test
 * @summary Test DatagramPacket constructor DatagramPacket(byte[], int, SocketAddress)
 * @run main DatagramPacketWitupTest
 */

import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class DatagramPacketWitupTest {
    public static void main(String[] args) throws Exception {
        testValidConstruction();
        testZeroLengthBuffer();
        testNullBuffer();
    }

    private static void testValidConstruction() throws Exception {
        byte[] buf = new byte[10];
        int length = 5;
        SocketAddress addr = new InetSocketAddress("127.0.0.1", 12345);
        DatagramPacket packet = new DatagramPacket(buf, length, addr);
        if (packet == null) {
            throw new RuntimeException("DatagramPacket construction returned null");
        }
        if (packet.getLength() != length) {
            throw new RuntimeException("Length mismatch: expected " + length + " but got " + packet.getLength());
        }
        if (!packet.getSocketAddress().equals(addr)) {
            throw new RuntimeException("Address mismatch: expected " + addr + " but got " + packet.getSocketAddress());
        }
    }

    private static void testZeroLengthBuffer() throws Exception {
        byte[] buf = new byte[0];
        int length = 0;
        SocketAddress addr = new InetSocketAddress("localhost", 0);
        DatagramPacket packet = new DatagramPacket(buf, length, addr);
        if (packet.getLength() != 0) {
            throw new RuntimeException("Expected length 0 but got " + packet.getLength());
        }
    }

    private static void testNullBuffer() throws Exception {
        try {
            new DatagramPacket(null, 0, new InetSocketAddress("localhost", 0));
            throw new RuntimeException("Expected NullPointerException for null buffer");
        } catch (NullPointerException e) {
            // Expected
        }
    }
}
