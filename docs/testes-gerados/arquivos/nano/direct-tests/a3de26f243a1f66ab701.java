/* @test    @summary Test DatagramPacket.setData(byte[] buf) for null and valid input    @run main DatagramPacketWitupTest */

import java.net.DatagramPacket;

public class DatagramPacketWitupTest {

    public static void main(String[] args) {
        testSetDataWithNull();
        testSetDataWithValidBuffer();
    }

    static void testSetDataWithNull() {
        DatagramPacket packet = new DatagramPacket(new byte[10], 10);
        try {
            packet.setData(null);
            System.out.println("FAIL: Expected NullPointerException for null buffer");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        }
    }

    static void testSetDataWithValidBuffer() {
        byte[] buffer = {1, 2, 3, 4, 5};
        DatagramPacket packet = new DatagramPacket(new byte[10], 10);
        packet.setData(buffer);
        if (packet.buf == buffer && packet.offset == 0 && packet.length == buffer.length) {
            System.out.println("PASS: Buffer set correctly");
        } else {
            System.out.println("FAIL: Buffer not set correctly");
        }
    }
}
