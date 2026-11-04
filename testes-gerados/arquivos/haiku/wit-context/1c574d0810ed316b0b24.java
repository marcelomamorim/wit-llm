/*
 * @test
 * @summary Verifies DatagramPacket.setPort accepts ports in [0, 65535] and rejects out-of-range values without changing the stored port
 * @run main DatagramPacketWitupTest
 */

import java.net.DatagramPacket;

public class DatagramPacketWitupTest {

    public static void main(String[] args) {
        byte[] buf = new byte[8];
        DatagramPacket p = new DatagramPacket(buf, buf.length);

        int[] validPorts = {0, 1, 1234, 65535};
        for (int v : validPorts) {
            p.setPort(v);
            if (p.getPort() != v) {
                throw new RuntimeException("getPort() returned " + p.getPort() + " expected " + v);
            }
        }

        int[] invalidPorts = {-1, 65536, Integer.MIN_VALUE, Integer.MAX_VALUE};
        for (int v : invalidPorts) {
            p.setPort(4321);
            try {
                p.setPort(v);
                throw new RuntimeException("No IllegalArgumentException for port " + v);
            } catch (IllegalArgumentException expected) {
                // expected
            }
            if (p.getPort() != 4321) {
                throw new RuntimeException("Port changed after rejected value " + v + ": " + p.getPort());
            }
        }
    }
}
