/*
 * @test
 * @summary Test java.net.DatagramPacket.setData(byte[] buf) method behavior
 * @run main DatagramPacketWitupTest
 */

import java.net.DatagramPacket;

public class DatagramPacketWitupTest {
    public static void main(String[] args) {
        testSetDataWithValidBuffer();
        testSetDataWithNullBuffer();
    }

    private static void testSetDataWithValidBuffer() {
        byte[] data = new byte[] {1, 2, 3, 4, 5};
        DatagramPacket packet = new DatagramPacket(new byte[10], 10);
        packet.setData(data);

        // Check that the buffer is set correctly
        byte[] buf = getBuf(packet);
        if (buf == null || buf.length != data.length) {
            throw new RuntimeException("Buffer length mismatch after setData");
        }
        for (int i = 0; i < data.length; i++) {
            if (buf[i] != data[i]) {
                throw new RuntimeException("Buffer content mismatch at index " + i);
            }
        }

        // Check offset, length, and bufLength fields via public API
        // DatagramPacket does not provide getters for offset and bufLength,
        // but length can be checked via getLength()
        if (packet.getLength() != data.length) {
            throw new RuntimeException("Length field not updated correctly");
        }
    }

    private static void testSetDataWithNullBuffer() {
        DatagramPacket packet = new DatagramPacket(new byte[10], 10);
        try {
            packet.setData(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.equals("null packet buffer")) {
                throw new RuntimeException("Unexpected NullPointerException message: " + msg);
            }
        }
    }

    // Reflection to access package-private field 'buf' since no public getter exists
    private static byte[] getBuf(DatagramPacket packet) {
        try {
            java.lang.reflect.Field f = DatagramPacket.class.getDeclaredField("buf");
            f.setAccessible(true);
            return (byte[]) f.get(packet);
        } catch (Exception e) {
            throw new RuntimeException("Failed to access buf field", e);
        }
    }
}
