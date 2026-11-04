/*
 * @test
 * @summary Test DatagramPacket constructor DatagramPacket(byte[], int, SocketAddress) for valid and invalid inputs
 * @run main DatagramPacketWitupTest
 */

import java.net.DatagramPacket;
import java.net.SocketAddress;
import java.net.InetSocketAddress;

public class DatagramPacketWitupTest {
    public static void main(String[] args) {
        testValidConstructor();
        testInvalidConstructor();
    }

    private static void testValidConstructor() {
        byte[] buffer = new byte[10];
        int length = 5;
        SocketAddress address = new InetSocketAddress("127.0.0.1", 12345);

        DatagramPacket packet = new DatagramPacket(buffer, length, address);

        if (packet == null) {
            throw new RuntimeException("DatagramPacket constructor returned null");
        }

        // Check that the length is set correctly
        if (packet.getLength() != length) {
            throw new RuntimeException("Expected length " + length + " but got " + packet.getLength());
        }

        // Check that the address is set correctly
        if (!address.equals(packet.getSocketAddress())) {
            throw new RuntimeException("Expected address " + address + " but got " + packet.getSocketAddress());
        }
    }

    private static void testInvalidConstructor() {
        byte[] buffer = new byte[5];
        SocketAddress address = new InetSocketAddress("127.0.0.1", 12345);

        // length greater than buffer length should throw IllegalArgumentException
        try {
            new DatagramPacket(buffer, 6, address);
            throw new RuntimeException("Expected IllegalArgumentException for length > buffer.length");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // negative length should throw IllegalArgumentException
        try {
            new DatagramPacket(buffer, -1, address);
            throw new RuntimeException("Expected IllegalArgumentException for negative length");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // null buffer should throw NullPointerException
        try {
            new DatagramPacket(null, 1, address);
            throw new RuntimeException("Expected NullPointerException for null buffer");
        } catch (NullPointerException expected) {
            // expected
        }

        // null address is allowed (no explicit exception in source), test that no exception is thrown
        try {
            DatagramPacket p = new DatagramPacket(buffer, 1, null);
            if (p == null) {
                throw new RuntimeException("Constructor returned null with null address");
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for null address: " + e);
        }
    }
}
