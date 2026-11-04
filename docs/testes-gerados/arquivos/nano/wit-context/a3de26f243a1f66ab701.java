/* @test    @summary Test for DatagramPacket.setData(byte[] buf) handling null buffer    @run main DatagramPacketWitupTest */

import java.net.DatagramPacket;

public class DatagramPacketWitupTest {

    public static void main(String[] args) {
        testSetDataWithNullBuffer();
        testSetDataWithValidBuffer();
    }

    private static void testSetDataWithNullBuffer() {
        DatagramPacket packet = new DatagramPacket(new byte[1], 1);
        try {
            packet.setData(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testSetDataWithValidBuffer() {
        byte[] buf = {1, 2, 3};
        DatagramPacket packet = new DatagramPacket(new byte[0], 0);
        try {
            packet.setData(buf);
            if (packet.buf == buf && packet.offset == 0 && packet.length == buf.length) {
                System.out.println("PASS: Valid buffer set correctly");
            } else {
                System.out.println("FAIL: Buffer not set as expected");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
